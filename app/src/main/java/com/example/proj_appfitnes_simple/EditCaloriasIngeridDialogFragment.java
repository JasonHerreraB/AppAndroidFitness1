package com.example.proj_appfitnes_simple;

import android.annotation.SuppressLint;
import android.content.Context;
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



public class EditCaloriasIngeridDialogFragment extends DialogFragment  {

    private Datos datos = new Datos();
    private TextView tv_currentCalories;
    private TextInputEditText et_newCalories;
    private Button btn_submit;

    private OnDataChangeListener onDataChangeListener;

    public interface OnDataChangeListener {
        void onDataChanged(Datos newDatos);
    }


    public EditCaloriasIngeridDialogFragment(Datos datos, OnDataChangeListener listener) {
        this.datos = datos;
        this.onDataChangeListener = listener;
    }



    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_calorias_ingerid, container, false);


        tv_currentCalories = view.findViewById(R.id.textView12);
        et_newCalories = view.findViewById(R.id.et_newCalorias);
        btn_submit = view.findViewById(R.id.button4);

        tv_currentCalories.setText(String.valueOf(datos.getCaloriaing()));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCaloriesStr = et_newCalories.getText().toString();
                if (!newCaloriesStr.isEmpty()) {
                    int newCalories = Integer.parseInt(newCaloriesStr);
                    datos.setCaloriaing(datos.getCaloriaing() + newCalories);
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

