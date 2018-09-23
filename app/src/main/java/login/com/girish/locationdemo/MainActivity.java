package login.com.girish.locationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Double lon = location.getLongitude();
            Double lat = location.getLatitude();
            textView.setText(lat + "," + lon);
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
        textView = findViewById(R.id.textView);
    }

    public void fetch(View view) {
        //1. create an object of LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //2. to last knownlocation use getLastKnownlocation
        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //3. or use requestLocationUpdates to fetch location periodically
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
           //ask user to grant permission
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}
            ,1234);

        }else {
            showLocation();
        }
    }

    public void showLocation(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1234:
                if (grantResults.length > 0 
                        && grantResults[0] ==  PackageManager.PERMISSION_GRANTED 
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    showLocation();
                }else {
                    Toast.makeText(this, "permission not granted...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
