package com.covideo.prod.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.covideo.prod.domain.Meeting;
import com.covideo.prod.domain.*; // for static metamodels
import com.covideo.prod.repository.MeetingRepository;
import com.covideo.prod.service.dto.MeetingCriteria;
import com.covideo.prod.service.dto.MeetingDTO;
import com.covideo.prod.service.mapper.MeetingMapper;

/**
 * Service for executing complex queries for {@link Meeting} entities in the database.
 * The main input is a {@link MeetingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeetingDTO} or a {@link Page} of {@link MeetingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeetingQueryService extends QueryService<Meeting> {

    private final Logger log = LoggerFactory.getLogger(MeetingQueryService.class);

    private final MeetingRepository meetingRepository;

    private final MeetingMapper meetingMapper;

    public MeetingQueryService(MeetingRepository meetingRepository, MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    /**
     * Return a {@link List} of {@link MeetingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingDTO> findByCriteria(MeetingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Meeting> specification = createSpecification(criteria);
        return meetingMapper.toDto(meetingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeetingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingDTO> findByCriteria(MeetingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Meeting> specification = createSpecification(criteria);
        return meetingRepository.findAll(specification, page)
            .map(meetingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeetingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Meeting> specification = createSpecification(criteria);
        return meetingRepository.count(specification);
    }

    /**
     * Function to convert {@link MeetingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Meeting> createSpecification(MeetingCriteria criteria) {
        Specification<Meeting> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Meeting_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Meeting_.title));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Meeting_.date));
            }
            if (criteria.getTimeZone() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeZone(), Meeting_.timeZone));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Meeting_.url));
            }
            if (criteria.getMeetingParticipantId() != null) {
                specification = specification.and(buildSpecification(criteria.getMeetingParticipantId(),
                    root -> root.join(Meeting_.meetingParticipants, JoinType.LEFT).get(MeetingParticipant_.id)));
            }
        }
        return specification;
    }
}
