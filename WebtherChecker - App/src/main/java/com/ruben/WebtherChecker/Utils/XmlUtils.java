package com.ruben.WebtherChecker.Utils;


import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ruben.WebtherChecker.Entities.Location;

public class XmlUtils {
	
	public static Document readXml(String url) {
		
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			return dBuilder.parse(url);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Location readWeatherXml(String url) {
			Document doc = readXml(url);
			doc.getDocumentElement().normalize();
			NodeList nList;
			Location location = new Location();

			
			nList = doc.getElementsByTagName("city");
			Element eElement = (Element) nList.item(0);
			location.setCity(eElement.getAttribute("name"));
			location.getSys().setCountry(eElement.getElementsByTagName("country").item(0).getTextContent());

			nList = doc.getElementsByTagName("temperature");
            eElement = (Element) nList.item(0);
            location.getMain().setTemp(Double.parseDouble(eElement.getAttribute("value")));
            
			nList = doc.getElementsByTagName("humidity");
            eElement = (Element) nList.item(0);
            location.getMain().setHumidity(Integer.parseInt(eElement.getAttribute("value")));

            nList = doc.getElementsByTagName("clouds");
            eElement = (Element) nList.item(0);
            location.getWeather().get(0).setMain(eElement.getAttribute("name"));

            nList = doc.getElementsByTagName("weather");
            eElement = (Element) nList.item(0);
            location.getWeather().get(0).setDescription(eElement.getAttribute("value"));	
            
            return location;
	}
}
