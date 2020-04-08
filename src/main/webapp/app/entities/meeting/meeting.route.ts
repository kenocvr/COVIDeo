import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeeting, Meeting } from 'app/shared/model/meeting.model';
import { MeetingService } from './meeting.service';
import { MeetingComponent } from './meeting.component';
import { MeetingDetailComponent } from './meeting-detail.component';
import { MeetingUpdateComponent } from './meeting-update.component';

@Injectable({ providedIn: 'root' })
export class MeetingResolve implements Resolve<IMeeting> {
  constructor(private service: MeetingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeeting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meeting: HttpResponse<Meeting>) => {
          if (meeting.body) {
            return of(meeting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Meeting());
  }
}

export const meetingRoute: Routes = [
  {
    path: '',
    component: MeetingComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meeting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeetingDetailComponent,
    resolve: {
      meeting: MeetingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meeting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeetingUpdateComponent,
    resolve: {
      meeting: MeetingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meeting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeetingUpdateComponent,
    resolve: {
      meeting: MeetingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'coviDeoApp.meeting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
