package br.com.bossini.ciclodevida2gpsccp3anmca;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView coordenadasTextView;
    private LocationManager locationManager;


    private LocationListener locationListener =
            new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat =
                            location.getLatitude();
                    double lon =
                            location.getLongitude();
                    coordenadasTextView.
                            setText(String.
                                    format("Lat:%.2f, Long:%.2f",
                                            lat, lon));
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordenadasTextView = findViewById(R.id.coordenadasTextView);
        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.
                checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions
                    (this,
                            new String
                                    []{Manifest.
                                    permission.
                                    ACCESS_FINE_LOCATION}, 567124 );
        }
        else{
            locationManager.
                    requestLocationUpdates(
                            LocationManager.
                                    GPS_PROVIDER, 0,
                            0, locationListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.
                removeUpdates(locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 567124){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.
                        checkSelfPermission(this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
                    locationManager.
                            requestLocationUpdates(
                                    LocationManager.
                                            GPS_PROVIDER, 0,
                                    0, locationListener);

                }

            }
            else{
                Toast.makeText(this,
                        "Sem GPS não rola",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
