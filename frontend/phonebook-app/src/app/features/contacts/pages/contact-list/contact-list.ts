import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ContactCardComponent } from '../../components/contact-card/contact-card';
import { ContactApiService } from '../../../../core/services/contact-api.service';
import { Contact } from '../../../../core/models/contact.model';
import { ContactFilterComponent } from '../../components/contact-filter/contact-filter';
import { finalize } from 'rxjs';
import { NgbModal, NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmModalComponent } from '../../components/confirm-modal-content/confirm-modal-content';

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    NgbModule,
    ContactFilterComponent,
    ContactCardComponent,
  ],
  templateUrl: './contact-list.html',
  styleUrls: ['./contact-list.scss'],
})
export class ContactListPage implements OnInit {
  contacts: Contact[] = [];
  filteredContacts: Contact[] = [];
  loading = false;

  searchText = '';
  selectedOption = 'All';

  constructor(
    private contactApi: ContactApiService,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.loadContacts();
  }

  loadContacts() {
    this.contactApi.getAll().subscribe({
      next: (data) => {
        this.contacts = data.filter((c) => c.active === 'Y');
        this.applyFilter();
      },
      error: (err) => {
        console.error('Error loading contacts:', err);
      },
    });
  }

  applyFilter() {
    const searchLower = this.searchText.toLowerCase();

    this.filteredContacts = this.contacts.filter((contact) => {
      const matchesSearch =
        contact.name.toLowerCase().includes(searchLower) ||
        contact.email.toLowerCase().includes(searchLower);

      let matchesOption = true;
      if (this.selectedOption === 'Favorites') {
        matchesOption = contact.favorite === 'Y';
      } else if (this.selectedOption === 'Active') {
        matchesOption = contact.active === 'Y';
      }

      return matchesSearch && matchesOption;
    });
  }

  onSearchTextChange(value: string) {
    this.searchText = value;
    this.applyFilter();
  }

  onSelectedOptionChange(value: string) {
    this.selectedOption = value;
    this.applyFilter();
  }

  trackById(index: number, contact: Contact) {
    return contact.id;
  }

  onSelectEdit(id: number) {
    this.router.navigate(['contacts', 'edit', id]);
  }
onSelectDeactivate(id: number) {
  const modalRef = this.modalService.open(ConfirmModalComponent);
  modalRef.componentInstance.message = 'Are you sure you want to deactivate this contact?';

  modalRef.result
    .then((result) => {
      if (result === 'confirm') {
        this.loading = true;
        this.contactApi
          .deactivate(id)
          .pipe(finalize(() => (this.loading = false)))
          .subscribe({
            next: () => {
              console.log('Contact deactivated successfully');
              this.loadContacts();
            },
            error: (err) => {
              console.error('Failed to deactivate contact', err);
              alert('Error while deactivating the contact');
            },
          });
      }
    })
    .catch((reason) => {
      console.log('Deactivate confirmation modal dismissed or error:', reason);
    });
}


  onSelectToggleFavorite(contact: Contact) {
    const updatedStatus = contact.favorite === 'Y' ? 'N' : 'Y';

    this.contactApi
      .toggleFavorite(contact.id)
      .pipe(finalize(() => {}))
      .subscribe({
        next: () => {
          console.log('Favorite status updated');
          contact.favorite = updatedStatus;
        },
        error: (err) => {
          console.error('Failed to update favorite status', err);
          alert('Error while updating favorite status');
        },
      });
  }
}

