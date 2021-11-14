package com.flcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiniteAutomata {
//    Q : Finite set of states.
//    Σ : set of Input Symbols.
//    q : Initial state.
//    F : set of Final States.
//    δ : Transition Function.
    private List<String> fa = new ArrayList<>();
    private List<String> states = new ArrayList<>();
    private List<String> inputSymbols = new ArrayList<>();
    private String initialState;
    private List<Pair<String, Integer>> transitionFunction = new ArrayList<>();

    public FiniteAutomata() {
        readFile();
    }

    private void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("FA.in"))) {
            fa = List.of(br.readLine().strip().split(","));
            states = List.of(br.readLine().strip().split(","));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run(int option) {
        switch (option) {
            case 1 -> System.out.println("1");
            case 2 -> System.out.println("2");
            case 3 -> System.out.println("3");
            case 4 -> System.out.println("4");
            default -> System.out.println("Try again. (1-4)");
        }
    }
}
