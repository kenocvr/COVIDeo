import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeetingParticipant, MeetingParticipant } from 'app/shared/model/meeting-participant.model';
import { MeetingParticipantService } from './meeting-participant.service';
import { MeetingParticipantComponent } from './meeting-participant.component';
import { MeetingParticipantDetailComponent } from './meeting-participant-detail.component';
import { MeetingParticipantUpdateComponent } from './meeting-participant-update.component';

@Injectable({ providedIn: 'root' })
export class MeetingParticipantResolve implements Resolve<IMeetingParticipant> {
  constructor(private service: MeetingParticipantService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeetingParticipant> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meetingParticipant: HttpResponse<MeetingParticipant>) => {
          if (meetingParticipant.body) {
            return of(meetingParticipant.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MeetingParticipant());
  }
}

export const meetingParticipantRoute: Routes = [
  {
    path: '',
    component: MeetingParticipantComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meetingParticipant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeetingParticipantDetailComponent,
    resolve: {
      meetingParticipant: MeetingParticipantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meetingParticipant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeetingParticipantUpdateComponent,
    resolve: {
      meetingParticipant: MeetingParticipantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meetingParticipant.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeetingParticipantUpdateComponent,
    resolve: {
      meetingParticipant: MeetingParticipantResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meetingParticipant.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
