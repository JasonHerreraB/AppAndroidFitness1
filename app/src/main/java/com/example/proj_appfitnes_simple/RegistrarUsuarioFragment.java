package com.example.proj_appfitnes_simple;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class RegistrarUsuarioFragment extends Fragment {

    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrar_usuario, container, false);

        etName = view.findViewById(R.id.inpt_Reg_Nombre);
        etEmail = view.findViewById(R.id.inpt_Reg_Correo);
        etPassword = view.findViewById(R.id.inpt_Reg_Passwd);
        btnRegister = view.findViewById(R.id.btn_Reg_Registrarse);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Validar el correo electrónico
                if (!Utils.isValidEmail(email)) {
                    Toast.makeText(getActivity(), "Por favor, introduce un correo electrónico válido", Toast.LENGTH_SHORT).show();
                    return; // No proceder con el registro
                }

                long userId = userManager.registerUser(name, email, password);
                if (userId != -1) {
                    Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
                } else {
                    Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static class Utils {
        // Expresión regular para validar el formato de correo electrónico
        private static final Pattern EMAIL_PATTERN =
                Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$");

        public static boolean isValidEmail(String email) {
            return EMAIL_PATTERN.matcher(email).matches();
        }
    }
}