package com.example.hashing_ring;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashRingService {
    private final ConsistentHashRing hashRing;

    public HashRingService() {
        this.hashRing = new ConsistentHashRing(3); // Example: 3 replicas
    }

    @PostConstruct
    public void init() {
        // Add nodes to the hash ring
        hashRing.add(new Node("Node1"));
        hashRing.add(new Node("Node2"));
        hashRing.add(new Node("Node3"));
    }

    public Node getNodeForKey(String key) {
        return hashRing.get(key);
    }

    public void addNode(String identifier) {
        hashRing.add(new Node(identifier));
    }

    public void removeNode(String identifier) {
        hashRing.remove(new Node(identifier));
    }

    public List<Node> getNodes() {
        return List.copyOf(hashRing.getNodes());
    }
}

