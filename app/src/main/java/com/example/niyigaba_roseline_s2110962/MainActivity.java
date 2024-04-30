package com.example.niyigaba_roseline_s2110962;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView cityTextView;
    private TextView mainTempTextView;
    private ImageView mainIconImageView;
    private TextView[] dayNamesTextViews;
    private ImageView[] dayIconsImageViews;
    private TextView[] dayMinTempTextViews;
    private TextView[] dayMaxTempTextViews;
    private TextView[] dayConditionTextViews;
    private ProgressBar progressBar;

    private Model model;

    private BottomNavigationView bottomNavigation;

    // Map to store weather conditions and their corresponding icons
    private Map<String, Integer> weatherIcons;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views and progress bar
        initializeViews();

        // Initialize weather icons map

        Map<String, Integer> weatherIcons = new HashMap<>();

            weatherIcons.put("Snow", R.drawable.snow);
            weatherIcons.put("Tornado", R.drawable.tornado);
            weatherIcons.put("Thunder", R.drawable.thunder);
            weatherIcons.put("Rain_Thunder", R.drawable.rain_thunder);
            weatherIcons.put("Rain", R.drawable.rain);
            weatherIcons.put("Overcast", R.drawable.overcast);
            weatherIcons.put("Night_Snow_Thunder", R.drawable.night_snow_thunder);
            weatherIcons.put("Night_Rain", R.drawable.night_rain);
            weatherIcons.put("Night_Clear", R.drawable.night_clear);
            weatherIcons.put("Night_Partial_Cloud", R.drawable.night_partial_cloud);
            weatherIcons.put("Mist", R.drawable.mist);
            weatherIcons.put("Sleet", R.drawable.sleet);
            weatherIcons.put("Night_Sleet", R.drawable.night_sleet);
            weatherIcons.put("sunny", R.drawable.sunny);
            weatherIcons.put("clear sky", R.drawable.sunny);


    model= new Model();

        // Set New York as the default city
        String defaultCity = "New York";
        String defaultRssFeedUrl = model.getRSSFeedUrl(defaultCity);

        // Execute AsyncTask to fetch and parse the RSS feed for New York
        new FetchRSSFeedTask(defaultCity).execute(defaultRssFeedUrl);

        // Set onClickListeners for city buttons
        findViewById(R.id.button_new_york).setOnClickListener(v -> onCityButtonClick("New York"));
        findViewById(R.id.button_london).setOnClickListener(v -> onCityButtonClick("London"));
        findViewById(R.id.button_glasgow).setOnClickListener(v -> onCityButtonClick("Glasgow"));
        findViewById(R.id.button_oman).setOnClickListener(v -> onCityButtonClick("Oman"));
        findViewById(R.id.button_mauritius).setOnClickListener(v -> onCityButtonClick("Mauritius"));
        findViewById(R.id.button_bangladesh).setOnClickListener(v -> onCityButtonClick("Bangladesh"));



        // make the textview clickable
        // Get the TextView by id
        TextView forecastTrigger = findViewById(R.id.forecastTrigger);

        // Create a SpannableString to underline the text
        SpannableString spannableString = new SpannableString("View Three days Forecast");
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        forecastTrigger.setText(spannableString);

        // Set OnHoverListener for the TextView
        forecastTrigger.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        // Change text color to blue when hovered on
                        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        forecastTrigger.setText(spannableString);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        // Change text color back to white when hover exits
                        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        forecastTrigger.setText(spannableString);
                        break;
                }
                return false;
            }
        });

        // Set click listener to show the three-day forecast
        forecastTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to show three-day forecast
                showthreedayforecast(v);
            }
        });

        // Initialize bottom navigation
