package com.ruben.WebtherChecker.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location implements Serializable{
	private static final long serialVersionUID = 1L;

	@SerializedName("name")
	private String city;
    private Main main;
    private Sys sys;
    private List<Weather> weather;

    public Location() {
    	this.main = new Main();
    	this.sys = new Sys();
    	this.weather = new ArrayList<>();
    	this.weather.add(new Location.Weather());
    }
    public static class Sys implements Serializable{
    	private String country;

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
    }

    public static class Main implements Serializable{
    	private static final long serialVersionUID = 1L;

        private double temp;
    	private int humidity;

    	public double getTemp() {
			return temp;
		}

		public void setTemp(double temp) {
			this.temp = temp;
		}

		public int getHumidity() {
			return humidity;
		}

		public void setHumidity(int humidity) {
			this.humidity = humidity;
		}

		public double getTempKelvin() {
    		return temp + 273.15;
    	}
    }

    public static class Weather implements Serializable{
    	private static final long serialVersionUID = 1L;

        private String main;
		private String description;

        public String getMain() {
			return main;
		}
		public void setMain(String main) {
			this.main = main;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
    }


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public Main getMain() {
		return main;
	}


	public void setMain(Main main) {
		this.main = main;
	}


	public Sys getSys() {
		return sys;
	}


	public void setSys(Sys sys) {
		this.sys = sys;
	}


	public List<Weather> getWeather() {
		return weather;
	}


	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}


	@Override
	public String toString() {
		return "City: " + city + "(" + sys.country + ")" +
				"\nTemperature: " +  main.temp + "º (" + String.format("%.2f", main.getTempKelvin()) + "K)" +
				"\nHumidity= " + main.humidity + "%" +
				"\nWeather: " + weather.get(0).main + "(" +  weather.get(0).description + ")";
	}
}
