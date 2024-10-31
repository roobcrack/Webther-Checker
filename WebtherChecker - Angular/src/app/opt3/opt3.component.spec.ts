import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Opt3Component } from './opt3.component';

describe('Opt3Component', () => {
  let component: Opt3Component;
  let fixture: ComponentFixture<Opt3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Opt3Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Opt3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