//        bottomNavigation = findViewById(R.id.bottom_navigation);
//        // Define the method to handle navigation item selection
//        bottomNavigation.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) () -> {
//
//            MenuItem item = null;
//            switch (item.getItemId()) {
//                 case R.id.menu_home:
//                      Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
//                      startActivity(homeIntent);
//                     return true;
//                 case R.id.menu_cities:
//
//                      Intent citiesIntent = new Intent(MainActivity.this, CitiesPage.class);
//                      startActivity(citiesIntent);
//                     return true;
//                 case R.id.menu_settings:
//
//                      Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
//                      startActivity(settingsIntent);
//                     return true;
//                 default:
//                     return false;
//             }});
        // end of on create
    }

    private void initializeViews() {
        cityTextView = findViewById(R.id.city_text);
        mainTempTextView = findViewById(R.id.temperature_text);
        mainIconImageView = findViewById(R.id.weather_icon);
        dayNamesTextViews = new TextView[]{findViewById(R.id.day1_name), findViewById(R.id.day2_name), findViewById(R.id.day3_name)};
        dayIconsImageViews = new ImageView[]{findViewById(R.id.day1_icon), findViewById(R.id.day2_icon), findViewById(R.id.day3_icon)};
        dayMinTempTextViews = new TextView[]{findViewById(R.id.day1_min_temp), findViewById(R.id.day2_min_temp), findViewById(R.id.day3_min_temp)};
        dayMaxTempTextViews = new TextView[]{findViewById(R.id.day1_max_temp), findViewById(R.id.day2_max_temp), findViewById(R.id.day3_max_temp)};
        dayConditionTextViews = new TextView[]{findViewById(R.id.day1_condition), findViewById(R.id.day2_condition), findViewById(R.id.day3_condition)};        progressBar = findViewById(R.id.progressBar);
    }

    private void onCityButtonClick(String city) {
        String rssFeedUrl = model.getRSSFeedUrl(city);
        new FetchRSSFeedTask(city).execute(rssFeedUrl);
    }

        @SuppressLint("StaticFieldLeak")
    private class FetchRSSFeedTask extends AsyncTask<String, Void, WeatherData> {
            private String context;

            public FetchRSSFeedTask(String context) {
                this.context = context;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected WeatherData doInBackground(String... urls) {
                String rssFeedData = fetchRSSFeed(urls[0]);
                return parseWeatherData(rssFeedData);
            }

            private String fetchRSSFeed(String urlString) {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    return readStream(in);
                } catch (IOException e) {
                    Log.e("FetchRSSFeedTask", "Error fetching RSS feed: " + e.getMessage());
                    return null;
                }
            }

            private String readStream(InputStream is) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    return sb.toString();
                } catch (IOException e) {
                    Log.e("FetchRSSFeedTask", "Error reading stream: " + e.getMessage());
                    return null;
                }
            }

            private WeatherData parseWeatherData(String rssFeedData) {
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(new StringReader(rssFeedData));

                    // Parse weather data and return WeatherData object
                    return model.parseWeatherData(parser);
                } catch (XmlPullParserException e) {
                    Log.e("ParseWeatherTask", "Error parsing RSS feed: " + e.getMessage());
                    return null;
                }
            }


            @Override
            protected void onPostExecute(WeatherData weatherData) {
                super.onPostExecute(weatherData);
                // Hide progress bar
                progressBar.setVisibility(View.GONE);
                // Update UI with weather data
                if (weatherData != null) {
                    updateWeatherUI(weatherData);
                } else {
                    // Handle null weather data, show error message, retry, etc.
                }
            }
        }
    private void updateWeatherUI(WeatherData weatherData) {

        if (weatherData == null) {
            Log.e(TAG, "Weather data is null. Unable to update UI.");
            return;
        }

        // Update main weather information
        cityTextView.setText(weatherData.getCityName());
        mainTempTextView.setText(weatherData.getMainTemp());
        setWeatherIcon(weatherData.getMainIcon(), mainIconImageView);


        if (weatherData != null) {

            String[] dayNames = weatherData.getDayNames();
            String[] dayMinTemps = weatherData.getDayMinTemps();
            String[] dayMaxTemps = weatherData.getDayMaxTemps();
            String[] dayConditions = weatherData.getDayConditions();
            String[] dayIcons = weatherData.getDayIcons();

        // Get references to the UI elements for the three-day forecast
        TextView[] dayNameTextViews = {findViewById(R.id.day1_name), findViewById(R.id.day2_name), findViewById(R.id.day3_name)};
        TextView[] dayMinTempTextViews = {findViewById(R.id.day1_min_temp), findViewById(R.id.day2_min_temp), findViewById(R.id.day3_min_temp)};
        TextView[] dayMaxTempTextViews = {findViewById(R.id.day1_max_temp), findViewById(R.id.day2_max_temp), findViewById(R.id.day3_max_temp)};
        ImageView[] dayIconImageViews = {findViewById(R.id.day1_icon), findViewById(R.id.day2_icon), findViewById(R.id.day3_icon)};
        TextView[] dayConditionTextViews = {findViewById(R.id.day1_condition), findViewById(R.id.day2_condition), findViewById(R.id.day3_condition)};

        // Update the UI elements with the data from the WeatherData object
            for (int i = 0; i < 3; i++) {
                if (dayNameTextViews[i] != null) {
                    dayNameTextViews[i].setText(dayNames[i]);
                }
                if (dayMinTempTextViews[i] != null) {
                    dayMinTempTextViews[i].setText(dayMinTemps[i]);
                }
                if (dayMaxTempTextViews[i] != null) {
                    dayMaxTempTextViews[i].setText(dayMaxTemps[i]);
                }
                if (dayIconImageViews[i] != null) {
                    dayIconImageViews[i].setImageResource(Integer.parseInt(dayIcons[i]));
                }
                if (dayConditionTextViews[i] != null) {
                    dayConditionTextViews[i].setText(dayConditions[i]);
                }
            }
        } else {
            // Handle case where weatherData is null (e.g., show error message, hide forecast UI elements, etc.)
        }

        // Set additional weather details
        TextView humidityTextView = findViewById(R.id.humidityTextView);
        TextView pressureTextView = findViewById(R.id.pressureTextView);
        TextView windDirectionTextView = findViewById(R.id.windDirectionTextView);
        TextView windSpeedTextView = findViewById(R.id.windSpeedTextView);
        TextView visibilityTextView = findViewById(R.id.visibilityTextView);
        TextView uvRiskTextView = findViewById(R.id.uvRiskTextView);
        TextView pollutionTextView = findViewById(R.id.pollutionTextView);
        TextView sunriseTextView = findViewById(R.id.sunriseTextView);
        TextView sunsetTextView = findViewById(R.id.sunsetTextView);

        // Update additional weather details
        humidityTextView.setText("Humidity: " + weatherData.getHumidity());
        pressureTextView.setText("Pressure: " + weatherData.getPressure());
        windDirectionTextView.setText("Wind Direction: " + weatherData.getWindDirection());
        windSpeedTextView.setText("Wind Speed: " + weatherData.getWindSpeed());
        visibilityTextView.setText("Visibility: " + weatherData.getVisibility());
        uvRiskTextView.setText("UV Risk: " + weatherData.getUvRisk());
        pollutionTextView.setText("Pollution: " + weatherData.getPollution());
        sunriseTextView.setText("Sunrise: " + weatherData.getSunrise());
        sunsetTextView.setText("Sunset: " + weatherData.getSunset());
    }


    private void setWeatherIcon(String icon, ImageView imageView) {
        if (weatherIcons != null && weatherIcons.containsKey(icon)) {
            int drawableId = weatherIcons.get(icon);
            Drawable drawable = AppCompatResources.getDrawable(MainActivity.this, drawableId);
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        } else {
            // Handle case when icon is not found in weatherIcons map
            // For example, set a default icon
            imageView.setImageResource(R.drawable.default_icon1);
        }

        }







   // pop up the three day forecast
    public void showthreedayforecast(View view) {
        // Inflate the pop-up layout
        View popupView = getLayoutInflater().inflate(R.layout.three_day_forecast, null);

        // Create a PopupWindow with the inflated layout
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Set background color for the pop-up window
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Show the pop-up window at the center of the screen
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

}
