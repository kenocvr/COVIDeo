package com.covideo.prod.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.covideo.prod.domain.enumeration.TimeZone;

/**
 * A DTO for the {@link com.covideo.prod.domain.Meeting} entity.
 */
public class MeetingDTO implements Serializable {

    private Long id;

    private String title;

    private Instant date;

    private TimeZone timeZone;

    private String url;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeetingDTO meetingDTO = (MeetingDTO) o;
        if (meetingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meetingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeetingDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
