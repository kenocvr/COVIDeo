package com.covideo.prod.service.mapper;

import com.covideo.prod.domain.*;
import com.covideo.prod.service.dto.MeetingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meeting} and its DTO {@link MeetingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeetingMapper extends EntityMapper<MeetingDTO, Meeting> {


    @Mapping(target = "meetingParticipants", ignore = true)
    @Mapping(target = "removeMeetingParticipant", ignore = true)
    Meeting toEntity(MeetingDTO meetingDTO);

    default Meeting fromId(Long id) {
        if (id == null) {
            return null;
        }
        Meeting meeting = new Meeting();
        meeting.setId(id);
        return meeting;
    }
}
