package com.covideo.prod.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.covideo.prod.domain.enumeration.TimeZone;

/**
 * A Meeting.
 */
@Entity
@Table(name = "meeting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_zone")
    private TimeZone timeZone;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "meeting")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Meeting title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getDate() {
        return date;
    }

    public Meeting date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Meeting timeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getUrl() {
        return url;
    }

    public Meeting url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<MeetingParticipant> getMeetingParticipants() {
        return meetingParticipants;
    }

    public Meeting meetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
        return this;
    }

    public Meeting addMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.add(meetingParticipant);
        meetingParticipant.setMeeting(this);
        return this;
    }

    public Meeting removeMeetingParticipant(MeetingParticipant meetingParticipant) {
        this.meetingParticipants.remove(meetingParticipant);
        meetingParticipant.setMeeting(null);
        return this;
    }

    public void setMeetingParticipants(Set<MeetingParticipant> meetingParticipants) {
        this.meetingParticipants = meetingParticipants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        return id != null && id.equals(((Meeting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Meeting{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
