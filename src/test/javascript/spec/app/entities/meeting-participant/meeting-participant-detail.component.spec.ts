import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviDeoTestModule } from '../../../test.module';
import { MeetingParticipantDetailComponent } from 'app/entities/meeting-participant/meeting-participant-detail.component';
import { MeetingParticipant } from 'app/shared/model/meeting-participant.model';

describe('Component Tests', () => {
  describe('MeetingParticipant Management Detail Component', () => {
    let comp: MeetingParticipantDetailComponent;
    let fixture: ComponentFixture<MeetingParticipantDetailComponent>;
    const route = ({ data: of({ meetingParticipant: new MeetingParticipant(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingParticipantDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeetingParticipantDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeetingParticipantDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load meetingParticipant on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meetingParticipant).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
