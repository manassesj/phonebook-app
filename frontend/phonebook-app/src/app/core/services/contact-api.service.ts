import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Contact } from '../models/contact.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ContactApiService {
  private http = inject(HttpClient);
  private readonly BASE_URL = 'http://localhost:8080/api/contacts';

  getAll(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.BASE_URL);
  }

  getById(id: number): Observable<Contact> {
    return this.http.get<Contact>(`${this.BASE_URL}/${id}`);
  }

  create(contact: Partial<Contact>): Observable<Contact> {
    return this.http.post<Contact>(this.BASE_URL, contact);
  }

  update(id: number, contact: Partial<Contact>): Observable<Contact> {
    return this.http.put<Contact>(`${this.BASE_URL}/${id}`, contact);
  }

  deactivate(id: number): Observable<Contact> {
    return this.http.patch<Contact>(`${this.BASE_URL}/${id}/deactivate`, {});
  }

  toggleFavorite(id: number): Observable<Contact> {
    return this.http.patch<Contact>(`${this.BASE_URL}/${id}/favorite`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE_URL}/${id}`);
  }
}
