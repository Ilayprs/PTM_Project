package test;

import java.util.ArrayList;
import java.util.List;


public class Node {
    private String name;
    private List<Node> edges;
    private Message msg;

    public Node(String name){
        this.name = name;
        edges = new ArrayList<Node>();
        msg = new Message(name);
    }

    public String getName(){
        return name;
    }

    public List<Node> getEdges(){
        return edges;
    }

    public Message getMessage(){
        return msg;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEdges(List<Node> edges){
        this.edges = new ArrayList<Node>(edges);
    }

    public void setMsg(Message msg){
        this.msg = new Message(msg.asText);
    }

    public void addEdge(Node edge){
        edges.add(edge);
    }

    public boolean hasCycles() {
        return helpHasCycles(this);
    }

    private boolean helpHasCycles(Node current) {
        for (Node edge : edges) {
            if (edge == current) {
                return true;
            }
            if (edge.helpHasCycles(current)) {
                return true;
            }
        }
        return false;
    }
}