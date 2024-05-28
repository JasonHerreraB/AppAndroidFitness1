package com.example.proj_appfitnes_simple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.proj_appfitnes_simple.UserContract.UserEntry;

public class DatosDao {

    private SQLiteDatabase database;
    private UserDbHelper dbHelper;

    public DatosDao(Context context) {
        dbHelper = new UserDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long registerUser(String name, String email, String password) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USER_NAME, name);
        values.put(UserEntry.COLUMN_USER_EMAIL, email);
        values.put(UserEntry.COLUMN_USER_PASSWORD, password);
        return database.insert(UserEntry.TABLE_USER, null, values);
    }

    public boolean loginUser(String email, String password) {
        String[] columns = { UserEntry.COLUMN_USER_ID };
        String selection = UserEntry.COLUMN_USER_EMAIL + " = ? AND " + UserEntry.COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = database.query(UserEntry.TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount > 0;
    }

    public void ActualizarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USER_NAME, usuario.getName());
        values.put(UserEntry.COLUMN_USER_EMAIL, usuario.getEmail());
        values.put(UserEntry.COLUMN_USER_PASSWORD, usuario.getPassword());
        values.put(UserEntry.COLUMN_USER_WEIGHT, usuario.getWeight());
        values.put(UserEntry.COLUMN_USER_HEIGHT, usuario.getHeight());
        values.put(UserEntry.COLUMN_USER_AGE, usuario.getAge());
        long userId = database.update(UserEntry.TABLE_USER, values, UserEntry.COLUMN_USER_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        usuario.setId((int) userId);
    }

    public void insertarDatos(Datos datos) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_DATOS_USER_ID, datos.getUserId());
        values.put(UserEntry.COLUMN_DATOS_CALORIA_ING, datos.getCaloriaing());
        values.put(UserEntry.COLUMN_DATOS_CALORIA_QUEM, datos.getCaloriaquem());
        values.put(UserEntry.COLUMN_DATOS_LITROS_ING, datos.getLitrosing());
        values.put(UserEntry.COLUMN_DATOS_DIF_CALORICAS, datos.getDifcaloricas());
        database.insert(UserEntry.TABLE_DATOS, null, values);
    }

    public void actualizarDatos(Datos datos) {
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_DATOS_CALORIA_ING, datos.getCaloriaing());
        values.put(UserEntry.COLUMN_DATOS_CALORIA_QUEM, datos.getCaloriaquem());
        values.put(UserEntry.COLUMN_DATOS_LITROS_ING, datos.getLitrosing());
        values.put(UserEntry.COLUMN_DATOS_DIF_CALORICAS, datos.getDifcaloricas());
        database.update(UserEntry.TABLE_DATOS, values, UserEntry.COLUMN_DATOS_USER_ID + " = ? AND " + UserEntry.COLUMN_DATOS_ID + " = ?", new String[]{String.valueOf(datos.getUserId()), String.valueOf(datos.getId())} );
    }

    public Datos obtenerDatosPorUsuario(int userId) {
        Datos datos = new Datos();
        Cursor cursor = database.query(UserEntry.TABLE_DATOS, null, UserEntry.COLUMN_DATOS_USER_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToLast()) {
            datos.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_DATOS_ID)));
            datos.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_DATOS_USER_ID)));
            datos.setCaloriaing(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_DATOS_CALORIA_ING)));
            datos.setCaloriaquem(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_DATOS_CALORIA_QUEM)));
            datos.setLitrosing(cursor.getFloat(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_DATOS_LITROS_ING)));
        }
        cursor.close();
        return datos;
    }

    public Boolean comprobarDatosPorUsuario(int userId) {
        Datos datos = new Datos();
        Cursor cursor = database.query(UserEntry.TABLE_DATOS, null, UserEntry.COLUMN_DATOS_USER_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        return cursorCount > 0;
    }

    public Usuario obtenerUsuarioPorEmailYPassword(String email, String password) {
        Usuario usuario = null;
        Cursor cursor = database.query(UserEntry.TABLE_USER, null, UserEntry.COLUMN_USER_EMAIL + " = ? AND " + UserEntry.COLUMN_USER_PASSWORD + " = ?", new String[]{email, password}, null, null, null);
        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_ID)));
            usuario.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_NAME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_EMAIL)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_PASSWORD)));
            usuario.setWeight(cursor.getFloat(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_WEIGHT)));
            usuario.setHeight(cursor.getFloat(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_HEIGHT)));
            usuario.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_AGE)));
        }
        cursor.close();
        return usuario;
    }

    public Usuario obtenerUsuarioPorId(int userId) {
        Usuario usuario = null;
        Cursor cursor = database.query(UserEntry.TABLE_USER, null, UserEntry.COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_ID)));
            usuario.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_NAME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_EMAIL)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_PASSWORD)));
            usuario.setWeight(cursor.getFloat(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_WEIGHT)));
            usuario.setHeight(cursor.getFloat(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_HEIGHT)));
            usuario.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(UserEntry.COLUMN_USER_AGE)));
        }
        cursor.close();
        return usuario;
    }


}

