package com.flcd;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scanner {
    private final SymbolTable stIdentifiers = new SymbolTable();
    private final SymbolTable stConstants = new SymbolTable();
    private final ArrayList<Pair<String, Integer>> pif = new ArrayList<>();

    public Scanner() {
    }

    public void scan(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            boolean error = false;
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                ArrayList<String> tokens = lineParser(line.strip());

                for (String token : tokens) {
                    if (token.equals(" ") || token.isEmpty()) {
                        continue;
                    }
                    if (isOperator(token) || isSeparator(token) || isReserved(token)) {
                        pif.add(new Pair<>(token, -1));
                    } else if (isIdentifier(token)) {
                        if (stIdentifiers.get(token).getRight() == -1) {
                            stIdentifiers.add(token);
                            pif.add(new Pair<>(token, null));
                        }
                    } else if (isConstant(token)) {
                        if (stConstants.get(token).getRight() == -1) {
                            stConstants.add(token);
                            pif.add(new Pair<>(token, null));
                        }
                    } else {
                        System.err.println("Undefined token " + token + " at line " + lineNo);
                        error = true;
                    }
                }
                lineNo++;
            }
            if(error) return;
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> lineParser(String line) {
        String[] split_line = line.split(" ");
        ArrayList<String> tokens = new ArrayList<>();
        for (String s : split_line) {
            if (isOperator(s) || isSeparator(s) || isReserved(s) || isConstant(s) || isIdentifier(s))
                tokens.add(s);
            else {
                int i = 0, j = 0;
                while (j < s.length()) {
                    if (s.charAt(j) == '\'' || s.charAt(j) == '"') {
                        j++;
                        while (j < s.length() && String.valueOf(s.charAt(j)).matches("[a-zA-Z0-9]")) j++;
                        if (j < s.length()) j++;
                    } else if (s.charAt(j) == '-') {
                        j++;
                        while (j < s.length() && String.valueOf(s.charAt(j)).matches("[0-9]")) j++;
                    } else if (String.valueOf(s.charAt(j)).matches("[^\\w]")) {
                        j++;
                        while (j < s.length() && String.valueOf(s.charAt(j)).matches("[^\\w;]")) {
                            j++;
                        }
                    } else
                        while (j < s.length() && String.valueOf(s.charAt(j)).matches("[\\w]")) {
                            j++;
                        }
                    tokens.add(s.substring(i, j));
                    i = j;
                }
            }
        }
        return tokens;
    }

    private void writeToFile() throws IOException {
        FileWriter pifFile = new FileWriter("PIF.out");
        for (Pair<String, Integer> pair : pif) {
            if (pair.getRight() == null) {
                if (isConstant(pair.getLeft())) {
                    pair.setRight(stConstants.get(pair.getLeft()).getLeft());
                } else {
                    pair.setRight(stIdentifiers.get(pair.getLeft()).getLeft());
                }
            }
            pifFile.write(pair + "\n");
        }
        pifFile.flush();
        pifFile.close();

        FileWriter stFile = new FileWriter("ST.out");
        stFile.write(stIdentifiers.toString());
        stFile.write("===\n");
        stFile.write(stConstants.toString());
        stFile.flush();
        stFile.close();
    }


    private final List<String> operators = Arrays.asList("==", "!=", "<=", ">=", "||", "&&", "+", "-", "*", "/", "=", "%", "<", ">");

    private final List<String> separators = Arrays.asList("{", "}", " ", ";", "(", ")", "[", "]");

    private final List<String> reserved = Arrays.asList("const", "do", "while", "else", "if", "int", "char", "boolean", "string", "float", "read", "print", "let");

    private boolean isIdentifier(String s) {
        return s.matches("[a-zA-Z][a-zA-Z_0-9]*");
    }

    private boolean isConstant(String s) {
        return s.matches("-?[1-9]+\\d*|0") ||
                s.matches("'[a-zA-Z0-9]'") ||
                s.matches("\"[a-zA-Z0-9]+\"");
    }

    private boolean isOperator(String token) {
        return operators.contains(token);
    }

    private boolean isSeparator(String token) {
        return separators.contains(token);
    }

    private boolean isReserved(String token) {
        return reserved.contains(token);
    }
}
