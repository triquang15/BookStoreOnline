package com.triquang.service;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class CommonUnity {
	static void  generateCountryList(HttpServletRequest request) {
		String[] counttryCodes = Locale.getISOCountries();

		Map<String, String> mapCountries = new TreeMap<>();

		for (String countryCode : counttryCodes) {
			Locale locale = new Locale("", countryCode);

			String code = locale.getCountry();
			String name = locale.getDisplayCountry();

			mapCountries.put(name, code);

		}
		
		request.setAttribute("mapCountries", mapCountries);
	}
}
