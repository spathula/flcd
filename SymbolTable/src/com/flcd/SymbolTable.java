package com.flcd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymbolTable {
    private List<List<String>> table = new ArrayList<>(asList(7));
    private int capacity = 7;
    private int size = 0;

    public int add(String token) {
        if((double) size/(capacity*3) > 0.7) extendTable();

        size++;
        int hash = this.hashFunction(token);

        table.get(hash).add(token);
        return hash;
    }

    public Pair get(String token) {
        return new Pair(hashFunction(token), table.get(hashFunction(token)).indexOf(token));
    }

    private void extendTable() {
        List<List<String>> temp = table;
        capacity *= 2;
        table = new ArrayList<>(asList(capacity));
        temp.forEach(list -> list.forEach(this::add));
    }

    private int hashFunction(String token) {
        return token.hashCode() % capacity;
    }

    private List<List<String>> asList(int size) {
        List<List<String>> list = new ArrayList<>();
        for(int i = 0; i < size; i++) list.add(new ArrayList<>());
        return list;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "table=" + table +
                '}';
    }
}
