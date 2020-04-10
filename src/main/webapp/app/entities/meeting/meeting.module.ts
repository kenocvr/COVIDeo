import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviDeoSharedModule } from 'app/shared/shared.module';
import { MeetingComponent } from './meeting.component';
import { MeetingDetailComponent } from './meeting-detail.component';

import { MeetingUpdateComponent } from './meeting-update.component';
import { MeetingDeleteDialogComponent } from './meeting-delete-dialog.component';
import { meetingRoute } from './meeting.route';

@NgModule({
  imports: [CoviDeoSharedModule, RouterModule.forChild(meetingRoute)],
  declarations: [MeetingComponent, MeetingDetailComponent, MeetingUpdateComponent, MeetingDeleteDialogComponent],
  entryComponents: [MeetingDeleteDialogComponent]
})
export class CoviDeoMeetingModule {}
