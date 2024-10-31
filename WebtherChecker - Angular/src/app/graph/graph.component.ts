import { Component } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-graph',
  standalone: true,
  imports: [BaseChartDirective],
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css']
})
export class GraphComponent {
  public chartType: string = 'bar';
  public datasets: Array<any> = [
    { data: [65, 59, 80, 81, 56], label: 'Series A' },
    { data: [28, 48, 40, 19, 86], label: 'Series B' }
  ];
  public labels: Array<string> = ['January', 'February', 'March', 'April', 'May'];
  public options: any = {
    responsive: true,
    scales: {
      x: {
        title: {
          display: true,
          text: 'Months'
        }
      },
      y: {
        title: {
          display: true,
          text: 'Values'
        }
      }
    }
  };
}
