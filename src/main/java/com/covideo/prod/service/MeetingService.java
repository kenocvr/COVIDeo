package com.covideo.prod.service;

import com.covideo.prod.service.dto.MeetingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.covideo.prod.domain.Meeting}.
 */
public interface MeetingService {

    /**
     * Save a meeting.
     *
     * @param meetingDTO the entity to save.
     * @return the persisted entity.
     */
    MeetingDTO save(MeetingDTO meetingDTO);

    /**
     * Get all the meetings.
     *
     * @return the list of entities.
     */
    List<MeetingDTO> findAll();


    /**
     * Get the "id" meeting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeetingDTO> findOne(Long id);

    /**
     * Delete the "id" meeting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
