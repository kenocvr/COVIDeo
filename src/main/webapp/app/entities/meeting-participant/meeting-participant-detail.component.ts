import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeetingParticipant } from 'app/shared/model/meeting-participant.model';

@Component({
  selector: 'jhi-meeting-participant-detail',
  templateUrl: './meeting-participant-detail.component.html'
})
export class MeetingParticipantDetailComponent implements OnInit {
  meetingParticipant: IMeetingParticipant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meetingParticipant }) => {
      this.meetingParticipant = meetingParticipant;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
