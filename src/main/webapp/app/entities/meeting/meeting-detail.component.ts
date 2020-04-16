import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeeting } from 'app/shared/model/meeting.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'jhi-meeting-detail',
  templateUrl: './meeting-detail.component.html'
})
export class MeetingDetailComponent implements OnInit {
  meeting: IMeeting | null = null;

  name = 'Set iframe source';
  url = 'https://serene-tundra-46064.herokuapp.com/';
  urlSafe: SafeResourceUrl | undefined;

  constructor(protected activatedRoute: ActivatedRoute, public sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meeting }) => {
      this.meeting = meeting;
      this.url += '?x=' + meeting.url;
      this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
    });
  }

  previousState(): void {
    window.history.back();
  }
}
