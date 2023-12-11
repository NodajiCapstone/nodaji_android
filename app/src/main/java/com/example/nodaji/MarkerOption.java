package com.example.nodaji;

public class MarkerOption {
    private int id;
    private String name;
    public MarkerOption(int id, String name){
        this.id=id;
        this.name=name;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name=name;
    }
    @Override
    public String toString(){
        return name;
    }

}
