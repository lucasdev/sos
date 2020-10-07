package com.example.sos.utils;

import com.example.sos.dto.MarcaDTO;
import com.example.sos.dto.PatrimonioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjSerializer {

    public static String MarcaToJson(MarcaDTO dto) {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = Obj.writeValueAsString(dto);
            System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }

    public static String PatrimonioToJson(PatrimonioDTO dto) {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = Obj.writeValueAsString(dto);
            System.out.println(jsonStr);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }
}
