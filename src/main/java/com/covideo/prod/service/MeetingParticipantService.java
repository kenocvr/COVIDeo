package com.covideo.prod.service;

import com.covideo.prod.service.dto.MeetingParticipantDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.covideo.prod.domain.MeetingParticipant}.
 */
public interface MeetingParticipantService {

    /**
     * Save a meetingParticipant.
     *
     * @param meetingParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    MeetingParticipantDTO save(MeetingParticipantDTO meetingParticipantDTO);

    /**
     * Get all the meetingParticipants.
     *
     * @return the list of entities.
     */
    List<MeetingParticipantDTO> findAll();


    /**
     * Get the "id" meetingParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeetingParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" meetingParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
