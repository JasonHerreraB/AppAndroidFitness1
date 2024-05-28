package com.example.proj_appfitnes_simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proj_appfitnes_simple.DatosDao.*;


public class IniciarSesionFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private DatosDao userManager;
    Usuario user;

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
        View view = inflater.inflate(R.layout.fragment_iniciar_sesion, container, false);

        etEmail = view.findViewById(R.id.inpt_InSes_Email);
        etPassword = view.findViewById(R.id.inpt_InSes_Passwd);
        btnLogin = view.findViewById(R.id.btn_InSes_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (userManager.loginUser(email, password)) {
                    user = userManager.obtenerUsuarioPorEmailYPassword(email, password);
                    guardarUsuarioEnPreferencias(user.getId());
                    // Navigate to home fragment
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                } else {
                    Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void guardarUsuarioEnPreferencias(int userId) {
        Context context = getActivity(); // Obtener el contexto de la actividad
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("user_id", userId);
            editor.apply();
        }
    }
}