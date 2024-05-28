package com.example.proj_appfitnes_simple;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

public class EditCaloriasQuemDialogFragment extends DialogFragment {

    private Datos datos;
    private TextView tv_currentCalories;
    private TextInputEditText et_newCalories;
    private Button btn_submit;
    private OnDataChangeListener onDataChangeListener;

    public interface OnDataChangeListener {
        void onDataChanged(Datos newDatos);
    }

    public EditCaloriasQuemDialogFragment(Datos datos, OnDataChangeListener listener) {
        this.datos = datos;
        this.onDataChangeListener = listener;
    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_calorias_quemadas, container, false);


        tv_currentCalories = view.findViewById(R.id.tv_calquem);
        et_newCalories = view.findViewById(R.id.tv_calquem_getnewcal);
        btn_submit = view.findViewById(R.id.btn_add);

        tv_currentCalories.setText(String.valueOf(datos.getCaloriaquem()));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCaloriesStr = et_newCalories.getText().toString();
                if (!newCaloriesStr.isEmpty()) {
                    int newCalories = Integer.parseInt(newCaloriesStr);
                    datos.setCaloriaquem(datos.getCaloriaquem() + newCalories);
                    if (onDataChangeListener != null) {
                        onDataChangeListener.onDataChanged(datos);
                    }
                    dismiss();
                }
            }
        });

        return view;
    }
}

