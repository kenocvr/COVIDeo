package com.covideo.prod.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MeetingParticipantMapperTest {

    private MeetingParticipantMapper meetingParticipantMapper;

    @BeforeEach
    public void setUp() {
        meetingParticipantMapper = new MeetingParticipantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(meetingParticipantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(meetingParticipantMapper.fromId(null)).isNull();
    }
}
