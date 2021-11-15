package com.flcd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteAutomata {
    //    Q : Finite set of states.
    //    Σ : set of Input Symbols.
    //    q : Initial state.
    //    F : set of Final States.
    //    δ : Transition Function.
    private List<String> states = new ArrayList<>();
    private List<String> inputSymbols = new ArrayList<>();
    private String initialState;
    private List<String> finalStates = new ArrayList<>();
    private Map<Pair<String, String>, List<String>> transitions = new HashMap<>();
    private boolean isDeterministic = true;

    public FiniteAutomata() {
        readFile();
    }

    private void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("FA.in"))) {
            states.addAll(List.of(br.readLine().strip().split(" ")));
            inputSymbols.addAll(List.of(br.readLine().strip().split(" ")));
            initialState = br.readLine().strip();
            finalStates.addAll(List.of(br.readLine().strip().split(" ")));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.strip().split(" ");
                List<String> list = new ArrayList<>();
                list.add(splitLine[2]);
                Pair<String, String> pair = new Pair<>(splitLine[0], splitLine[1]);
                if (transitions.containsKey(pair)) {
                    transitions.get(pair).add(splitLine[2]);
                    isDeterministic = false;
                } else transitions.put(pair, list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String verify() {
        List<String> sequence = List.of(new Scanner(System.in).nextLine().strip().split(" "));
        System.out.println(sequence);
        if (isDeterministic) {
            String currentState = initialState;
            for (String seqItem : sequence) {
                List<String> aux = transitions.get(new Pair<>(currentState, seqItem));
                if (aux != null) currentState = aux.get(0);
                else return "Not accepted";
            }
            if (finalStates.contains(currentState)) return "Accepted";
            else return "Not accepted";
        }
        return "Not deterministic";
    }

    public void run(int option) {
        switch (option) {
            case 1 -> System.out.println(states);
            case 2 -> System.out.println(inputSymbols);
            case 3 -> System.out.println(transitions);
            case 4 -> System.out.println(finalStates);
            case 5 -> System.out.println(verify());
            default -> System.out.println("Try again. (1-4)");
        }
    }
}
