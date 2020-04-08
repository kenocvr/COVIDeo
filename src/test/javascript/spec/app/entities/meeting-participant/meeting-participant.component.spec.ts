import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoviDeoTestModule } from '../../../test.module';
import { MeetingParticipantComponent } from 'app/entities/meeting-participant/meeting-participant.component';
import { MeetingParticipantService } from 'app/entities/meeting-participant/meeting-participant.service';
import { MeetingParticipant } from 'app/shared/model/meeting-participant.model';

describe('Component Tests', () => {
  describe('MeetingParticipant Management Component', () => {
    let comp: MeetingParticipantComponent;
    let fixture: ComponentFixture<MeetingParticipantComponent>;
    let service: MeetingParticipantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingParticipantComponent],
        providers: []
      })
        .overrideTemplate(MeetingParticipantComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeetingParticipantComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeetingParticipantService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MeetingParticipant(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.meetingParticipants && comp.meetingParticipants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
