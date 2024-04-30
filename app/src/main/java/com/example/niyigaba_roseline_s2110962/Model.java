package com.example.niyigaba_roseline_s2110962;
//Names:Niyigaba_Roseline
//StudentId:S2110962
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, Integer> weatherIcons;


    String getRSSFeedUrl(String city) {
        String rssFeedUrl;
        switch (city) {
            case "London":
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743";
                break;
            case "Glasgow":
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579";
                break;
            case "Oman":
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286";
                break;
            case "Mauritius":
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
                break;
            case "Bangladesh":
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241";
                break;
            default:
                rssFeedUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581";
                break;
        }
        return rssFeedUrl;
    }

//    // New method to get the RSS feed URL for main weather information
//    public String getMainWeatherRSSFeedUrl(String city) {
//        String mainWeatherUrl;
//        switch (city) {
//            case "London":
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2643743";
//                break;
//            case "Glasgow":
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/2648579";
//                break;
//            case "Oman":
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/287286";
//                break;
//            case "Mauritius":
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154";
//                break;
//            case "Bangladesh":
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/1185241";
//                break;
//            default:
//                mainWeatherUrl = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/5128581";
//                break;
//        }
//        return mainWeatherUrl;
//    }


    String extractHumidity(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Humidity: ");
            if (startIndex != -1) {
                startIndex += 10; // Move startIndex to the beginning of humidity value
                int endIndex = description.indexOf('%', startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Return an empty string or handle the case appropriately
    }

    String extractPressure(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Pressure: ");
            if (startIndex != -1) {
                startIndex += 9; // Move startIndex to the beginning of pressure value
                int endIndex = description.indexOf("hPa", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Return an empty string or handle the case appropriately
    }

    String extractWindDirection(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Wind Direction: ");
            if (startIndex != -1) {
                startIndex += 16;
                int endIndex = description.indexOf(", Wind Speed:", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractWindSpeed(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Wind Speed: ");
            if (startIndex != -1) {
                startIndex += 12;
                int endIndex = description.indexOf("mph", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractVisibility(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Visibility: ");
            if (startIndex != -1) {
                startIndex += 12;
                int endIndex = description.indexOf(", Pressure:", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractUVRisk(String description) {
        if (description != null) {
            int startIndex = description.indexOf("UV Risk: ");
            if (startIndex != -1) {
                startIndex += 9;
                int endIndex = description.indexOf(", Pollution:", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractPollution(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Pollution: ");
            if (startIndex != -1) {
                startIndex += 11;
                int endIndex = description.indexOf(", Sunrise:", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractSunrise(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Sunrise: ");
            if (startIndex != -1) {
                startIndex += 9;
                int endIndex = description.indexOf(" EDT", startIndex);
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }

    String extractSunset(String description) {
        if (description != null) {
            int startIndex = description.indexOf("Sunset: ");
            if (startIndex != -1) {
                startIndex += 8;
                int endIndex = description.lastIndexOf(" EDT");
                if (endIndex != -1) {
                    return description.substring(startIndex, endIndex).trim();
                }
            }
        }
        return ""; // Or handle the case appropriately
    }
    String extractDayMaxTemp(String description, String dayName) {
        // Extract day's maximum temperature from description
        // Logic based on how the maximum temperature is represented in the RSS feed
        String maxTemp = "";
        String searchTitle = dayName + " Maximum Temperature: ";
        int startIndex = description.indexOf(searchTitle);
        if (startIndex != -1) {
            startIndex += searchTitle.length();
            int endIndex = description.indexOf("°C", startIndex);
            if (endIndex != -1) {
                maxTemp = description.substring(startIndex, endIndex).trim();
            }
        }
        return maxTemp;
    }


    String extractDayMinTemp(String description) {
        // Extract day's minimum temperature from description
        // Logic based on how the minimum temperature is represented in the RSS feed
        String minTemp = "";
        String searchTitle = " Minimum Temperature: ";
        int startIndex = description.indexOf(searchTitle);
        if (startIndex != -1) {
            startIndex += searchTitle.length();
            int endIndex = description.indexOf("°C", startIndex);
            if (endIndex != -1) {
                minTemp = description.substring(startIndex, endIndex).trim();
            }
        }
        return minTemp;
    }

    String extractDayName(String description) {
        // Extract day name from description
        // Logic based on how the day names are represented in the RSS feed
        String dayName = "";
        String searchTitle = "day";
        int startIndex = description.indexOf(searchTitle);
        if (startIndex != -1) {
            int endIndex = description.indexOf(":", startIndex);
            if (endIndex != -1) {
                dayName = description.substring(startIndex, endIndex).trim();
            }
        }
        return dayName;
    }

    String extractMainTemperature(String description) {
        // Extract main temperature from description
        int startIndex = description.indexOf("Minimum Temperature: ");
        if (startIndex != -1) {
            startIndex += "Minimum Temperature: ".length();
            int endIndex = description.indexOf("Â°C", startIndex);
            if (endIndex != -1) {
                return description.substring(startIndex, endIndex).trim();
            }
        }
        return "";
    }

    String extractMainIcon(String description) {
        // Convert the description to lowercase for case-insensitive comparisons
        description = description.toLowerCase();

        // Check for different weather conditions and return the corresponding icon
        if (description.contains("snow")) {
            return "snow"; // Icon for snow
        } else if (description.contains("tornado")) {
            return "tornado"; // Icon for tornado
        } else if (description.contains("thunder")) {
            return "thunder"; // Icon for thunder
        } else if (description.contains("rain") && description.contains("thunder")) {
            return "rain_thunder"; // Icon for rain with thunder
        } else if (description.contains("rain")) {
            return "rain"; // Icon for rain
        } else if (description.contains("overcast")) {
            return "overcast"; // Icon for overcast weather
        } else if (description.contains("night") && description.contains("snow") && description.contains("thunder")) {
            return "night_snow_thunder"; // Icon for night with snow and thunder
        } else if (description.contains("night") && description.contains("rain")) {
            return "night_rain"; // Icon for night with rain
        } else if (description.contains("night") && description.contains("clear")) {
            return "night_clear"; // Icon for clear night
        } else if (description.contains("night") && description.contains("partial") && description.contains("cloud")) {
            return "night_partial_cloud"; // Icon for night with partial clouds
        } else if (description.contains("mist")) {
            return "mist"; // Icon for mist
        } else if (description.contains("sleet")) {
            return "sleet"; // Icon for sleet
        } else if (description.contains("night") && description.contains("sleet")) {
            return "night_sleet"; // Icon for night with sleet
        } else {
            // Default icon if weather condition is not recognized
            return "default_icon1"; // Replace "default_icon" with the icon you want to use for unknown conditions
        }
    }


    String extractDayIcon(String description) {
        // Extract day weather icon from description
        // Add your logic here based on how the icons are represented in the RSS feed

        if (description.contains("Clear Sky")) {
            return "clear_sky_icon";
        } else if (description.contains("Light Rain")) {
            return "light_rain_icon";
        } else if (description.contains("Heavy Rain")) {
            return "heavy_rain_icon";
        } else if (description.contains("Cloudy")) {
            return "cloudy_icon";
        } else if (description.contains("Snow")) {
            return "snow_icon";
        } else if (description.contains("Tornado")) {
            return "tornado_icon";
        } else if (description.contains("Thunder")) {
            return "thunder_icon";
        } else if (description.contains("Rain_Thunder")) {
            return "rain_thunder_icon";
        } else if (description.contains("Overcast")) {
            return "overcast_icon";
        } else if (description.contains("Night_Snow_Thunder")) {
            return "night_snow_thunder_icon";
        } else if (description.contains("Night_Rain")) {
            return "night_rain_icon";
        } else if (description.contains("Night_Clear")) {
            return "night_clear_icon";
        } else if (description.contains("Night_Partial_Cloud")) {
            return "night_partial_cloud_icon";
        } else if (description.contains("Mist")) {
            return "mist_icon";
        } else if (description.contains("Sleet")) {
            return "sleet_icon";
        } else if (description.contains("Night_Sleet")) {
            return "night_sleet_icon";
        } else if (description.contains("Sunny") || description.contains("Clear sky")) {
            return "sunny_icon";
        } else {
            // Default icon if weather condition is not recognized
            return "default_icon1";
        }
    }

    public WeatherData parseWeatherData(XmlPullParser parser) {
        try {
            String cityName = "";
            String date="";
            String mainCondition="";
            String mainTemp = "";
            String mainIcon = "";
            String humidity = "";
            String pressure = "";
            String windDirection = "";
            String windSpeed = "";
            String visibility = "";
            String sunrise = "";
            String sunset = "";
            String pollution = "";
            String uvRisk = "";
            String[] dayNames = new String[3];
            String[] dayMinTemps = new String[3];
            String[] dayMaxTemps = new String[3];
            String[] dayIcons = new String[3];
            int dayIndex = 0;
            String description = "";

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && dayIndex < 3) {
                if (eventType == XmlPullParser.START_TAG) {
                    String tag = parser.getName();
                    if (tag.equals("title")) {
                        cityName = parser.nextText().trim();
                    } else if (tag.equals("description")) {
                        description = parser.nextText().trim();
                        mainTemp = extractMainTemperature(description);
                        mainIcon = extractMainIcon(description);
                        dayNames[dayIndex] = extractDayName(description);
                        dayMinTemps[dayIndex] = extractDayMinTemp(description);
                        dayMaxTemps[dayIndex] = extractDayMaxTemp(description, dayNames[dayIndex]);
                        dayIcons[dayIndex] = extractDayIcon(description);
                        dayIndex++;
                        if (dayIndex == 3) {
                            break;
                        }
                    }
                    humidity = extractHumidity(description);
                    pressure = extractPressure(description);
                    windDirection = extractWindDirection(description);
                    windSpeed = extractWindSpeed(description);
                    visibility = extractVisibility(description);
                    uvRisk = extractUVRisk(description);
                    pollution = extractPollution(description);
                    sunrise = extractSunrise(description);
                    sunset = extractSunset(description);
                }
                eventType = parser.next();
            }

            // Create and return a new WeatherData object
            WeatherData weatherData = new WeatherData(cityName, date, mainCondition, mainTemp, mainIcon,
                    dayNames, dayMinTemps, dayMaxTemps, dayIcons,
                    humidity, pressure, windDirection, windSpeed, visibility,
                    uvRisk, pollution, sunrise, sunset);
            return weatherData;

        } catch (XmlPullParserException | IOException e) {
            Log.e("Model", "Error parsing weather data: " + e.getMessage());
            return null;
        }
    }

}
