export interface Contact {
  id: number;
  name: string;
  email: string;
  mobile: string;
  phone?: string;
  favorite: 'Y' | 'N';
  active: 'Y' | 'N';
  createdAt: string;
}
