package com.example.hashing_ring;

import java.util.*;

public class ConsistentHashRing {
    private final int numberOfReplicas;
    private final SortedMap<Integer, Node> circle = new TreeMap<>();

    public ConsistentHashRing(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    private int hash(String key) {
        return key.hashCode();
    }

    public void add(Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hash(node.getIdentifier() + i), node);
        }
    }

    public void remove(Node node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hash(node.getIdentifier() + i));
        }
    }

    public Node get(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, Node> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    public Set<Node> getNodes() {
        return new HashSet<>(circle.values());
    }
}

