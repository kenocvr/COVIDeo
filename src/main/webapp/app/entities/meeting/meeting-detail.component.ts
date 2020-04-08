import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeeting } from 'app/shared/model/meeting.model';

@Component({
  selector: 'jhi-meeting-detail',
  templateUrl: './meeting-detail.component.html'
})
export class MeetingDetailComponent implements OnInit {
  meeting: IMeeting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meeting }) => {
      this.meeting = meeting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
