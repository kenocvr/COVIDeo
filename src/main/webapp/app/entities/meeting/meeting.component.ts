import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMeeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';
import { MeetingDeleteDialogComponent } from './meeting-delete-dialog.component';

@Component({
  selector: 'jhi-meeting',
  templateUrl: './meeting.component.html'
})
export class MeetingComponent implements OnInit, OnDestroy {
  meetings?: IMeeting[];
  eventSubscriber?: Subscription;

  constructor(protected meetingService: MeetingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.meetingService.query().subscribe((res: HttpResponse<IMeeting[]>) => {
      this.meetings = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMeetings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMeeting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMeetings(): void {
    this.eventSubscriber = this.eventManager.subscribe('meetingListModification', () => this.loadAll());
  }

  delete(meeting: IMeeting): void {
    const modalRef = this.modalService.open(MeetingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.meeting = meeting;
  }
}
