import java.util.*;

public class Dijkstra {
    private Graph graph;
    private Map<String, Integer> distances = new HashMap<>();


    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    void dijkstra(String start) {

        for (String vertex : graph.adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        PriorityQueue<Graph.Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        priorityQueue.add(new Graph.Edge(start, 0));

        while (!priorityQueue.isEmpty()) {
            Graph.Edge current = priorityQueue.poll();
            for (Graph.Edge edge : graph.adjacencyList.get(current.destination)) {
                int newDist = distances.get(current.destination) + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    priorityQueue.add(new Graph.Edge(edge.destination, newDist));
                }
            }
        }
    }

    public Map<String, Integer> getDistances() {
        return distances;
    }
}
