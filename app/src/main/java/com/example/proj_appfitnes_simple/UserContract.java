package com.example.proj_appfitnes_simple;

import android.provider.BaseColumns;

public class UserContract {

    public static class UserEntry implements BaseColumns {

        // User Table Columns
        public static final String COLUMN_USER_ID = "_id";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PASSWORD = "password";
        public static final String COLUMN_USER_WEIGHT = "weight";
        public static final String COLUMN_USER_HEIGHT = "height";
        public static final String COLUMN_USER_AGE = "age";

        // Datos Table Columns
        public static final String COLUMN_DATOS_ID = "_id";
        public static final String COLUMN_DATOS_USER_ID = "user_id";
        public static final String COLUMN_DATOS_CALORIA_ING = "caloria_ing";
        public static final String COLUMN_DATOS_CALORIA_QUEM = "caloria_quem";
        public static final String COLUMN_DATOS_LITROS_ING = "litros_ing";
        public static final String COLUMN_DATOS_DIF_CALORICAS = "dif_caloricas";

        // Table names
        public static final String TABLE_USER = "user";
        public static final String TABLE_DATOS = "datos";

    }

}
