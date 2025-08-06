import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactFilter } from './contact-filter';

describe('ContactFilter', () => {
  let component: ContactFilter;
  let fixture: ComponentFixture<ContactFilter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactFilter]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactFilter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
