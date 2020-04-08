import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeetingParticipant } from 'app/shared/model/meeting-participant.model';
import { MeetingParticipantService } from './meeting-participant.service';

@Component({
  templateUrl: './meeting-participant-delete-dialog.component.html'
})
export class MeetingParticipantDeleteDialogComponent {
  meetingParticipant?: IMeetingParticipant;

  constructor(
    protected meetingParticipantService: MeetingParticipantService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meetingParticipantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('meetingParticipantListModification');
      this.activeModal.close();
    });
  }
}
