package com.covideo.prod.web.rest;

import com.covideo.prod.service.MeetingParticipantService;
import com.covideo.prod.web.rest.errors.BadRequestAlertException;
import com.covideo.prod.service.dto.MeetingParticipantDTO;
import com.covideo.prod.service.dto.MeetingParticipantCriteria;
import com.covideo.prod.service.MeetingParticipantQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.covideo.prod.domain.MeetingParticipant}.
 */
@RestController
@RequestMapping("/api")
public class MeetingParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantResource.class);

    private static final String ENTITY_NAME = "meetingParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingParticipantService meetingParticipantService;

    private final MeetingParticipantQueryService meetingParticipantQueryService;

    public MeetingParticipantResource(MeetingParticipantService meetingParticipantService, MeetingParticipantQueryService meetingParticipantQueryService) {
        this.meetingParticipantService = meetingParticipantService;
        this.meetingParticipantQueryService = meetingParticipantQueryService;
    }

    /**
     * {@code POST  /meeting-participants} : Create a new meetingParticipant.
     *
     * @param meetingParticipantDTO the meetingParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingParticipantDTO, or with status {@code 400 (Bad Request)} if the meetingParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meeting-participants")
    public ResponseEntity<MeetingParticipantDTO> createMeetingParticipant(@RequestBody MeetingParticipantDTO meetingParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save MeetingParticipant : {}", meetingParticipantDTO);
        if (meetingParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new meetingParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingParticipantDTO result = meetingParticipantService.save(meetingParticipantDTO);
        return ResponseEntity.created(new URI("/api/meeting-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meeting-participants} : Updates an existing meetingParticipant.
     *
     * @param meetingParticipantDTO the meetingParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the meetingParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meeting-participants")
    public ResponseEntity<MeetingParticipantDTO> updateMeetingParticipant(@RequestBody MeetingParticipantDTO meetingParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update MeetingParticipant : {}", meetingParticipantDTO);
        if (meetingParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingParticipantDTO result = meetingParticipantService.save(meetingParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meeting-participants} : get all the meetingParticipants.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetingParticipants in body.
     */
    @GetMapping("/meeting-participants")
    public ResponseEntity<List<MeetingParticipantDTO>> getAllMeetingParticipants(MeetingParticipantCriteria criteria) {
        log.debug("REST request to get MeetingParticipants by criteria: {}", criteria);
        List<MeetingParticipantDTO> entityList = meetingParticipantQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /meeting-participants/count} : count all the meetingParticipants.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/meeting-participants/count")
    public ResponseEntity<Long> countMeetingParticipants(MeetingParticipantCriteria criteria) {
        log.debug("REST request to count MeetingParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(meetingParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /meeting-participants/:id} : get the "id" meetingParticipant.
     *
     * @param id the id of the meetingParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meeting-participants/{id}")
    public ResponseEntity<MeetingParticipantDTO> getMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to get MeetingParticipant : {}", id);
        Optional<MeetingParticipantDTO> meetingParticipantDTO = meetingParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingParticipantDTO);
    }

    /**
     * {@code DELETE  /meeting-participants/:id} : delete the "id" meetingParticipant.
     *
     * @param id the id of the meetingParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meeting-participants/{id}")
    public ResponseEntity<Void> deleteMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MeetingParticipant : {}", id);
        meetingParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
