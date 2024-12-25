package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name){
        this.name = name;
        edges = new ArrayList<>();
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void addEdge(Node n){
        edges.add(n);
    }
    public void setEdges(List<Node> edges){
        this.edges = edges;
    }
    public List<Node> getEdges(){
        return edges;
    }
    public void setMsg(Message msg){
        this.msg = msg;
    }
    public Message getMsg(){
        return msg;
    }

    public boolean hasCycles() {
        return hasCyclesHelper(this, null, new HashSet<>());
    }

    public boolean hasCyclesHelper(Node current, Node parent, Set<Node> visited) {
        visited.add(current);

        for (Node n : current.edges) {
            if (!visited.contains(n)) {
                if (hasCyclesHelper(n, current, visited)) {
                    return true;
                }
            } else if (n != parent) {
                return true;
            }
        }

        return false;
    }
}