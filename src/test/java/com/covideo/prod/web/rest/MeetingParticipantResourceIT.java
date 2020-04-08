package com.covideo.prod.web.rest;

import com.covideo.prod.CoviDeoApp;
import com.covideo.prod.domain.MeetingParticipant;
import com.covideo.prod.domain.User;
import com.covideo.prod.domain.Meeting;
import com.covideo.prod.repository.MeetingParticipantRepository;
import com.covideo.prod.service.MeetingParticipantService;
import com.covideo.prod.service.dto.MeetingParticipantDTO;
import com.covideo.prod.service.mapper.MeetingParticipantMapper;
import com.covideo.prod.web.rest.errors.ExceptionTranslator;
import com.covideo.prod.service.dto.MeetingParticipantCriteria;
import com.covideo.prod.service.MeetingParticipantQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.covideo.prod.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MeetingParticipantResource} REST controller.
 */
@SpringBootTest(classes = CoviDeoApp.class)
public class MeetingParticipantResourceIT {

    @Autowired
    private MeetingParticipantRepository meetingParticipantRepository;

    @Autowired
    private MeetingParticipantMapper meetingParticipantMapper;

    @Autowired
    private MeetingParticipantService meetingParticipantService;

    @Autowired
    private MeetingParticipantQueryService meetingParticipantQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMeetingParticipantMockMvc;

    private MeetingParticipant meetingParticipant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeetingParticipantResource meetingParticipantResource = new MeetingParticipantResource(meetingParticipantService, meetingParticipantQueryService);
        this.restMeetingParticipantMockMvc = MockMvcBuilders.standaloneSetup(meetingParticipantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeetingParticipant createEntity(EntityManager em) {
        MeetingParticipant meetingParticipant = new MeetingParticipant();
        return meetingParticipant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeetingParticipant createUpdatedEntity(EntityManager em) {
        MeetingParticipant meetingParticipant = new MeetingParticipant();
        return meetingParticipant;
    }

    @BeforeEach
    public void initTest() {
        meetingParticipant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeetingParticipant() throws Exception {
        int databaseSizeBeforeCreate = meetingParticipantRepository.findAll().size();

        // Create the MeetingParticipant
        MeetingParticipantDTO meetingParticipantDTO = meetingParticipantMapper.toDto(meetingParticipant);
        restMeetingParticipantMockMvc.perform(post("/api/meeting-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingParticipantDTO)))
            .andExpect(status().isCreated());

        // Validate the MeetingParticipant in the database
        List<MeetingParticipant> meetingParticipantList = meetingParticipantRepository.findAll();
        assertThat(meetingParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        MeetingParticipant testMeetingParticipant = meetingParticipantList.get(meetingParticipantList.size() - 1);
    }

    @Test
    @Transactional
    public void createMeetingParticipantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meetingParticipantRepository.findAll().size();

        // Create the MeetingParticipant with an existing ID
        meetingParticipant.setId(1L);
        MeetingParticipantDTO meetingParticipantDTO = meetingParticipantMapper.toDto(meetingParticipant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeetingParticipantMockMvc.perform(post("/api/meeting-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeetingParticipant in the database
        List<MeetingParticipant> meetingParticipantList = meetingParticipantRepository.findAll();
        assertThat(meetingParticipantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeetingParticipants() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);

        // Get all the meetingParticipantList
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meetingParticipant.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getMeetingParticipant() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);

        // Get the meetingParticipant
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants/{id}", meetingParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meetingParticipant.getId().intValue()));
    }


    @Test
    @Transactional
    public void getMeetingParticipantsByIdFiltering() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);

        Long id = meetingParticipant.getId();

        defaultMeetingParticipantShouldBeFound("id.equals=" + id);
        defaultMeetingParticipantShouldNotBeFound("id.notEquals=" + id);

        defaultMeetingParticipantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeetingParticipantShouldNotBeFound("id.greaterThan=" + id);

        defaultMeetingParticipantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeetingParticipantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMeetingParticipantsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        meetingParticipant.setUser(user);
        meetingParticipantRepository.saveAndFlush(meetingParticipant);
        Long userId = user.getId();

        // Get all the meetingParticipantList where user equals to userId
        defaultMeetingParticipantShouldBeFound("userId.equals=" + userId);

        // Get all the meetingParticipantList where user equals to userId + 1
        defaultMeetingParticipantShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMeetingParticipantsByMeetingIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);
        Meeting meeting = MeetingResourceIT.createEntity(em);
        em.persist(meeting);
        em.flush();
        meetingParticipant.setMeeting(meeting);
        meetingParticipantRepository.saveAndFlush(meetingParticipant);
        Long meetingId = meeting.getId();

        // Get all the meetingParticipantList where meeting equals to meetingId
        defaultMeetingParticipantShouldBeFound("meetingId.equals=" + meetingId);

        // Get all the meetingParticipantList where meeting equals to meetingId + 1
        defaultMeetingParticipantShouldNotBeFound("meetingId.equals=" + (meetingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeetingParticipantShouldBeFound(String filter) throws Exception {
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meetingParticipant.getId().intValue())));

        // Check, that the count call also returns 1
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeetingParticipantShouldNotBeFound(String filter) throws Exception {
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMeetingParticipant() throws Exception {
        // Get the meetingParticipant
        restMeetingParticipantMockMvc.perform(get("/api/meeting-participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeetingParticipant() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);

        int databaseSizeBeforeUpdate = meetingParticipantRepository.findAll().size();

        // Update the meetingParticipant
        MeetingParticipant updatedMeetingParticipant = meetingParticipantRepository.findById(meetingParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedMeetingParticipant are not directly saved in db
        em.detach(updatedMeetingParticipant);
        MeetingParticipantDTO meetingParticipantDTO = meetingParticipantMapper.toDto(updatedMeetingParticipant);

        restMeetingParticipantMockMvc.perform(put("/api/meeting-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingParticipantDTO)))
            .andExpect(status().isOk());

        // Validate the MeetingParticipant in the database
        List<MeetingParticipant> meetingParticipantList = meetingParticipantRepository.findAll();
        assertThat(meetingParticipantList).hasSize(databaseSizeBeforeUpdate);
        MeetingParticipant testMeetingParticipant = meetingParticipantList.get(meetingParticipantList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingMeetingParticipant() throws Exception {
        int databaseSizeBeforeUpdate = meetingParticipantRepository.findAll().size();

        // Create the MeetingParticipant
        MeetingParticipantDTO meetingParticipantDTO = meetingParticipantMapper.toDto(meetingParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeetingParticipantMockMvc.perform(put("/api/meeting-participants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingParticipantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeetingParticipant in the database
        List<MeetingParticipant> meetingParticipantList = meetingParticipantRepository.findAll();
        assertThat(meetingParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeetingParticipant() throws Exception {
        // Initialize the database
        meetingParticipantRepository.saveAndFlush(meetingParticipant);

        int databaseSizeBeforeDelete = meetingParticipantRepository.findAll().size();

        // Delete the meetingParticipant
        restMeetingParticipantMockMvc.perform(delete("/api/meeting-participants/{id}", meetingParticipant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeetingParticipant> meetingParticipantList = meetingParticipantRepository.findAll();
        assertThat(meetingParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
