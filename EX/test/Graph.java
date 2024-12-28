package test;

import java.util.ArrayList;
import java.util.HashMap;

import test.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node>{
    
    public boolean hasCycles() {
        for(Node n : this) {
            if(n.hasCycles()) {
                return true;
            }
        }
        return false;
    }
    public void createFromTopics(){
    }

    
}
