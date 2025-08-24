import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {


    public Map<String, List<Edge>> adjacencyList;

    public final Map<String, Integer> vertexIndexMap;

    public final List<String> verticesList;
    public final Map<String, Area> areas;
    public final int INF = Integer.MAX_VALUE;

    public Map<String, Area> getAreas() {
        return areas;
    }

    public Graph() {
        adjacencyList = new HashMap<>();
        verticesList = new ArrayList<>();
        vertexIndexMap = new HashMap<>();
        areas = new HashMap<>();
    }

    public void addVertex(String label) {
        //check area
        String[] vertex = label.split("");
        //if it was a vertex for another area,create a  new area!
        if (vertex[1].equals("1")) {
            areas.put(vertex[0], new Area());
            areas.get(vertex[0]).getGraph().addFinalVertex(label);
            addFinalVertex(label);
        }
        //add vertex to it's area
        areas.get(vertex[0]).getGraph().addFinalVertex(label);
    }

    public void addFinalVertex(String label) {
        //add vertex
        int index = verticesList.size();
        adjacencyList.putIfAbsent(label, new ArrayList<>());
        vertexIndexMap.put(label, index);
        verticesList.add(label);
    }

    //add edge
    public void addEdge(String source, String destination, int weight) {
        //seperated areas
        String[] vertexS = source.split("");
        String[] vertexD = destination.split("");
        Area area1 = areas.get(vertexS[0]);
        Area area2 = areas.get(vertexD[0]);
        //compare areas
        //if they are the same,add edge in that area's graph
        if (area1 == area2) {
            //add edge in area's graph
            area1.getGraph().adjacencyList.get(source).add(new Edge(source, destination, weight));
            area1.getGraph().adjacencyList.get(destination).add(new Edge(destination, source, weight));
        }
        //else add edge in main graph
        else {
            addFinalEdge(source, destination, weight);
        }
    }

    public void addFinalEdge(String source, String destination, int weight) {
        adjacencyList.get(source).add(new Edge(source, destination, weight));
        adjacencyList.get(destination).add(new Edge(destination, source, weight));
    }


    public int getWeight(String v1, String v2) {
        if (v1.equals(v2)) return 0;
        for (Edge edge : this.adjacencyList.get(v1)) {
            if (edge.destination.equals(v2))
                return edge.weight;
        }

        return INF;
    }

    static class Edge {
        String source;
        String destination;
        int weight;

        Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }

        Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return source + " - " + destination + "(" + weight + ")";
        }
    }


    void makeGraph(String[] vertices, String[][] edges) {

        // Adding vertices

        for (String vertex : vertices) {
            this.addVertex(vertex);
        }

        // Adding edges with weights


        for (String[] s : edges) {
            this.addEdge(s[0], s[1], Integer.parseInt(s[2]));
        }

        //specify safe and unsafe areas
        for (String area : this.areas.keySet()) {
            if (area.equals("B") || area.equals("F"))
                this.areas.get(area).setSafe(false);
        }
    }


    void localRotation(Map<String, Map<String, Integer>> distances) {
        // vise versa Dijkstra algorithm for safe areas
        for (String area : this.areas.keySet()) {
            Area area1 = this.areas.get(area);

            if (area1.isSafe()) {
                // Dijkstra's algorithm for finding the shortest path

                Dijkstra dijkstra = new Dijkstra(area1.getGraph());
                dijkstra.dijkstra(area + "1");
                distances.put(area + "1", dijkstra.getDistances());

            } else {
                //if unSafe,using Kruskal

                // Kruskal's algorithm for finding the Minimum Spanning Tree (MST)

                Kruskal kruskal = new Kruskal(area1.getGraph());
                kruskal.kruskal();
                area1.setGraph(kruskal.getGraph());

                // Breadth-First Search (BFS) traversal from a given start vertex
                BFS bfs = new BFS(area1.getGraph(), distances);
                bfs.bfs(area + "1");

            }
        }
    }


    String localToRoot(String v1, String v2, Map<String, Map<String, Integer>> safeDistances, int[] sum) {
        if (v1.equals(v2)) return "";
        int distance = safeDistances.get(v2).get(v1);
        sum[0] += distance;
        return v1 + " -> " + v2 + " : " + distance;
    }

    String rootToLocal(String v1, String v2, Map<String, Map<String, Integer>> safeDistances, int[] sum) {
        if (v1.equals(v2)) return "";
        int distance = safeDistances.get(v1).get(v2);
        sum[0] += distance;
        return v1 + " -> " + v2 + " : " + distance;
    }


    String nonLocal(String v1, String v2, int[][] floyd, int[] sum) {
        if (v1.equals(v2)) return "";
        int distance = floyd[vertexIndexMap.get(v1)][vertexIndexMap.get(v2)];
        sum[0] += distance;
        return v1 + " -> " + v2 + " : " + distance;
    }

    void outPut(String v1, String v2, Map<String, Map<String, Integer>> distances, int[][] floyd) {
        int[] sum = new int[1];
        if (!v1.equals(v2)) {

            String area1 = v1.split("")[0];
            String area2 = v2.split("")[0];


            //if they are in the same area
            if (area1.equals(area2)) {
                System.out.println(this.localToRoot(v1, area1 + "1", distances, sum));
                System.out.println(this.rootToLocal(area1 + "1", v2, distances, sum));
            } else {
                System.out.println(this.localToRoot(v1, area1 + "1", distances, sum));
                System.out.println(this.nonLocal(area1 + "1", area2 + "1", floyd, sum));
                System.out.println(this.rootToLocal(area2 + "1", v2, distances, sum));
            }
        }
        System.out.println("Total distance is: " + sum[0]);
    }

}