import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-opt1',
  standalone: true,
  templateUrl: './opt1.component.html',
  styleUrls: ['./opt1.component.css'],
  imports: [FormsModule]
})
export class Opt1Component {
  head = 'By Coordinates';
  lat: number | null = null;
  long: number | null = null;

  @Output() coordinatesSubmitted = new EventEmitter<{ lat: number; long: number }>();

  onSubmit() {
    if (this.lat !== null && this.long !== null) {
      this.coordinatesSubmitted.emit({ lat: this.lat, long: this.long });
    }
  }
}
