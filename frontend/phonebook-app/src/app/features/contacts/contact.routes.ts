import { Routes } from '@angular/router';
import { ContactListPage } from './pages/contact-list/contact-list';
import { ContactFormPage } from './pages/contact-form/contact-form';

export const CONTACT_ROUTES: Routes = [
  { path: '', component: ContactListPage },
  { path: 'new', component: ContactFormPage },
  { path: 'edit/:id', component: ContactFormPage },
];
