import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoviDeoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { MeetingParticipantDeleteDialogComponent } from 'app/entities/meeting-participant/meeting-participant-delete-dialog.component';
import { MeetingParticipantService } from 'app/entities/meeting-participant/meeting-participant.service';

describe('Component Tests', () => {
  describe('MeetingParticipant Management Delete Component', () => {
    let comp: MeetingParticipantDeleteDialogComponent;
    let fixture: ComponentFixture<MeetingParticipantDeleteDialogComponent>;
    let service: MeetingParticipantService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CoviDeoTestModule],
        declarations: [MeetingParticipantDeleteDialogComponent]
      })
        .overrideTemplate(MeetingParticipantDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeetingParticipantDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeetingParticipantService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
