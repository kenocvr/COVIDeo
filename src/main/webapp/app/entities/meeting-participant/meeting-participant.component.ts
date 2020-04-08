import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMeetingParticipant } from 'app/shared/model/meeting-participant.model';
import { MeetingParticipantService } from './meeting-participant.service';
import { MeetingParticipantDeleteDialogComponent } from './meeting-participant-delete-dialog.component';

@Component({
  selector: 'jhi-meeting-participant',
  templateUrl: './meeting-participant.component.html'
})
export class MeetingParticipantComponent implements OnInit, OnDestroy {
  meetingParticipants?: IMeetingParticipant[];
  eventSubscriber?: Subscription;

  constructor(
    protected meetingParticipantService: MeetingParticipantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.meetingParticipantService.query().subscribe((res: HttpResponse<IMeetingParticipant[]>) => {
      this.meetingParticipants = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMeetingParticipants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMeetingParticipant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMeetingParticipants(): void {
    this.eventSubscriber = this.eventManager.subscribe('meetingParticipantListModification', () => this.loadAll());
  }

  delete(meetingParticipant: IMeetingParticipant): void {
    const modalRef = this.modalService.open(MeetingParticipantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.meetingParticipant = meetingParticipant;
  }
}
