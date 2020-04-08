package com.covideo.prod.service.mapper;

import com.covideo.prod.domain.*;
import com.covideo.prod.service.dto.MeetingParticipantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeetingParticipant} and its DTO {@link MeetingParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MeetingMapper.class})
public interface MeetingParticipantMapper extends EntityMapper<MeetingParticipantDTO, MeetingParticipant> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "meeting.id", target = "meetingId")
    MeetingParticipantDTO toDto(MeetingParticipant meetingParticipant);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "meetingId", target = "meeting")
    MeetingParticipant toEntity(MeetingParticipantDTO meetingParticipantDTO);

    default MeetingParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeetingParticipant meetingParticipant = new MeetingParticipant();
        meetingParticipant.setId(id);
        return meetingParticipant;
    }
}
