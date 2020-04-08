import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoviDeoTestModule } from '../../../test.module';
import { MeetingComponent } from 'app/entities/meeting/meeting.component';
import { MeetingService } from 'app/entities/meeting/meeting.service';
import { Meeting } from 'app/shared/model/meeting.model';

describe('Component Tests', () => {
  describe('Meeting Management Component', () => {
    let comp: MeetingComponent;
    let fixture: ComponentFixture<MeetingComponent>;
    let service: MeetingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingComponent],
        providers: []
      })
        .overrideTemplate(MeetingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeetingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeetingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Meeting(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.meetings && comp.meetings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
