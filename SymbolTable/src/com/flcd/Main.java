package com.flcd;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();
        List<String> tokens = Arrays.asList("test","012","\"string\"","false","1","2","3","4");
        tokens.forEach(st::add);
        tokens.forEach(t -> System.out.println(st.get(t)));
        System.out.println(st);
    }
}
