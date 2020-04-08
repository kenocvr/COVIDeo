package com.covideo.prod.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class MeetingMapperTest {

    private MeetingMapper meetingMapper;

    @BeforeEach
    public void setUp() {
        meetingMapper = new MeetingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(meetingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(meetingMapper.fromId(null)).isNull();
    }
}
