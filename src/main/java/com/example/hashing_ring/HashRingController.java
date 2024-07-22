package com.example.hashing_ring;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hash-ring")
public class HashRingController {

    private final HashRingService hashRingService;

    @GetMapping("/node")
    public Node getNodeForKey(@RequestParam String key) {
        return hashRingService.getNodeForKey(key);
    }

    @PostMapping("/node")
    public void addNode(@RequestParam String identifier) {
        hashRingService.addNode(identifier);
    }

    @DeleteMapping("/node")
    public void removeNode(@RequestParam String identifier) {
        hashRingService.removeNode(identifier);
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return hashRingService.getNodes();
    }
}

