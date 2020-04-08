package com.covideo.prod.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.covideo.prod.domain.enumeration.TimeZone;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.covideo.prod.domain.Meeting} entity. This class is used
 * in {@link com.covideo.prod.web.rest.MeetingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /meetings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MeetingCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TimeZone
     */
    public static class TimeZoneFilter extends Filter<TimeZone> {

        public TimeZoneFilter() {
        }

        public TimeZoneFilter(TimeZoneFilter filter) {
            super(filter);
        }

        @Override
        public TimeZoneFilter copy() {
            return new TimeZoneFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter date;

    private TimeZoneFilter timeZone;

    private StringFilter url;

    private LongFilter meetingParticipantId;

    public MeetingCriteria(){
    }

    public MeetingCriteria(MeetingCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.timeZone = other.timeZone == null ? null : other.timeZone.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.meetingParticipantId = other.meetingParticipantId == null ? null : other.meetingParticipantId.copy();
    }

    @Override
    public MeetingCriteria copy() {
        return new MeetingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public TimeZoneFilter getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZoneFilter timeZone) {
        this.timeZone = timeZone;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public LongFilter getMeetingParticipantId() {
        return meetingParticipantId;
    }

    public void setMeetingParticipantId(LongFilter meetingParticipantId) {
        this.meetingParticipantId = meetingParticipantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MeetingCriteria that = (MeetingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(date, that.date) &&
            Objects.equals(timeZone, that.timeZone) &&
            Objects.equals(url, that.url) &&
            Objects.equals(meetingParticipantId, that.meetingParticipantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        date,
        timeZone,
        url,
        meetingParticipantId
        );
    }

    @Override
    public String toString() {
        return "MeetingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (timeZone != null ? "timeZone=" + timeZone + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (meetingParticipantId != null ? "meetingParticipantId=" + meetingParticipantId + ", " : "") +
            "}";
    }

}
