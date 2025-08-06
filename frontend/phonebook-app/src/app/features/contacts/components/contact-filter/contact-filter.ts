import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contact-filter',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact-filter.html',
  styleUrls: ['./contact-filter.scss']
})
export class ContactFilterComponent {
  @Input() searchText = '';

  @Input() filterOptions: string[] = ['All', 'Favorites', 'Active'];
  @Input() selectedOption = 'All';

  @Output() searchTextChange = new EventEmitter<string>();
  @Output() selectedOptionChange = new EventEmitter<string>();

  onSearchTextChange(value: string) {
    this.searchTextChange.emit(value);
  }

  onOptionChange(value: string) {
    this.selectedOptionChange.emit(value);
  }
}
