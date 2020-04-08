package com.covideo.prod.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.covideo.prod.web.rest.TestUtil;

public class MeetingParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetingParticipantDTO.class);
        MeetingParticipantDTO meetingParticipantDTO1 = new MeetingParticipantDTO();
        meetingParticipantDTO1.setId(1L);
        MeetingParticipantDTO meetingParticipantDTO2 = new MeetingParticipantDTO();
        assertThat(meetingParticipantDTO1).isNotEqualTo(meetingParticipantDTO2);
        meetingParticipantDTO2.setId(meetingParticipantDTO1.getId());
        assertThat(meetingParticipantDTO1).isEqualTo(meetingParticipantDTO2);
        meetingParticipantDTO2.setId(2L);
        assertThat(meetingParticipantDTO1).isNotEqualTo(meetingParticipantDTO2);
        meetingParticipantDTO1.setId(null);
        assertThat(meetingParticipantDTO1).isNotEqualTo(meetingParticipantDTO2);
    }
}
