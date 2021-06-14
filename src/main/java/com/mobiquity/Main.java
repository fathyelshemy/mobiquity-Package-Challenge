package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

public class Main {

    public static void main(String[] args) {
        try {
            Packer.pack("C:\\Users\\Fathyelshemy8\\Desktop\\Backend code assignment - Mobiquity 2021\\src\\main\\test\\resources\\example_input");
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}
