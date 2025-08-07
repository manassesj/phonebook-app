import { Routes } from '@angular/router';
import { authGuard } from './shared/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'contacts',
    pathMatch: 'full'
  },
  {
    path: 'contacts',
    loadChildren: () =>
      import('./features/contacts/contact.routes').then(m => m.CONTACT_ROUTES),
    canActivate: [authGuard],
  },
];
