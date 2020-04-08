package com.covideo.prod.service.impl;

import com.covideo.prod.service.MeetingService;
import com.covideo.prod.domain.Meeting;
import com.covideo.prod.repository.MeetingRepository;
import com.covideo.prod.service.dto.MeetingDTO;
import com.covideo.prod.service.mapper.MeetingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Meeting}.
 */
@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final Logger log = LoggerFactory.getLogger(MeetingServiceImpl.class);

    private final MeetingRepository meetingRepository;

    private final MeetingMapper meetingMapper;

    public MeetingServiceImpl(MeetingRepository meetingRepository, MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    /**
     * Save a meeting.
     *
     * @param meetingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeetingDTO save(MeetingDTO meetingDTO) {
        log.debug("Request to save Meeting : {}", meetingDTO);
        Meeting meeting = meetingMapper.toEntity(meetingDTO);
        meeting = meetingRepository.save(meeting);
        return meetingMapper.toDto(meeting);
    }

    /**
     * Get all the meetings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeetingDTO> findAll() {
        log.debug("Request to get all Meetings");
        return meetingRepository.findAll().stream()
            .map(meetingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one meeting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeetingDTO> findOne(Long id) {
        log.debug("Request to get Meeting : {}", id);
        return meetingRepository.findById(id)
            .map(meetingMapper::toDto);
    }

    /**
     * Delete the meeting by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Meeting : {}", id);
        meetingRepository.deleteById(id);
    }
}
