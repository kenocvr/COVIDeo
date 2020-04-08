import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'meeting',
        loadChildren: () => import('./meeting/meeting.module').then(m => m.CoviDeoMeetingModule)
      },
      {
        path: 'meeting-participant',
        loadChildren: () => import('./meeting-participant/meeting-participant.module').then(m => m.CoviDeoMeetingParticipantModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CoviDeoEntityModule {}
