import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-opt2',
  standalone: true,
  templateUrl: './opt2.component.html',
  styleUrls: ['./opt2.component.css'],
  imports: [FormsModule]
})
export class Opt2Component {
  head = 'By City';
  city: string = '';

  @Output() citySubmitted = new EventEmitter<string>();

  onSubmit() {
    if (this.city) {
      console.log('Submitting city:', this.city); 
      this.citySubmitted.emit(this.city);
    }
  }
}
