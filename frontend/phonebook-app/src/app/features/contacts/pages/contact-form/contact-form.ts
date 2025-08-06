import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { ContactApiService } from '../../../../core/services/contact-api.service';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, NgbAlertModule],
  templateUrl: './contact-form.html',
  styleUrls: ['./contact-form.scss'],
})
export class ContactFormPage implements OnInit {
  private fb = inject(FormBuilder);
  private contactApi = inject(ContactApiService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  form!: FormGroup;
  isEditMode = false;
  contactId?: number;
  error = '';
  loading = false;

  ngOnInit() {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      email: ['', [Validators.email, Validators.maxLength(255)]],
      mobile: ['', [Validators.required, Validators.pattern(/^\d{10,11}$/)]],
      phone: ['', [Validators.pattern(/^\d{10}$/)]],
      is_favorite: ['N'],
      is_active: ['Y'],
    });

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.contactId = +params['id'];
        this.loadContact(this.contactId);
      }
    });
  }

  loadContact(id: number) {
    this.loading = true;
    this.contactApi.getById(id).subscribe({
      next: (contact) => {
        this.form.patchValue(contact);
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load contact.';
        this.loading = false;
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.error = '';

    const contactData = this.form.value;
    console.log(contactData)

    const request$ = this.isEditMode
      ? this.contactApi.update(this.contactId!, contactData)
      : this.contactApi.create(contactData);

    request$.subscribe({
      next: () => this.router.navigate(['/contacts']),
      error: () => {
        this.error = 'Failed to save contact. Check if the cell phone number is unique.';
        this.loading = false;
      }
    });
  }
}
