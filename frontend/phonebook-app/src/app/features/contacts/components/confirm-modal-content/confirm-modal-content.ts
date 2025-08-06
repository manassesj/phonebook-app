import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirm-modal',
  standalone: true,
  imports: [CommonModule, NgbModule],
  templateUrl: './confirm-modal-content.html',
})
export class ConfirmModalComponent {
  @Input() message = '';

  constructor(public modal: NgbActiveModal) {}
}
