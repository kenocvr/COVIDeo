import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';

@Component({
  templateUrl: './meeting-delete-dialog.component.html'
})
export class MeetingDeleteDialogComponent {
  meeting?: IMeeting;

  constructor(protected meetingService: MeetingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meetingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('meetingListModification');
      this.activeModal.close();
    });
  }
}
