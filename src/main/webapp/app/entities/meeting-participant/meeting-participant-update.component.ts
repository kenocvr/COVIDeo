import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMeetingParticipant, MeetingParticipant } from 'app/shared/model/meeting-participant.model';
import { MeetingParticipantService } from './meeting-participant.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMeeting } from 'app/shared/model/meeting.model';
import { MeetingService } from 'app/entities/meeting/meeting.service';

type SelectableEntity = IUser | IMeeting;

@Component({
  selector: 'jhi-meeting-participant-update',
  templateUrl: './meeting-participant-update.component.html'
})
export class MeetingParticipantUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  meetings: IMeeting[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [],
    meetingId: []
  });

  constructor(
    protected meetingParticipantService: MeetingParticipantService,
    protected userService: UserService,
    protected meetingService: MeetingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ meetingParticipant }) => {
      this.updateForm(meetingParticipant);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.meetingService
        .query()
        .pipe(
          map((res: HttpResponse<IMeeting[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IMeeting[]) => (this.meetings = resBody));
    });
  }

  updateForm(meetingParticipant: IMeetingParticipant): void {
    this.editForm.patchValue({
      id: meetingParticipant.id,
      userId: meetingParticipant.userId,
      meetingId: meetingParticipant.meetingId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const meetingParticipant = this.createFromForm();
    if (meetingParticipant.id !== undefined) {
      this.subscribeToSaveResponse(this.meetingParticipantService.update(meetingParticipant));
    } else {
      this.subscribeToSaveResponse(this.meetingParticipantService.create(meetingParticipant));
    }
  }

  private createFromForm(): IMeetingParticipant {
    return {
      ...new MeetingParticipant(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      meetingId: this.editForm.get(['meetingId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeetingParticipant>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
