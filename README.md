# Consistent Hashing Ring with Spring Boot

In distributed systems, achieving balanced load distribution is crucial. Consistent hashing is a technique that helps in evenly distributing tasks or requests across multiple nodes. This guide will help you implement a consistent hashing ring using Spring Boot.

## Step-by-Step Guide

### Step 1: Create a New Spring Boot Project

Start by creating a new Spring Boot project using [Spring Initializr](https://start.spring.io/) with the following dependencies:
- Spring Web

### Step 2: Define the Hash Ring

1. **Create a class to represent the nodes in your hash ring:**

    ```java
    public class Node {
        private final String identifier;
        
        public Node(String identifier) {
            this.identifier = identifier;
        }
        
        public String getIdentifier() {
            return identifier;
        }
    }
    ```

2. **Create a class to handle the consistent hashing logic:**

    ```java
    import java.util.SortedMap;
    import java.util.TreeMap;

    public class ConsistentHashRing {
        private final SortedMap<Integer, Node> ring = new TreeMap<>();
        private final int numberOfReplicas;

        public ConsistentHashRing(int numberOfReplicas) {
            this.numberOfReplicas = numberOfReplicas;
        }

        private int hash(String key) {
            return key.hashCode();
        }

        public void addNode(Node node) {
            for (int i = 0; i < numberOfReplicas; i++) {
                ring.put(hash(node.getIdentifier() + i), node);
            }
        }

        public void removeNode(Node node) {
            for (int i = 0; i < numberOfReplicas; i++) {
                ring.remove(hash(node.getIdentifier() + i));
            }
        }

        public Node getNode(String key) {
            if (ring.isEmpty()) {
                return null;
            }
            int hash = hash(key);
            if (!ring.containsKey(hash)) {
                SortedMap<Integer, Node> tailMap = ring.tailMap(hash);
                hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
            }
            return ring.get(hash);
        }

        public Collection<Node> getAllNodes() {
            return new HashSet<>(ring.values());
        }
    }
    ```

### Step 3: Create a Service to Use the Hash Ring

```java
import org.springframework.stereotype.Service;

@Service
public class HashRingService {
    private final ConsistentHashRing consistentHashRing;

    public HashRingService() {
        this.consistentHashRing = new ConsistentHashRing(3); // numberOfReplicas
    }

    public void addNode(String identifier) {
        consistentHashRing.addNode(new Node(identifier));
    }

    public void removeNode(String identifier) {
        consistentHashRing.removeNode(new Node(identifier));
    }

    public Node getNode(String key) {
        return consistentHashRing.getNode(key);
    }

    public Collection<Node> getAllNodes() {
        return consistentHashRing.getAllNodes();
    }
}
```

### Step 4: Create a Controller

Expose the hash ring functionality via REST endpoints:

```java
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/hash-ring")
public class HashRingController {
    private final HashRingService hashRingService;

    public HashRingController(HashRingService hashRingService) {
        this.hashRingService = hashRingService;
    }

    @PostMapping("/node")
    public void addNode(@RequestParam String identifier) {
        hashRingService.addNode(identifier);
    }

    @DeleteMapping("/node")
    public void removeNode(@RequestParam String identifier) {
        hashRingService.removeNode(identifier);
    }

    @GetMapping("/node")
    public Node getNode(@RequestParam String key) {
        return hashRingService.getNode(key);
    }

    @GetMapping("/nodes")
    public Collection<Node> getAllNodes() {
        return hashRingService.getAllNodes();
    }
}
```

### Step 5: Test Your Application

Run your Spring Boot application and use tools like Postman, Curl, or setup Swagger to test the endpoints:

1. **Add nodes:**

    - **Method:** POST
    - **URL:** `http://localhost:8080/hash-ring/node?identifier=Node4`

2. **Get node for a key:**

    - **Method:** GET
    - **URL:** `http://localhost:8080/hash-ring/node?key=someKey`

3. **Remove nodes:**

    - **Method:** DELETE
    - **URL:** `http://localhost:8080/hash-ring/node?identifier=Node4`

4. **List nodes:**

    - **Method:** GET
    - **URL:** `http://localhost:8080/hash-ring/nodes`

This simple implementation of a consistent hash ring can help you evenly distribute tasks or requests across multiple nodes in your distributed system. Happy coding! 👨‍💻👩‍💻  
