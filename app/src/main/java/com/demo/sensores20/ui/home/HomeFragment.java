package com.demo.sensores20.ui.home;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.demo.sensores20.R;
import com.demo.sensores20.sensores.MySensor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    private DatabaseReference mDatabase;

    private TextView txtAcelerometro;
    private TextView txtLuz;
    private TextView txtGravedad;
    private TextView txtProximidad;

    private SensorManager sensorManager;
    DecimalFormat formato1 = new DecimalFormat("#.##");
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    private Sensor sensorAcelerometro;
    private SensorEventListener oyenteAcelerometro = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            MySensor mySensor = new MySensor();
            mySensor.setNombre("Acelerometro");
            mySensor.setId(mySensor.getNombre()+dateFormat.format(new Date()));
            mySensor.setValor(formato1.format(x)+" m/s, " + formato1.format(y) +" m/s, " + formato1.format(z) +" m/s ");


            txtAcelerometro.setText(mySensor.getValor());
            mDatabase.child("demossensores").child(mySensor.getId()).setValue(mySensor);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private Sensor sensorLuz;
    private SensorEventListener oyenteLuz = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float niveLuz = event.values[0];
            txtLuz.setText(niveLuz+" lx");
            mDatabase.child("demossensores").child("Sensor de luz").setValue(new MySensor("Sensor de luz",niveLuz+" lx"));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private Sensor sensorGravedad;
    private SensorEventListener oyenteGravedad = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            txtGravedad.setText(formato1.format(x)+" m/s, " + formato1.format(y) +" m/s, " + formato1.format(z) +" m/s " );
            mDatabase.child("demossensores").child("Sensor de gravedad").setValue(new MySensor("Sensor de gravedad",formato1.format(x)+" m/s, " + formato1.format(y) +" m/s, " + formato1.format(z) +" m/s "));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private Sensor sensorProximidad;
    private SensorEventListener oyenteProximidad = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float distancia = event.values[0];
            txtProximidad.setText(distancia+" cm");
            mDatabase.child("demossensores").child("Sensor de proximidad").setValue(new MySensor("Sensor de luz",distancia+" cm"));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        configSensores();
        configViews(root);

        return root;
    }

    private void configSensores() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorGravedad = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    private void configViews(View root) {
        txtAcelerometro = root.findViewById(R.id.txtAcelerometro);
        txtLuz = root.findViewById(R.id.txtLuz);;
        txtGravedad = root.findViewById(R.id.txtGravedad);;
        txtProximidad = root.findViewById(R.id.txtproximidad);;
    }
}