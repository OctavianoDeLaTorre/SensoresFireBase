package com.demo.sensores20.ui.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.demo.sensores20.R;
import com.demo.sensores20.sensores.MySensor;
import com.demo.sensores20.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HistorialFragment extends Fragment implements ValueEventListener {

    private DatabaseReference mDatabase;

    private TextView txtAcelerometro;
    private TextView txtLuz;
    private TextView txtGravedad;
    private TextView txtProximidad;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_historial, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query mQueryRef = mDatabase.child(HomeFragment.BASE_DE_DATOS);

        mQueryRef.addValueEventListener(this);

        configViews(root);
        return root;
    }

    private void configViews(View root) {
        txtAcelerometro = root.findViewById(R.id.txtAcelerometroHistorial);
        txtLuz = root.findViewById(R.id.txtLuzHistorial);;
        txtGravedad = root.findViewById(R.id.txtGravedadHistorial);;
        txtProximidad = root.findViewById(R.id.txtproximidadHistorial);;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot registro : dataSnapshot.getChildren()){
            MySensor sensor = registro.getValue(MySensor.class);

           switch (sensor.getId()){
               case "Acelerometro":
                   txtAcelerometro.setText(sensor.getValor());
                   break;

               case "Sensor de luz":
                   txtLuz.setText(sensor.getValor());
                   break;

               case "Sensor de gravedad":
                   txtGravedad.setText(sensor.getValor());
                   break;

               case "Sensor de proximidad":
                   txtProximidad.setText(sensor.getValor());
                   break;

               default:
                   break;
           }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}