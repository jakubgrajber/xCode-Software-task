import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NumbersInputComponent } from './numbers-input.component';

describe('NumbersInputComponent', () => {
  let component: NumbersInputComponent;
  let fixture: ComponentFixture<NumbersInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NumbersInputComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NumbersInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
