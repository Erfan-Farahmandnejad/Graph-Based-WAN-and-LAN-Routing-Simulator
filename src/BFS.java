import java.util.*;

public class BFS {
    Graph graph;
    Map<String, Map<String, Integer>> distances;

    public BFS(Graph graph, Map<String, Map<String, Integer>> distances) {
        this.graph = graph;
        this.distances=distances;
    }

    public void bfs(String start) {
        ;
        Map<String, Integer> dist = new HashMap<>();
        dist.put(start, 0);
        distances.put(start, dist);


        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int cur = dist.get(current);


            for (Graph.Edge neighbor : graph.adjacencyList.get(current)) {
                if (!visited.contains(neighbor.destination)) {
                    visited.add(neighbor.destination);
                    queue.offer(neighbor.destination);

                    dist.put(neighbor.destination, graph.getWeight(current, neighbor.destination) + cur);
                    distances.put(current, dist);

                }
            }
        }


    }

    public Map<String, Map<String, Integer>> getDistances() {
        return distances;
    }
}
