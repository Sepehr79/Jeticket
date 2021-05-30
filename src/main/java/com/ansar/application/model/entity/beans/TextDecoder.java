package com.ansar.application.model.entity.beans;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

public class TextDecoder {

    private TextDecoder(){

    }

    public static String decode(String text, int count){

        for (int i = 0 ; i < count ; i++){
            text = new String(Base64.getDecoder().decode(text.getBytes()));
        }

        return text;
    }

    public static String encode(String text, int count){

        for (int i = 0 ; i < count ; i++){
            text = Base64.getEncoder().encodeToString(text.getBytes());
        }

        return text;
    }


}
