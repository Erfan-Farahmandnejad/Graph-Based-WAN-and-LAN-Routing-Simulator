import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Floyd {
   private Graph graph;
   private int[][] dist;

    public Floyd(Graph graph) {
        this.graph = graph;
    }

    void floydWarshall() {
        int V = graph.verticesList.size();
        dist = new int[V][V];

        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], graph.INF);
            dist[i][i] = 0;
        }

        for (Map.Entry<String, List<Graph.Edge>> entry : graph.adjacencyList.entrySet()) {
            int u = graph.vertexIndexMap.get(entry.getKey());
            for (Graph.Edge edge : entry.getValue()) {
                int v = graph.vertexIndexMap.get(edge.destination);
                dist[u][v] = edge.weight;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != graph.INF && dist[k][j] != graph.INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

    }

    public int[][] getDist() {
        return dist;
    }
}
