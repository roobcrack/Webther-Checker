import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Location } from './location';
import { Opt1Component } from './opt1/opt1.component';
import { Opt2Component } from './opt2/opt2.component';
import { Opt3Component } from './opt3/opt3.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

interface Article {
  author: string;
  title: string;
  url: string;
  publishedAt: string;
  urlToImage?: string;
}

interface TemperatureData {
  fecha: string;
  intervalos: { hora: string; temperatura: string }[];
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule, Opt1Component, Opt2Component, Opt3Component],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Weather Checker';
  selectedTab: string = 'Opt1';
  weatherData: Location | null = null;
  serializedData: Location[] = [];
  data5days: { date: string; main: { temp: number; humidity: number } }[] = [];
  dates: Date[] = [];
  articles: Article[] = [];
  temperatureData: TemperatureData[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchSerializedData();
    this.fetchNewsData();
  }

  getWeatherDataByCoordinates(latitude: number, longitude: number) {
    const apiUrl = `http://localhost:8080/api/coords?latitude=${latitude}&longitude=${longitude}`;
    this.http.get<Location>(apiUrl).subscribe({
      next: (response) => {
        this.weatherData = response;
        this.get5daysDataCoords(latitude, longitude);
      },
      error: (err) => {
        console.error('Error fetching weather data:', err);
      },
    });
    setTimeout(() => {
      this.fetchSerializedData();
    }, 1000);
  }

  getWeatherDataByCity(city: string) {
    const apiUrl = `http://localhost:8080/api/namec?city=${city}`;
    this.http.get<Location>(apiUrl).subscribe({
      next: (response) => {
        this.weatherData = response;
        this.get5daysDataName(city);
      },
      error: (err) => {
        console.error('Error fetching weather data:', err);
      },
    });
    setTimeout(() => {
      this.fetchSerializedData();
    }, 1000);
  }

  fetchSerializedData() {
    const apiUrl = 'http://localhost:8080/api/listdata';
    this.http.get<Location[]>(apiUrl).subscribe({
      next: (data) => {
        this.serializedData = data;
      },
      error: (err) => {
        console.error('Error fetching serialized data:', err);
      },
    });
  }

  get5daysDataCoords(latitude: number, longitude: number) {
    const apiUrl = `http://localhost:8080/api/coords5days?latitude=${latitude}&longitude=${longitude}`;
    this.http.get<{ list: Location[] }>(apiUrl).subscribe({
      next: (data) => {
        this.data5days = this.prepare5DaysData(data.list);
      },
      error: (err) => {
        console.error('Error fetching data for the next 5 days:', err);
      },
    });
  }

  get5daysDataName(city: string) {
    const apiUrl = `http://localhost:8080/api/names5days?city=${city}`;
    this.http.get<{ list: Location[] }>(apiUrl).subscribe({
      next: (data) => {
        this.data5days = this.prepare5DaysData(data.list);
      },
      error: (err) => {
        console.error('Error fetching data for the next 5 days:', err);
      },
    });
  }

  prepare5DaysData(data: Location[]): { date: string; main: { temp: number; humidity: number } }[] {
    const today = new Date();
    return data.map((location, index) => {
      const futureDate = new Date(today);
      futureDate.setDate(today.getDate() + index);
      return {
        date: futureDate.toLocaleDateString(),
        main: {
          temp: location.main.temp,
          humidity: location.main.humidity,
        },
      };
    });
  }

  fetchNewsData() {
    const apiUrl = 'http://localhost:8080/api/news';
    this.http.get<{ articles: Article[] }>(apiUrl).subscribe({
      next: (response) => {
        this.articles = response.articles;
      },
      error: (err) => {
        console.error('Error fetching news data:', err);
      },
    });
  }

  fetchTemperatureData(from: string, to: string) {
    const apiUrl = `http://localhost:8080/api/timelist?from=${from}&to=${to}`;
    this.http.get<{ fechas: TemperatureData[] }>(apiUrl).subscribe({
      next: (response) => {
        this.temperatureData = response.fechas;
      },
      error: (err) => {
        console.error('Error fetching temperature data:', err);
      },
    });
  }

  selectOption(option: string) {
    this.selectedTab = option;
  }

  handleDatesSelected(dates: { start: string; end: string }) {
    this.fetchTemperatureData(dates.start, dates.end);
  }
}
