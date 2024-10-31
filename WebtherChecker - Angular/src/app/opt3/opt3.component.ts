import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-opt3',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './opt3.component.html',
  styleUrls: ['./opt3.component.css']
})
export class Opt3Component {
  head = 'By dates';

  dateStart: string = '';
  dateEnd: string = '';

  @Output() datesSelected = new EventEmitter<{ start: string; end: string }>();

  onSubmit() {
    this.datesSelected.emit({ start: this.dateStart, end: this.dateEnd });
  }
}
