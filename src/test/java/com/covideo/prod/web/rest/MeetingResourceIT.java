package com.covideo.prod.web.rest;

import com.covideo.prod.CoviDeoApp;
import com.covideo.prod.domain.Meeting;
import com.covideo.prod.domain.MeetingParticipant;
import com.covideo.prod.repository.MeetingRepository;
import com.covideo.prod.service.MeetingService;
import com.covideo.prod.service.dto.MeetingDTO;
import com.covideo.prod.service.mapper.MeetingMapper;
import com.covideo.prod.web.rest.errors.ExceptionTranslator;
import com.covideo.prod.service.dto.MeetingCriteria;
import com.covideo.prod.service.MeetingQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.covideo.prod.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.covideo.prod.domain.enumeration.TimeZone;
/**
 * Integration tests for the {@link MeetingResource} REST controller.
 */
@SpringBootTest(classes = CoviDeoApp.class)
public class MeetingResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.Hawaii;
    private static final TimeZone UPDATED_TIME_ZONE = TimeZone.Alaska;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingQueryService meetingQueryService;

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

    private MockMvc restMeetingMockMvc;

    private Meeting meeting;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeetingResource meetingResource = new MeetingResource(meetingService, meetingQueryService);
        this.restMeetingMockMvc = MockMvcBuilders.standaloneSetup(meetingResource)
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
    public static Meeting createEntity(EntityManager em) {
        Meeting meeting = new Meeting()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .timeZone(DEFAULT_TIME_ZONE)
            .url(DEFAULT_URL);
        return meeting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meeting createUpdatedEntity(EntityManager em) {
        Meeting meeting = new Meeting()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .timeZone(UPDATED_TIME_ZONE)
            .url(UPDATED_URL);
        return meeting;
    }

    @BeforeEach
    public void initTest() {
        meeting = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeeting() throws Exception {
        int databaseSizeBeforeCreate = meetingRepository.findAll().size();

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);
        restMeetingMockMvc.perform(post("/api/meetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingDTO)))
            .andExpect(status().isCreated());

        // Validate the Meeting in the database
        List<Meeting> meetingList = meetingRepository.findAll();
        assertThat(meetingList).hasSize(databaseSizeBeforeCreate + 1);
        Meeting testMeeting = meetingList.get(meetingList.size() - 1);
        assertThat(testMeeting.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMeeting.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMeeting.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testMeeting.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createMeetingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meetingRepository.findAll().size();

        // Create the Meeting with an existing ID
        meeting.setId(1L);
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeetingMockMvc.perform(post("/api/meetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        List<Meeting> meetingList = meetingRepository.findAll();
        assertThat(meetingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeetings() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList
        restMeetingMockMvc.perform(get("/api/meetings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get the meeting
        restMeetingMockMvc.perform(get("/api/meetings/{id}", meeting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meeting.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }


    @Test
    @Transactional
    public void getMeetingsByIdFiltering() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        Long id = meeting.getId();

        defaultMeetingShouldBeFound("id.equals=" + id);
        defaultMeetingShouldNotBeFound("id.notEquals=" + id);

        defaultMeetingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeetingShouldNotBeFound("id.greaterThan=" + id);

        defaultMeetingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeetingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMeetingsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title equals to DEFAULT_TITLE
        defaultMeetingShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the meetingList where title equals to UPDATED_TITLE
        defaultMeetingShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title not equals to DEFAULT_TITLE
        defaultMeetingShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the meetingList where title not equals to UPDATED_TITLE
        defaultMeetingShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMeetingShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the meetingList where title equals to UPDATED_TITLE
        defaultMeetingShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title is not null
        defaultMeetingShouldBeFound("title.specified=true");

        // Get all the meetingList where title is null
        defaultMeetingShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeetingsByTitleContainsSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title contains DEFAULT_TITLE
        defaultMeetingShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the meetingList where title contains UPDATED_TITLE
        defaultMeetingShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where title does not contain DEFAULT_TITLE
        defaultMeetingShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the meetingList where title does not contain UPDATED_TITLE
        defaultMeetingShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllMeetingsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where date equals to DEFAULT_DATE
        defaultMeetingShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the meetingList where date equals to UPDATED_DATE
        defaultMeetingShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where date not equals to DEFAULT_DATE
        defaultMeetingShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the meetingList where date not equals to UPDATED_DATE
        defaultMeetingShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where date in DEFAULT_DATE or UPDATED_DATE
        defaultMeetingShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the meetingList where date equals to UPDATED_DATE
        defaultMeetingShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where date is not null
        defaultMeetingShouldBeFound("date.specified=true");

        // Get all the meetingList where date is null
        defaultMeetingShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeetingsByTimeZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where timeZone equals to DEFAULT_TIME_ZONE
        defaultMeetingShouldBeFound("timeZone.equals=" + DEFAULT_TIME_ZONE);

        // Get all the meetingList where timeZone equals to UPDATED_TIME_ZONE
        defaultMeetingShouldNotBeFound("timeZone.equals=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTimeZoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where timeZone not equals to DEFAULT_TIME_ZONE
        defaultMeetingShouldNotBeFound("timeZone.notEquals=" + DEFAULT_TIME_ZONE);

        // Get all the meetingList where timeZone not equals to UPDATED_TIME_ZONE
        defaultMeetingShouldBeFound("timeZone.notEquals=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTimeZoneIsInShouldWork() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where timeZone in DEFAULT_TIME_ZONE or UPDATED_TIME_ZONE
        defaultMeetingShouldBeFound("timeZone.in=" + DEFAULT_TIME_ZONE + "," + UPDATED_TIME_ZONE);

        // Get all the meetingList where timeZone equals to UPDATED_TIME_ZONE
        defaultMeetingShouldNotBeFound("timeZone.in=" + UPDATED_TIME_ZONE);
    }

    @Test
    @Transactional
    public void getAllMeetingsByTimeZoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where timeZone is not null
        defaultMeetingShouldBeFound("timeZone.specified=true");

        // Get all the meetingList where timeZone is null
        defaultMeetingShouldNotBeFound("timeZone.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeetingsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url equals to DEFAULT_URL
        defaultMeetingShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the meetingList where url equals to UPDATED_URL
        defaultMeetingShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMeetingsByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url not equals to DEFAULT_URL
        defaultMeetingShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the meetingList where url not equals to UPDATED_URL
        defaultMeetingShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMeetingsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url in DEFAULT_URL or UPDATED_URL
        defaultMeetingShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the meetingList where url equals to UPDATED_URL
        defaultMeetingShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMeetingsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url is not null
        defaultMeetingShouldBeFound("url.specified=true");

        // Get all the meetingList where url is null
        defaultMeetingShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllMeetingsByUrlContainsSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url contains DEFAULT_URL
        defaultMeetingShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the meetingList where url contains UPDATED_URL
        defaultMeetingShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllMeetingsByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        // Get all the meetingList where url does not contain DEFAULT_URL
        defaultMeetingShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the meetingList where url does not contain UPDATED_URL
        defaultMeetingShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllMeetingsByMeetingParticipantIsEqualToSomething() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);
        MeetingParticipant meetingParticipant = MeetingParticipantResourceIT.createEntity(em);
        em.persist(meetingParticipant);
        em.flush();
        meeting.addMeetingParticipant(meetingParticipant);
        meetingRepository.saveAndFlush(meeting);
        Long meetingParticipantId = meetingParticipant.getId();

        // Get all the meetingList where meetingParticipant equals to meetingParticipantId
        defaultMeetingShouldBeFound("meetingParticipantId.equals=" + meetingParticipantId);

        // Get all the meetingList where meetingParticipant equals to meetingParticipantId + 1
        defaultMeetingShouldNotBeFound("meetingParticipantId.equals=" + (meetingParticipantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeetingShouldBeFound(String filter) throws Exception {
        restMeetingMockMvc.perform(get("/api/meetings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meeting.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));

        // Check, that the count call also returns 1
        restMeetingMockMvc.perform(get("/api/meetings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeetingShouldNotBeFound(String filter) throws Exception {
        restMeetingMockMvc.perform(get("/api/meetings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeetingMockMvc.perform(get("/api/meetings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMeeting() throws Exception {
        // Get the meeting
        restMeetingMockMvc.perform(get("/api/meetings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        int databaseSizeBeforeUpdate = meetingRepository.findAll().size();

        // Update the meeting
        Meeting updatedMeeting = meetingRepository.findById(meeting.getId()).get();
        // Disconnect from session so that the updates on updatedMeeting are not directly saved in db
        em.detach(updatedMeeting);
        updatedMeeting
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .timeZone(UPDATED_TIME_ZONE)
            .url(UPDATED_URL);
        MeetingDTO meetingDTO = meetingMapper.toDto(updatedMeeting);

        restMeetingMockMvc.perform(put("/api/meetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingDTO)))
            .andExpect(status().isOk());

        // Validate the Meeting in the database
        List<Meeting> meetingList = meetingRepository.findAll();
        assertThat(meetingList).hasSize(databaseSizeBeforeUpdate);
        Meeting testMeeting = meetingList.get(meetingList.size() - 1);
        assertThat(testMeeting.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMeeting.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMeeting.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testMeeting.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingMeeting() throws Exception {
        int databaseSizeBeforeUpdate = meetingRepository.findAll().size();

        // Create the Meeting
        MeetingDTO meetingDTO = meetingMapper.toDto(meeting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeetingMockMvc.perform(put("/api/meetings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meetingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meeting in the database
        List<Meeting> meetingList = meetingRepository.findAll();
        assertThat(meetingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeeting() throws Exception {
        // Initialize the database
        meetingRepository.saveAndFlush(meeting);

        int databaseSizeBeforeDelete = meetingRepository.findAll().size();

        // Delete the meeting
        restMeetingMockMvc.perform(delete("/api/meetings/{id}", meeting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meeting> meetingList = meetingRepository.findAll();
        assertThat(meetingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
