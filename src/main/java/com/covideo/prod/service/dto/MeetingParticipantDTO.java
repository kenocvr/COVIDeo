package com.covideo.prod.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.covideo.prod.domain.MeetingParticipant} entity.
 */
public class MeetingParticipantDTO implements Serializable {

    private Long id;


    private Long userId;

    private Long meetingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeetingParticipantDTO meetingParticipantDTO = (MeetingParticipantDTO) o;
        if (meetingParticipantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meetingParticipantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeetingParticipantDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", meetingId=" + getMeetingId() +
            "}";
    }
}
