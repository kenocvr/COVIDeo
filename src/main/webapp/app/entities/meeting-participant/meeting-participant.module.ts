import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoviDeoSharedModule } from 'app/shared/shared.module';
import { MeetingParticipantComponent } from './meeting-participant.component';
import { MeetingParticipantDetailComponent } from './meeting-participant-detail.component';
import { MeetingParticipantUpdateComponent } from './meeting-participant-update.component';
import { MeetingParticipantDeleteDialogComponent } from './meeting-participant-delete-dialog.component';
import { meetingParticipantRoute } from './meeting-participant.route';

@NgModule({
  imports: [CoviDeoSharedModule, RouterModule.forChild(meetingParticipantRoute)],
  declarations: [
    MeetingParticipantComponent,
    MeetingParticipantDetailComponent,
    MeetingParticipantUpdateComponent,
    MeetingParticipantDeleteDialogComponent
  ],
  entryComponents: [MeetingParticipantDeleteDialogComponent]
})
export class CoviDeoMeetingParticipantModule {}
