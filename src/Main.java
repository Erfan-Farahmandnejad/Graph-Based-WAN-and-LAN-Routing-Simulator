import java.util.*;


class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();

        // Define vertices for the graph
        String[] vertices = {"A1", "A2", "A3", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "C5",
                "D1", "D2", "D3", "E1", "E2", "E3", "E4", "E5", "F1", "F2", "F3", "F4", "F5"};

        // Define edges as pairs of vertices with weights
        String[][] edges = {
                {"A1", "A2", "2"}, {"A2", "A3", "4"}, {"B1", "B2", "2"}, {"B2", "B3", "3"},
                {"B2", "B4", "8"}, {"B4", "B3", "7"}, {"C1", "C2", "1"}, {"C2", "C3", "1"}, {"C2", "C5", "5"},
                {"C1", "C4", "3"}, {"D1", "D2", "2"}, {"D1", "D3", "4"}, {"E1", "E2", "2"},
                {"E1", "E4", "6"}, {"E1", "E3", "1"}, {"E4", "E5", "9"}, {"F1", "F2", "2"},
                {"F1", "F3", "2"}, {"F1", "F5", "7"}, {"F2", "F4", "3"}, {"F4", "F5", "8"},
                {"F4", "F3", "6"}, {"F5", "F3", "5"}, {"C1", "D1", "10"}, {"C1", "E1", "15"}, {"B1", "F1", "44"},
                {"D1", "B1", "12"}, {"F1", "A1", "21"}, {"A1", "B1", "33"}, {"A1", "E1", "12"}
        };

        // Define more vertices and edges for different test cases

//        String[] vertices2 = {"G1", "G2", "G3", "H1", "H2", "H3", "H4", "I1", "I2", "I3", "I4", "I5",
//                "J1", "J2", "J3", "K1", "K2", "K3", "K4", "K5", "L1", "L2", "L3", "L4", "L5"};
//
//        String[][] edges2 = {
//                {"G1", "G2", "3"}, {"G2", "G3", "5"}, {"H1", "H2", "2"}, {"H2", "H3", "4"},
//                {"H2", "H4", "7"}, {"H4", "H3", "6"}, {"I1", "I2", "2"}, {"I2", "I3", "3"}, {"I2", "I5", "4"},
//                {"I1", "I4", "2"}, {"J1", "J2", "3"}, {"J1", "J3", "5"}, {"K1", "K2", "1"},
//                {"K1", "K4", "7"}, {"K1", "K3", "2"}, {"K4", "K5", "8"}, {"L1", "L2", "2"},
//                {"L1", "L3", "1"}, {"L1", "L5", "6"}, {"L2", "L4", "3"}, {"L4", "L5", "9"},
//                {"L4", "L3", "4"}, {"L5", "L3", "7"}, {"I1", "J1", "11"}, {"I1", "K1", "13"}, {"H1", "L1", "43"},
//                {"J1", "H1", "14"}, {"L1", "G1", "20"}, {"G1", "H1", "32"}, {"G1", "K1", "11"}
//        };
//
//        String[] vertices3 = {"M1", "M2", "M3", "N1", "N2", "N3", "N4", "O1", "O2", "O3", "O4", "O5",
//                "P1", "P2", "P3", "Q1", "Q2", "Q3", "Q4", "Q5", "R1", "R2", "R3", "R4", "R5"};
//
//        String[][] edges3 = {
//                {"M1", "M2", "2"}, {"M2", "M3", "6"}, {"N1", "N2", "3"}, {"N2", "N3", "5"},
//                {"N2", "N4", "9"}, {"N4", "N3", "8"}, {"O1", "O2", "3"}, {"O2", "O3", "2"}, {"O2", "O5", "6"},
//                {"O1", "O4", "4"}, {"P1", "P2", "1"}, {"P1", "P3", "3"}, {"Q1", "Q2", "4"},
//                {"Q1", "Q4", "8"}, {"Q1", "Q3", "2"}, {"Q4", "Q5", "10"}, {"R1", "R2", "3"},
//                {"R1", "R3", "1"}, {"R1", "R5", "5"}, {"R2", "R4", "4"}, {"R4", "R5", "7"},
//                {"R4", "R3", "3"}, {"R5", "R3", "6"}, {"O1", "P1", "12"}, {"O1", "Q1", "14"}, {"N1", "R1", "41"},
//                {"P1", "N1", "15"}, {"R1", "M1", "19"}, {"M1", "N1", "31"}, {"M1", "Q1", "13"}
//        };
//
//        String[] vertices4 = {"S1", "S2", "S3", "T1", "T2", "T3", "T4", "U1", "U2", "U3", "U4", "U5",
//                "V1", "V2", "V3", "W1", "W2", "W3", "W4", "W5", "X1", "X2", "X3", "X4", "X5"};
//
//        String[][] edges4 = {
//                {"S1", "S2", "1"}, {"S2", "S3", "5"}, {"T1", "T2", "3"}, {"T2", "T3", "6"},
//                {"T2", "T4", "9"}, {"T4", "T3", "8"}, {"U1", "U2", "2"}, {"U2", "U3", "4"}, {"U2", "U5", "5"},
//                {"U1", "U4", "3"}, {"V1", "V2", "2"}, {"V1", "V3", "4"}, {"W1", "W2", "1"},
//                {"W1", "W4", "9"}, {"W1", "W3", "3"}, {"W4", "W5", "7"}, {"X1", "X2", "2"},
//                {"X1", "X3", "1"}, {"X1", "X5", "4"}, {"X2", "X4", "3"}, {"X4", "X5", "10"},
//                {"X4", "X3", "5"}, {"X5", "X3", "8"}, {"U1", "V1", "13"}, {"U1", "W1", "15"}, {"T1", "X1", "42"},
//                {"V1", "T1", "16"}, {"X1", "S1", "18"}, {"S1", "T1", "30"}, {"S1", "W1", "10"}
//        };

        // Build the graph using the specified vertices and edges
        graph.makeGraph(vertices, edges);


        // Initialize and execute the Floyd-Warshall algorithm to find shortest paths between all pairs of vertices
        Floyd floyd = new Floyd(graph);
        floyd.floydWarshall();

        // Initialize a data structure to store distances for local processing
        Map<String, Map<String, Integer>> distances = new HashMap<>();
        graph.localRotation(distances);


        // User interaction for adding new vertex pairs and retrieving shortest path information
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Prompt the user to input vertices for a new query
            System.out.println("Enter new vertexes...          enter * to finish\n");
            String v1 = scanner.next();

            // Check if the user wants to terminate input
            if (v1.equals("*")) {
                break;
            }

            String v2 = scanner.next();

            // Check if the user wants to terminate input
            if (v2.equals("*")) {
                break;
            }

            // Output the shortest path and distance between the two specified vertices
            graph.outPut(v1, v2, distances, floyd.getDist());
        }

    }
}
