# **Graph-Based-WAN-and-LAN-Routing-Simulator**

## **Abstract**

This project is a Java-based simulation of a hierarchical computer network routing system. It models a **Wide Area Network (WAN)** connecting multiple **Local Area Networks (LANs)**, implementing a multi-algorithm routing strategy that adapts to network conditions. The system uses the **Floyd-Warshall algorithm** for efficient all-pairs shortest path computation between LAN edge nodes. For intra-LAN routing, it employs **Dijkstra's algorithm** in resourced areas for speed-optimized paths, and a combination of **Kruskal's MST** and **BFS** in under-resourced areas for cost-effective routing. All routing tables are pre-computed, enabling $O(1)$ query responses with a clean, object-oriented design.

## **Table of Contents**

* [Abstract](#abstract)
* [Project Overview](#project-overview)  
* [Features](#features)  
* [Core Routing Logic](#core-routing-logic)  
* [Algorithms Implemented](#algorithms-implemented)  
* [Implementation Details](#implementation-details)
* [Project Structure](#project-structure)  
* [Requirements](#requirements)
* [How to Run](#how-to-run)  
* [Contributors](#contributors)  
* [License](#license)

## **Project Overview**

This program simulates message routing in a large computer network, as specified by the "Algorithm Design" course project. The network consists of a **Wide Area Network (WAN)** which connects several smaller **Local Area Networks (LANs)**, referred to as "Areas."

The goal is to find the most efficient path for a message between any two nodes in the entire network, balancing speed and cost based on the characteristics of each Area.

### **Network Structure**

![Network Graph Structure](https://github.com/user-attachments/assets/1704035d-07c7-47df-9bf3-85ad01cbddb9)

*Figure: Example of the WAN graph structure with LANs (areas A-F), edge nodes, and weighted connections.*

* **Areas (LANs):** The network is divided into Areas (e.g., A, B, C).  
* **Nodes:** Each node is identified by its Area and a number (e.g., A1, C3).  
* **Edge Nodes:** The node numbered 1 in each Area (e.g., A1, B1) is the designated "Edge Node," which handles routing to and from other Areas.  
* **Area Types:**  
  * **Resourced (Safe):** These Areas (e.g., A, C, D, E) prioritize high-speed routing.  
  * **Under-resourced (Unsafe):** These Areas (e.g., B, F) prioritize minimizing connection costs.

## **Features**

* **Graph Construction**: Dynamically builds the WAN graph from vertices and edges, automatically creating LAN subgraphs.  
* **Area Classification**: Supports "safe" and "unsafe" LANs with tailored routing strategies.  
* **Multi-Algorithm Routing**: Implements the optimal algorithm for each part of the network (Floyd-Warshall, Dijkstra, Kruskal + BFS).  
* **Pre-computation**: All routing tables (WAN and LAN) are pre-calculated for fast, $O(1)$ query lookups.  
* **Interactive Querying**: Allows the user to repeatedly enter source-destination pairs to retrieve the full path and total distance.  
* **Code Structure**: A clean, object-oriented design separates graph logic, area logic, and each algorithm into its own class.

## **Core Routing Logic**

A key requirement of the simulation is that all message routing follows a strict 3-step path:

1. **Local to Edge:** The message travels from the **Source Node** (e.g., E2) to its Area's **Edge Node** (E1).  
2. **Edge to Edge (WAN):** The message travels from the **Source Edge Node** (E1) to the **Destination Area's Edge Node** (A1).  
3. **Edge to Local:** The message travels from the **Destination Edge Node** (A1) to the final **Destination Node** (A3).

This 3-step process is used even if the source and destination nodes are in the same Area.

## **Algorithms Implemented**

To optimize this routing logic, the program pre-calculates paths using three distinct graph algorithms based on the context.

> **Note:** All algorithms perform their computations during initialization. Once the routing tables are built, path queries are answered in $O(1)$ time through simple lookups.

### **1. Inter-Area (WAN) Routing**

* **Problem:** Find the shortest path between all pairs of *Edge Nodes* (e.g., A1 to B1, A1 to C1, etc.). This must be pre-calculated for fast lookups.  
* **Solution:** **Floyd-Warshall Algorithm**. This algorithm is ideal for finding all-pairs shortest paths in a graph. It computes and stores the shortest distance between every pair of nodes in the main graph, allowing for $O(1)$ lookup time for the path distance between any two edge nodes during routing.  
* **Time Complexity:** $O(V^3)$  
* **Space Complexity:** $O(V^2)$

### **2. Intra-Area (Resourced/Safe LAN) Routing**

* **Problem:** In "Resourced" Areas, find the fastest (shortest) path from the Area's *Edge Node* to every other node within that same Area.  
* **Solution:** **Dijkstra's Algorithm**. Since all traffic must pass through the edge node, we only need to find the shortest path from that single source. Dijkstra's is a highly efficient algorithm for finding the single-source shortest paths in a weighted graph.  
* **Time Complexity:** $O((E+V) \log V)$  
* **Space Complexity:** $O(V+E)$

### **3. Intra-Area (Under-resourced/Unsafe LAN) Routing**

* **Problem:** In "Under-resourced" Areas, the priority is to minimize connection costs. This requires two steps:  
  1. Reduce the network connections to the bare minimum required for connectivity.  
  2. Find a routing path on this new, minimal graph.  
* **Solution (Step 1):** **Kruskal's Algorithm**. To minimize connection costs, we compute the Minimum Spanning Tree (MST) of the Area's graph. This retains all nodes while minimizing the total edge-weight (cost). Uses Union-Find (Disjoint Set Union) for efficient cycle detection.  
* **Solution (Step 2):** **Breadth-First Search (BFS)**. After Kruskal's algorithm, the Area's graph is now a tree. BFS is a very fast algorithm ( $O(V+E)$ ) for finding the shortest path in terms of *hops* from the edge node to all other nodes in the tree.  
* **Time Complexity:** $O(E \log E)$ (for Kruskal) + $O(V+E)$ (for BFS)  
* **Space Complexity:** $O(V+E)$

## **Implementation Details**

### **Data Structures**

* **Adjacency List**: The graph is represented using adjacency lists for efficient edge traversal, stored in a `HashMap<String, List<Edge>>`.
* **Edge Nodes**: Automatically detected as nodes ending with "1" (e.g., A1, B1) during graph construction.
* **Area Classification**: Areas are automatically classified as safe/resourced or unsafe/under-resourced based on their identifier (B and F are unsafe).

### **Routing Strategy**

The system implements a hybrid routing approach:
1. **WAN Routing**: All inter-area communication must go through edge nodes, using pre-computed Floyd-Warshall distances.
2. **Safe Area Routing**: Direct Dijkstra shortest paths from the edge node.
3. **Unsafe Area Routing**: MST construction via Kruskal followed by tree-based BFS traversal.

### **Graph Representation**

Each Area maintains its own subgraph, while the main Graph class manages the overall WAN topology. This separation allows independent computation of intra-area routes while maintaining a unified interface for inter-area routing queries.

## **Project Structure**

```
├── Area.java        # Represents a LAN with its graph and safety status  
├── BFS.java         # Implements BFS for distance computation in unsafe areas  
├── Dijkstra.java    # Implements Dijkstra for shortest paths in safe areas  
├── Floyd.java       # Implements Floyd-Warshall for border-to-border paths  
├── Graph.java       # Core graph class with adjacency list, areas, and routing logic  
├── Kruskal.java     # Implements Kruskal for MST in unsafe areas  
├── Main.java        # Entry point: Builds graph, computes distances, handles queries  
└── README.md        # This file
```

## **Requirements**

This project requires:
* **Java Development Kit (JDK)**: Version 8 or higher
* **Operating System**: Any OS with Java support (Windows, macOS, Linux)

## **How to Run**

### **Prerequisites**

Ensure you have Java installed on your system. You can verify this by running:
```bash
java -version
```

### **Compilation and Execution**

1. Navigate to the project directory containing the source files.

2. Compile all Java files:  
   ```bash
   javac *.java
   ```

3. Run the Main class:  
   ```bash
   java Main
   ```

4. The program will automatically build the graph and pre-calculate all WAN (Floyd-Warshall) and LAN (Dijkstra/Kruskal+BFS) paths.

5. You will be prompted to enter two vertices (a source and a destination):  
   ```
   Enter new vertexes...          enter * to finish
   ```

6. Enter the source and destination nodes (e.g., `E2 A3`).  
   The program will output the 3-step path, the distance for each step, and the total distance.

7. To quit the program, enter `*` as the vertex.

### **Example Output**

For input `E2 A3`:

```
E2 -> E1 : 2  
E1 -> A1 : 12  
A1 -> A3 : 6  
Total distance is: 20
```

### **Test Cases**

Try these additional examples:
- `C3 A1` - Cross-area routing from C to A
- `B2 F3` - Routing through unsafe areas (B and F)
- `D1 D3` - Same area routing (forces path through edge node)
- `F5 B4` - Complex multi-area routing

## **Contributors**

* [Erfan-Farahmandnejad](https://github.com/Erfan-Farahmandnejad)
* [Shabnam-Khaqanpoor](https://github.com/Shabnam-Khaqanpoor)
  

## **License**

This project is open-source and available under the MIT License.
