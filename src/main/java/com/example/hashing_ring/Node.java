package com.example.hashing_ring;

import lombok.Getter;

@Getter
public class Node {
    private final String identifier;

    public Node(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Node{" + "identifier='" + identifier + '\'' + '}';
    }
}

