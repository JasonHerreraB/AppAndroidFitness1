package com.example.proj_appfitnes_simple;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ConfiguracionFragment extends Fragment {

    private SharedPreferences preferences;
    private DatosDao userManager;
    private EditText etWeight, etHeight, etAge;
    private Button btnSave;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userManager = new DatosDao(context);
        userManager.open();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        userManager.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        int userId ;

        if (getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE) != null){
            preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            userId = preferences.getInt("user_id", -1);
        }else
            userId = -1;

        if (userId == -1) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
            return view;
        }

        etWeight = view.findViewById(R.id.et_weight);
        etHeight = view.findViewById(R.id.et_height);
        etAge = view.findViewById(R.id.et_age);
        btnSave = view.findViewById(R.id.btn_save);

        Usuario datos = userManager.obtenerUsuarioPorId(userId);
        if (datos != null) {
            etWeight.setText(String.valueOf(datos.getWeight()));
            etHeight.setText(String.valueOf(datos.getHeight()));
            etAge.setText(String.valueOf(datos.getAge()));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = etWeight.getText().toString();
                String heightStr = etHeight.getText().toString();
                String ageStr = etAge.getText().toString();

                if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(ageStr)) {
                    Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                float weight = Float.parseFloat(weightStr);
                float height = Float.parseFloat(heightStr);
                int age = Integer.parseInt(ageStr);

                datos.setWeight(weight);
                datos.setHeight(height);
                datos.setAge(age);

                userManager.ActualizarUsuario(datos);
                Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}