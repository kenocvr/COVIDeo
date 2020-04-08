export interface IMeetingParticipant {
  id?: number;
  userId?: number;
  meetingId?: number;
}

export class MeetingParticipant implements IMeetingParticipant {
  constructor(public id?: number, public userId?: number, public meetingId?: number) {}
}
