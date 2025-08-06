import { Routes } from '@angular/router';
import { authGuard } from './shared/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'contacts',
    canActivate: [authGuard],
    loadChildren: () =>
      import('./features/contacts/contact.routes').then(m => m.CONTACT_ROUTES),
  },
];
