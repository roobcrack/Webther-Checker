package com.ruben.WebtherChecker.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.WebtherChecker.Entities.Location;
import com.ruben.WebtherChecker.Entities.NewsResponse;
import com.ruben.WebtherChecker.Entities.WeatherResponse;
import com.ruben.WebtherChecker.Utils.FilesUtils;
import com.ruben.WebtherChecker.Utils.JsonUtils;
import com.ruben.WebtherChecker.Utils.SerializeUtils;
import com.ruben.WebtherChecker.Utils.XmlUtils;

@RestController
@RequestMapping("/api")
public class WeatherController {
	private String apiKey = "30db9cad9c2ad82cbdf3d193bcd6c197";

    @GetMapping("/coords")
    public String getLocation(@RequestParam("latitude") double latitude,
                              @RequestParam("longitude") double longitude) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude
                     + "&lon=" + longitude + "&lang=es&units=metric&appid=" + apiKey;

        Location location = JsonUtils.readGeneric(url, Location.class);
        SerializeUtils.serializeObject("src\\main\\java\\com\\ruben\\WebtherChecker\\Files\\Serialized\\" + LocalDate.now() + ".txt", location);
        return JsonUtils.toJson(location);
    }

    @GetMapping("/namec")
    public String getLocationName(@RequestParam("city") String city) {
		String apiKey = "30db9cad9c2ad82cbdf3d193bcd6c197";
		String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city
				+ "&lang=es&units=metric&mode=xml&appid=" + apiKey;

		Location location = XmlUtils.readWeatherXml(url);
        SerializeUtils.serializeObject("src\\main\\java\\com\\ruben\\WebtherChecker\\Files\\Serialized\\" + LocalDate.now() + ".txt", location);
        return JsonUtils.toJson(location);
    }

    @GetMapping("/listdata")
    public String getSerializedData() {
        List<Location> locations = SerializeUtils.deserializeAllFiles("src\\main\\java\\com\\ruben\\WebtherChecker\\Files\\Serialized");

        return JsonUtils.toJson(locations);
    }


    @GetMapping("/coords5days")
    public String getNext5DaysCoords(@RequestParam("latitude") double latitude,
            					@RequestParam("longitude") double longitude) {
        return getNext5Days("https://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&units=metric&lon="+longitude+"&appid="+apiKey);
    }

    @GetMapping("/names5days")
    public String getNext5DaysName(@RequestParam("city") String city) {
        return getNext5Days("https://api.openweathermap.org/data/2.5/forecast?q=+"+city+"&units=metric&appid="+apiKey);
    }

    public String getNext5Days(String url) {
        WeatherResponse response = JsonUtils.readGeneric(url, WeatherResponse.class);
        List<Location> allData = response.getList();
        List<Location> selectedData = new ArrayList<>();

        for (int i = 0; i < allData.size(); i += 8) {
            selectedData.add(allData.get(i));
        }
        WeatherResponse selectedResponse = new WeatherResponse();
        selectedResponse.setList(selectedData);

        return JsonUtils.toJson(selectedResponse);
    }

    @GetMapping("/news")
    public String getNews() {
        NewsResponse response = JsonUtils.readGeneric("https://newsapi.org/v2/top-headlines?q=temperature&apiKey=beaff37a2a4d41e6ac7021dbb7441bbc",
        		NewsResponse.class);

        return JsonUtils.toJson(response);
    }

    @GetMapping("/timelist")
    public static String readTemperaturesFile(@RequestParam("from") String dateFrom,
                                              @RequestParam("to") String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(dateFrom, formatter);
        LocalDate endDate = LocalDate.parse(dateTo, formatter);
        
        // Limitar el intervalo a un máximo de 10 días
        endDate = startDate.plusDays(Math.min(9, ChronoUnit.DAYS.between(startDate, endDate)));

        List<Map<String, Object>> fechas = startDate.datesUntil(endDate.plusDays(1))
            .<Map<String, Object>>map(date -> {
                String currentDate = date.format(formatter);
                Map<String, String> temperaturesMap = FilesUtils.returnFileListed("src\\main\\java\\com\\ruben\\WebtherChecker\\Files\\datos.csv").stream()
                    .filter(e -> e.split(",")[5].contains(currentDate))
                    .collect(Collectors.toMap(e -> e.substring(81, 86), e -> e.split(",")[6], (o, n) -> o, TreeMap::new));

                List<Map<String, Object>> intervalos = temperaturesMap.entrySet().stream()
                    .<Map<String, Object>>map(entry -> Map.of("hora", entry.getKey(), "temperatura", entry.getValue()))
                    .collect(Collectors.toList());

                return Map.of("fecha", currentDate, "intervalos", intervalos);
            }).collect(Collectors.toList());

        return JsonUtils.toJson(Map.of("fechas", fechas));
    }
}

