import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ContactListPage } from './contact-list';


describe('ContactList', () => {
  let component: ContactListPage;
  let fixture: ComponentFixture<ContactListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactListPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
