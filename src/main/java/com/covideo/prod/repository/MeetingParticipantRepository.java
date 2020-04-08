package com.covideo.prod.repository;

import com.covideo.prod.domain.MeetingParticipant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MeetingParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long>, JpaSpecificationExecutor<MeetingParticipant> {

}
