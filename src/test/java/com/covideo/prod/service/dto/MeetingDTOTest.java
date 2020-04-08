package com.covideo.prod.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.covideo.prod.web.rest.TestUtil;

public class MeetingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetingDTO.class);
        MeetingDTO meetingDTO1 = new MeetingDTO();
        meetingDTO1.setId(1L);
        MeetingDTO meetingDTO2 = new MeetingDTO();
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
        meetingDTO2.setId(meetingDTO1.getId());
        assertThat(meetingDTO1).isEqualTo(meetingDTO2);
        meetingDTO2.setId(2L);
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
        meetingDTO1.setId(null);
        assertThat(meetingDTO1).isNotEqualTo(meetingDTO2);
    }
}
