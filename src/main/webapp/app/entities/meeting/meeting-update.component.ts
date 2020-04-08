import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMeeting, Meeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';

@Component({
  selector: 'jhi-meeting-update',
  templateUrl: './meeting-update.component.html'
})
export class MeetingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    title: [],
    date: [],
    timeZone: [],
    url: []
  });

  constructor(protected meetingService: MeetingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meeting }) => {
      this.updateForm(meeting);
    });
  }

  updateForm(meeting: IMeeting): void {
    this.editForm.patchValue({
      id: meeting.id,
      title: meeting.title,
      date: meeting.date != null ? meeting.date.format(DATE_TIME_FORMAT) : null,
      timeZone: meeting.timeZone,
      url: meeting.url
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meeting = this.createFromForm();
    if (meeting.id !== undefined) {
      this.subscribeToSaveResponse(this.meetingService.update(meeting));
    } else {
      this.subscribeToSaveResponse(this.meetingService.create(meeting));
    }
  }

  private createFromForm(): IMeeting {
    return {
      ...new Meeting(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      date: this.editForm.get(['date'])!.value != null ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      timeZone: this.editForm.get(['timeZone'])!.value,
      url: this.editForm.get(['url'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeeting>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
