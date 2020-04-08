import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeetingParticipant } from 'app/shared/model/meeting-participant.model';

type EntityResponseType = HttpResponse<IMeetingParticipant>;
type EntityArrayResponseType = HttpResponse<IMeetingParticipant[]>;

@Injectable({ providedIn: 'root' })
export class MeetingParticipantService {
  public resourceUrl = SERVER_API_URL + 'api/meeting-participants';

  constructor(protected http: HttpClient) {}

  create(meetingParticipant: IMeetingParticipant): Observable<EntityResponseType> {
    return this.http.post<IMeetingParticipant>(this.resourceUrl, meetingParticipant, { observe: 'response' });
  }

  update(meetingParticipant: IMeetingParticipant): Observable<EntityResponseType> {
    return this.http.put<IMeetingParticipant>(this.resourceUrl, meetingParticipant, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeetingParticipant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeetingParticipant[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
