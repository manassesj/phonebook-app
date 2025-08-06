import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';
import { Contact } from '../../../../core/models/contact.model';

@Component({
  selector: 'app-contact-card',
  standalone: true,
  imports: [
    CommonModule,
    NgbTooltipModule
  ],
  templateUrl: './contact-card.html',
  styleUrls: ['./contact-card.scss']
})
export class ContactCardComponent {
  @Input() contact!: Contact;
  @Output() selectEdit = new EventEmitter<number>();
  @Output() selectDeactivate = new EventEmitter<number>();
  @Output() selectToggleFavorite = new EventEmitter<Contact>();

  onEdit() {
    this.selectEdit.emit(this.contact.id);
  }

  onDeactivate() {
    this.selectDeactivate.emit(this.contact.id);
  }

  onToggleFavorite() {
    this.selectToggleFavorite.emit(this.contact);
  }
}
