package com.covideo.prod.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.covideo.prod.web.rest.TestUtil;

public class MeetingParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeetingParticipant.class);
        MeetingParticipant meetingParticipant1 = new MeetingParticipant();
        meetingParticipant1.setId(1L);
        MeetingParticipant meetingParticipant2 = new MeetingParticipant();
        meetingParticipant2.setId(meetingParticipant1.getId());
        assertThat(meetingParticipant1).isEqualTo(meetingParticipant2);
        meetingParticipant2.setId(2L);
        assertThat(meetingParticipant1).isNotEqualTo(meetingParticipant2);
        meetingParticipant1.setId(null);
        assertThat(meetingParticipant1).isNotEqualTo(meetingParticipant2);
    }
}
