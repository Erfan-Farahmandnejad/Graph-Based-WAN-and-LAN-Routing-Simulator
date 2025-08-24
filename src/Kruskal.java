import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Kruskal {

    private Graph graph;

    public Kruskal(Graph graph) {
        this.graph = graph;
    }

    public void kruskal() {

        List<Graph.Edge> result = new ArrayList<>();
        List<Graph.Edge> edges = new ArrayList<>();

        for (Map.Entry<String, List<Graph.Edge>> entry : graph.adjacencyList.entrySet()) {
            edges.addAll(entry.getValue());
        }

        edges.sort(Comparator.comparingInt(e -> e.weight));

        DisjointSet ds = new DisjointSet(graph.verticesList.size());

        for (Graph.Edge edge : edges) {
            int u = graph.vertexIndexMap.get(edge.source);
            int v = graph.vertexIndexMap.get(edge.destination);

            if (ds.find(u) != ds.find(v)) {
                result.add(edge);
                ds.union(u, v);
            }
        }

        //make new graph after mst
        Graph newGraph = new Graph();
        for (Graph.Edge edge : result) {
            newGraph.addFinalVertex(edge.source);
            newGraph.addFinalVertex(edge.destination);
            newGraph.addFinalEdge(edge.source, edge.destination, edge.weight);
        }

        this.graph=newGraph;

    }

    public Graph getGraph() {
        return graph;
    }




    static class DisjointSet {
        private int[] parent;
        private int[] rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int u) {
            if (u != parent[u]) {
                parent[u] = find(parent[u]);
            }
            return parent[u];
        }

        void union(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);

            if (rootU != rootV) {
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else {
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
        }

    }

}
