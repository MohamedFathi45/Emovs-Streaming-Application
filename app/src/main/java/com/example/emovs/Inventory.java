package com.example.emovs;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private static Map<BigInteger , com.example.emovs.GeneralShow> inventory = new HashMap<>();

    private static Inventory inventory_instance = null;

    public static synchronized Inventory getInstance(){
        if(inventory_instance == null)
            inventory_instance = new Inventory();

        return inventory_instance;
    }
    private Inventory(){

    }

    public int getSize(){
        return inventory.size();
    }

    public com.example.emovs.GeneralShow getShow(BigInteger id){

        return inventory.get(id);
    }

    public void add(com.example.emovs.GeneralShow generalShow){
        if(inventory.get(generalShow.getId()) == null)
            inventory.put(generalShow.getId() , generalShow);
    }

}
