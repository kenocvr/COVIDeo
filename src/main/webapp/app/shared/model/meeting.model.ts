import { Moment } from 'moment';
import { IMeetingParticipant } from 'app/shared/model/meeting-participant.model';
import { TimeZone } from 'app/shared/model/enumerations/time-zone.model';

export interface IMeeting {
  id?: number;
  title?: string;
  date?: Moment;
  timeZone?: TimeZone;
  url?: string;
  meetingParticipants?: IMeetingParticipant[];
}

export class Meeting implements IMeeting {
  constructor(
    public id?: number,
    public title?: string,
    public date?: Moment,
    public timeZone?: TimeZone,
    public url?: string,
    public meetingParticipants?: IMeetingParticipant[]
  ) {}
}
