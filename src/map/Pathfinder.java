package map;

import core.Position;

import java.util.*;

public class Pathfinder {

    public static List<Position> findPath(Position start,Position target,SymulatorMap map,String name){
        final PriorityQueue<Node> open = new PriorityQueue<>();
        final Set<Node> closed = new HashSet<>();
        final Node[][] nodeMap = new Node[map.getTiles().length][map.getTiles()[0].length];
        final List<Tile> avaiablePath = map.getListOfPositionOfType(name);
        Node curr;
        for (int i = 0; i <avaiablePath.size() ; i++) {

            int x = avaiablePath.get(i).getCenterPosition().intX();
            int y = avaiablePath.get(i).getCenterPosition().intY();
            int heuristics = Math.abs(x- target.gridX()) + Math.abs(y- target.gridY());
            Node node = new Node(10,heuristics,x,y,avaiablePath.get(i).getAvaiableMoves());
            nodeMap[x][y] = node;
        }

        Node startNode = nodeMap[start.gridX()][start.gridY()];
        Node targetNode = nodeMap[target.gridX()][target.gridY()];
        open.add(startNode);

        do{
            curr = open.poll(); //poll gives the top element and moves the collection whereas peek does not remove the first element
            closed.add(curr);

            if(curr.equals(targetNode)){
                return extractPath(curr);
            }

            for (int i = 0; i < curr.getAvaiableMoves().size(); i++) {
                int x = curr.gridX +curr.getAvaiableMoves().get(i).get(0);
                int y = curr.gridY +curr.getAvaiableMoves().get(i).get(1);
                if(nodeMap[x][y] !=null){
                    Node neighbor = nodeMap[x][y];
                    if (closed.contains(neighbor)){

                        continue;
                    }
                    int calculatedCost = neighbor.heuristic + neighbor.moveCost + curr.totalCost;

                    if (((curr.gridX == neighbor.gridX)|| (curr.gridY == neighbor.gridY)) &&
                            (calculatedCost < neighbor.totalCost || !open.contains(neighbor))){
                        neighbor.totalCost = calculatedCost;
                        neighbor.parent = curr;
                        if(!open.contains(neighbor)){
                            open.add(neighbor);
                        }
                    }
                }

            }

        }while(!open.isEmpty());
        return List.of(start);
    }

    private static List<Position> extractPath(Node curr) {
        List<Position> path = new ArrayList<>();
        while(curr.parent !=null){
            path.add(curr.getPosition());
            curr = curr.parent;
        }
        Collections.reverse(path);
        return path;
    }


    private static class Node implements Comparable<Node>{
        private int moveCost;
        private int heuristic;
        private int totalCost;
        private Node parent;
        private int gridX;
        private int gridY;
        private List<List<Integer>> avaiableMoves;

        public List<List<Integer>> getAvaiableMoves() {
            return avaiableMoves;
        }

        public Node(int moveCost, int heuristic, int gridX, int gridY, List avaiableMoves) {
            this.moveCost = moveCost;
            this.heuristic = heuristic;
            this.gridX = gridX;
            this.gridY = gridY;
            this.avaiableMoves = avaiableMoves;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.totalCost,o.totalCost);
        }

        public Position getPosition(){
            return Position.ofGridPosition(gridX,gridY);
        }
    }
}
