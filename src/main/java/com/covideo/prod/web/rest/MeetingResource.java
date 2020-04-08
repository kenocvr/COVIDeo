package com.covideo.prod.web.rest;

import com.covideo.prod.service.MeetingService;
import com.covideo.prod.web.rest.errors.BadRequestAlertException;
import com.covideo.prod.service.dto.MeetingDTO;
import com.covideo.prod.service.dto.MeetingCriteria;
import com.covideo.prod.service.MeetingQueryService;

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
 * REST controller for managing {@link com.covideo.prod.domain.Meeting}.
 */
@RestController
@RequestMapping("/api")
public class MeetingResource {

    private final Logger log = LoggerFactory.getLogger(MeetingResource.class);

    private static final String ENTITY_NAME = "meeting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetingService meetingService;

    private final MeetingQueryService meetingQueryService;

    public MeetingResource(MeetingService meetingService, MeetingQueryService meetingQueryService) {
        this.meetingService = meetingService;
        this.meetingQueryService = meetingQueryService;
    }

    /**
     * {@code POST  /meetings} : Create a new meeting.
     *
     * @param meetingDTO the meetingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetingDTO, or with status {@code 400 (Bad Request)} if the meeting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meetings")
    public ResponseEntity<MeetingDTO> createMeeting(@RequestBody MeetingDTO meetingDTO) throws URISyntaxException {
        log.debug("REST request to save Meeting : {}", meetingDTO);
        if (meetingDTO.getId() != null) {
            throw new BadRequestAlertException("A new meeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetingDTO result = meetingService.save(meetingDTO);
        return ResponseEntity.created(new URI("/api/meetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meetings} : Updates an existing meeting.
     *
     * @param meetingDTO the meetingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetingDTO,
     * or with status {@code 400 (Bad Request)} if the meetingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meetings")
    public ResponseEntity<MeetingDTO> updateMeeting(@RequestBody MeetingDTO meetingDTO) throws URISyntaxException {
        log.debug("REST request to update Meeting : {}", meetingDTO);
        if (meetingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetingDTO result = meetingService.save(meetingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meetings} : get all the meetings.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetings in body.
     */
    @GetMapping("/meetings")
    public ResponseEntity<List<MeetingDTO>> getAllMeetings(MeetingCriteria criteria) {
        log.debug("REST request to get Meetings by criteria: {}", criteria);
        List<MeetingDTO> entityList = meetingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /meetings/count} : count all the meetings.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/meetings/count")
    public ResponseEntity<Long> countMeetings(MeetingCriteria criteria) {
        log.debug("REST request to count Meetings by criteria: {}", criteria);
        return ResponseEntity.ok().body(meetingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /meetings/:id} : get the "id" meeting.
     *
     * @param id the id of the meetingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meetings/{id}")
    public ResponseEntity<MeetingDTO> getMeeting(@PathVariable Long id) {
        log.debug("REST request to get Meeting : {}", id);
        Optional<MeetingDTO> meetingDTO = meetingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetingDTO);
    }

    /**
     * {@code DELETE  /meetings/:id} : delete the "id" meeting.
     *
     * @param id the id of the meetingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        log.debug("REST request to delete Meeting : {}", id);
        meetingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
