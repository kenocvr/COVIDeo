import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CoviDeoTestModule } from '../../../test.module';
import { MeetingParticipantUpdateComponent } from 'app/entities/meeting-participant/meeting-participant-update.component';
import { MeetingParticipantService } from 'app/entities/meeting-participant/meeting-participant.service';
import { MeetingParticipant } from 'app/shared/model/meeting-participant.model';

describe('Component Tests', () => {
  describe('MeetingParticipant Management Update Component', () => {
    let comp: MeetingParticipantUpdateComponent;
    let fixture: ComponentFixture<MeetingParticipantUpdateComponent>;
    let service: MeetingParticipantService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingParticipantUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeetingParticipantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeetingParticipantUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeetingParticipantService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeetingParticipant(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeetingParticipant();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
