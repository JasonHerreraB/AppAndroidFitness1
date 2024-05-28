package com.example.proj_appfitnes_simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import android.widget.Button;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    Datos datos;

    TextView tv_caloing;
    TextView tv_caloquem;
    TextView tv_aguaing;
    TextView tv_difcalo;

    Button bt_editcaloing;
    Button bt_editcaloquem;
    Button bt_editaguaing;
    Button bt_reinday;
    SharedPreferences preferences;


    private DatosDao userManager;

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


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar vistas
        tv_caloing = view.findViewById(R.id.tv_home_caloing);
        tv_caloquem = view.findViewById(R.id.tv_home_caloquem);
        tv_aguaing = view.findViewById(R.id.tv_home_aguaing);
        tv_difcalo = view.findViewById(R.id.tv_home_difcalo);
        bt_editcaloing = view.findViewById(R.id.btn_home_editcaloing);
        bt_editcaloquem = view.findViewById(R.id.btn_home_editcaloquem);
        bt_editaguaing = view.findViewById(R.id.btn_home_editaguaing);
        bt_reinday = view.findViewById(R.id.btn_home_reday);

        // Obtener userId de SharedPreferences

        int userId;

        if (getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE) != null) {
            preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            userId = preferences.getInt("user_id", -1);
        } else
            userId = -1;

        if (userId != -1) {

            if (userManager.comprobarDatosPorUsuario(userId)) {
                datos = userManager.obtenerDatosPorUsuario(userId);
            } else {
                datos = new Datos();
                datos.setUserId(userId);
                userManager.insertarDatos(datos);
                datos = userManager.obtenerDatosPorUsuario(userId);
            }
            actualizarDatosVista(datos);
        } else {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
        }

        // Configurar listeners de los botones
        bt_editcaloing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCaloriasIngeridDialogFragment dialog = new EditCaloriasIngeridDialogFragment(datos, new EditCaloriasIngeridDialogFragment.OnDataChangeListener() {
                    @Override
                    public void onDataChanged(Datos newDatos) {
                        datos = newDatos;
                        datos.setUserId(userId);
                        userManager.actualizarDatos(datos);
                        actualizarDatosVista(datos);
                    }
                });
                dialog.show(getParentFragmentManager(), "EditCaloriesDialog");
            }
        });

        bt_editcaloquem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCaloriasQuemDialogFragment dialog = new EditCaloriasQuemDialogFragment(datos, new EditCaloriasQuemDialogFragment.OnDataChangeListener() {
                    @Override
                    public void onDataChanged(Datos newDatos) {
                        datos = newDatos;
                        datos.setUserId(userId);
                        userManager.actualizarDatos(datos);
                        actualizarDatosVista(datos);
                    }
                });
                dialog.show(getParentFragmentManager(), "EditCaloriesQuemDialog");
            }
        });

        bt_editaguaing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos.setLitrosing(datos.getLitrosing() + 0.25f);
                datos.setUserId(userId);
                userManager.actualizarDatos(datos);
                actualizarDatosVista(datos);
            }
        });

        bt_reinday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datos = new Datos();
                datos.setUserId(userId);
                userManager.insertarDatos(datos);
                datos = userManager.obtenerDatosPorUsuario(userId);
                actualizarDatosVista(datos);
            }
        });

        return view;
    }

    public void actualizarDatosVista(Datos dts) {
        if (dts != null)
            datos = dts;
        tv_caloing.setText(String.valueOf(datos.getCaloriaing()));
        tv_caloquem.setText(String.valueOf(datos.getCaloriaquem()));
        tv_aguaing.setText(String.valueOf(datos.getLitrosing()));
        // Calcula la diferencia calórica
        datos.setDifcaloricas(datos.getCaloriaing() - datos.getCaloriaquem());
        userManager.actualizarDatos(datos);
        tv_difcalo.setText(String.valueOf(datos.getDifcaloricas()));
    }
}
