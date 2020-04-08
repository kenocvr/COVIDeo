import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoviDeoTestModule } from '../../../test.module';
import { MeetingDetailComponent } from 'app/entities/meeting/meeting-detail.component';
import { Meeting } from 'app/shared/model/meeting.model';

describe('Component Tests', () => {
  describe('Meeting Management Detail Component', () => {
    let comp: MeetingDetailComponent;
    let fixture: ComponentFixture<MeetingDetailComponent>;
    const route = ({ data: of({ meeting: new Meeting(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeetingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeetingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load meeting on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meeting).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
