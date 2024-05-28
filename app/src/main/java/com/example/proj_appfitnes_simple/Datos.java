package com.example.proj_appfitnes_simple;

import java.io.Serializable;

public class Datos implements Serializable {

    private int id;
    private int userId;
    private int caloriaing;
    private int caloriaquem;
    private float litrosing;
    private int difcaloricas;

    public Datos(){
        caloriaing = 0;
        caloriaquem = 0;
        litrosing = 0;
        difcaloricas = 0;
    }

    public int getDifcaloricas() {
        return difcaloricas;
    }

    public void setDifcaloricas(int difcaloricas) {
        this.difcaloricas = difcaloricas;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCaloriaing() { return caloriaing; }
    public void setCaloriaing(int caloriaing) { this.caloriaing = caloriaing; }
    public int getCaloriaquem() { return caloriaquem; }
    public void setCaloriaquem(int caloriaquem) { this.caloriaquem = caloriaquem; }
    public float getLitrosing() { return litrosing; }
    public void setLitrosing(float litrosing) { this.litrosing = litrosing; }

}
