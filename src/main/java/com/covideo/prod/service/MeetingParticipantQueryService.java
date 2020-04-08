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

import com.covideo.prod.domain.MeetingParticipant;
import com.covideo.prod.domain.*; // for static metamodels
import com.covideo.prod.repository.MeetingParticipantRepository;
import com.covideo.prod.service.dto.MeetingParticipantCriteria;
import com.covideo.prod.service.dto.MeetingParticipantDTO;
import com.covideo.prod.service.mapper.MeetingParticipantMapper;

/**
 * Service for executing complex queries for {@link MeetingParticipant} entities in the database.
 * The main input is a {@link MeetingParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeetingParticipantDTO} or a {@link Page} of {@link MeetingParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeetingParticipantQueryService extends QueryService<MeetingParticipant> {

    private final Logger log = LoggerFactory.getLogger(MeetingParticipantQueryService.class);

    private final MeetingParticipantRepository meetingParticipantRepository;

    private final MeetingParticipantMapper meetingParticipantMapper;

    public MeetingParticipantQueryService(MeetingParticipantRepository meetingParticipantRepository, MeetingParticipantMapper meetingParticipantMapper) {
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.meetingParticipantMapper = meetingParticipantMapper;
    }

    /**
     * Return a {@link List} of {@link MeetingParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeetingParticipantDTO> findByCriteria(MeetingParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MeetingParticipant> specification = createSpecification(criteria);
        return meetingParticipantMapper.toDto(meetingParticipantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeetingParticipantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeetingParticipantDTO> findByCriteria(MeetingParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MeetingParticipant> specification = createSpecification(criteria);
        return meetingParticipantRepository.findAll(specification, page)
            .map(meetingParticipantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeetingParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MeetingParticipant> specification = createSpecification(criteria);
        return meetingParticipantRepository.count(specification);
    }

    /**
     * Function to convert {@link MeetingParticipantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MeetingParticipant> createSpecification(MeetingParticipantCriteria criteria) {
        Specification<MeetingParticipant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MeetingParticipant_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(MeetingParticipant_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getMeetingId() != null) {
                specification = specification.and(buildSpecification(criteria.getMeetingId(),
                    root -> root.join(MeetingParticipant_.meeting, JoinType.LEFT).get(Meeting_.id)));
            }
        }
        return specification;
    }
}
