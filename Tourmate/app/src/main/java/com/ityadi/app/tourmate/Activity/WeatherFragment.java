package com.ityadi.app.tourmate.Activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ityadi.app.tourmate.ApiHelper.CurrentWeatherApi;
import com.ityadi.app.tourmate.Common.CommonMethod;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.TempConvertor;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Weather.CurrentWeatherArrayList;

import java.io.InputStream;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {
    public String location;
    String CITY,ICON,MAIN,DESCRIPTION;
    Double TEMP_KELVIN,TEMP_CELCIOUS,TEMP_FARENHITE,PRESSURE,WIN_SPEED,WIN_DEG;
    int HUMIDITY;

    TextView cityTV,timeTV,temparatureTV,headingTV,descriptionTV,pressureTV,humidityTV;
    ImageView weatherImage;
    CommonMethod commonMethod;

    public WeatherFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        ((Dashboard) getActivity()).fab.hide();

        final TempConvertor tempConvertor = new TempConvertor();
        commonMethod =  new CommonMethod();

        //come from TravelEventDetails Fragment with bundle location
        Bundle bundle = this.getArguments();
        location = bundle.getString("location");
        ////

        // Current Weather
        CurrentWeatherApi currentWeatherApi = Network.createWeatherService(CurrentWeatherApi.class);
        Call<CurrentWeatherArrayList> weatherCall = currentWeatherApi.getWeather("%"+location);
        weatherCall.enqueue(new Callback<CurrentWeatherArrayList>() {
            @Override
            public void onResponse(Call<CurrentWeatherArrayList> call, Response<CurrentWeatherArrayList> response) {
                CurrentWeatherArrayList responseBody=response.body();
                if (responseBody == null) {
                    Toast.makeText(getActivity(),"Data can not be retrived.",Toast.LENGTH_SHORT).show();
                }
                else {
                    cityTV = (TextView) rootView.findViewById(R.id.cityTV);
                    timeTV = (TextView) rootView.findViewById(R.id.timeTV);
                    temparatureTV = (TextView) rootView.findViewById(R.id.temparatureTV);
                    headingTV = (TextView) rootView.findViewById(R.id.headingTV);
                    descriptionTV = (TextView) rootView.findViewById(R.id.descriptionTV);
                    pressureTV = (TextView) rootView.findViewById(R.id.pressureTV);
                    humidityTV = (TextView) rootView.findViewById(R.id.humidityTV);

                    CITY = responseBody.getName();
                    CITY = CITY.replace("Bangshal", "Dhaka");
                    Date dt = new java.util.Date((long) responseBody.getDt() * 1000);
                    ICON = "http://openweathermap.org/img/w/"+responseBody.getWeather().get(0).getIcon()+".png";
                    new DownloadImageTask((ImageView) rootView.findViewById(R.id.weatherImage)).execute(ICON);

                    MAIN = responseBody.getWeather().get(0).getMain();
                    DESCRIPTION = responseBody.getWeather().get(0).getDescription();

                    PRESSURE =  responseBody.getMain().getPressure();
                    HUMIDITY =  responseBody.getMain().getHumidity();
                    WIN_SPEED = responseBody.getWind().getSpeed();
                    WIN_DEG = responseBody.getWind().getDeg();

                    TEMP_KELVIN =  responseBody.getMain().getTemp();
                    TEMP_CELCIOUS =  tempConvertor.getCelcious(TEMP_KELVIN);
                    TEMP_FARENHITE =  tempConvertor.getFarenhite(TEMP_KELVIN);

                    cityTV.setText(CITY);
                    timeTV.setText(commonMethod.weatherDateTime(dt));
                    temparatureTV.setText(String.format("%1$,.2f", TEMP_CELCIOUS)+(char) 0x00B0+"C");
                    headingTV.setText(MAIN);
                    descriptionTV.setText(DESCRIPTION);
                    pressureTV.setText(PRESSURE+" hpa");
                    humidityTV.setText(HUMIDITY +" %");
                }
            }
            @Override
            public void onFailure(Call<CurrentWeatherArrayList> call, Throwable t) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }
        });


        // forcast Weather







        ((Dashboard) getActivity()).setTitle("Weather");
        ((Dashboard) getActivity()).fab.hide();

        //Toast.makeText(getActivity(), location+" -> "+TEMP_KELVIN, Toast.LENGTH_LONG).show();
        return rootView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
