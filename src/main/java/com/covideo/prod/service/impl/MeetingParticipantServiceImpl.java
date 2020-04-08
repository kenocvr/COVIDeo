package com.covideo.prod.service.impl;

import com.covideo.prod.service.MeetingParticipantService;
import com.covideo.prod.domain.MeetingParticipant;
import com.covideo.prod.repository.MeetingParticipantRepository;
import com.covideo.prod.service.dto.MeetingParticipantDTO;
import com.covideo.prod.service.mapper.MeetingParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MeetingParticipant}.
 */
@Service
@Transactional
public class MeetingParticipantServiceImpl implements MeetingParticipantService {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantServiceImpl.class);

    private final MeetingParticipantRepository meetingParticipantRepository;

    private final MeetingParticipantMapper meetingParticipantMapper;

    public MeetingParticipantServiceImpl(MeetingParticipantRepository meetingParticipantRepository, MeetingParticipantMapper meetingParticipantMapper) {
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.meetingParticipantMapper = meetingParticipantMapper;
    }

    /**
     * Save a meetingParticipant.
     *
     * @param meetingParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeetingParticipantDTO save(MeetingParticipantDTO meetingParticipantDTO) {
        log.debug("Request to save MeetingParticipant : {}", meetingParticipantDTO);
        MeetingParticipant meetingParticipant = meetingParticipantMapper.toEntity(meetingParticipantDTO);
        meetingParticipant = meetingParticipantRepository.save(meetingParticipant);
        return meetingParticipantMapper.toDto(meetingParticipant);
    }

    /**
     * Get all the meetingParticipants.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeetingParticipantDTO> findAll() {
        log.debug("Request to get all MeetingParticipants");
        return meetingParticipantRepository.findAll().stream()
            .map(meetingParticipantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one meetingParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeetingParticipantDTO> findOne(Long id) {
        log.debug("Request to get MeetingParticipant : {}", id);
        return meetingParticipantRepository.findById(id)
            .map(meetingParticipantMapper::toDto);
    }

    /**
     * Delete the meetingParticipant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeetingParticipant : {}", id);
        meetingParticipantRepository.deleteById(id);
    }
}
