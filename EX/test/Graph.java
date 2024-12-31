package test;

import java.util.*;

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

    public void createFromTopics() {
        TopicManager tm = TopicManagerSingleton.get();
        Map<String, Topic> map = tm.getData();
        Map<String, Node> nodeMap = new HashMap<>();
        StringBuilder sb;

        for (Map.Entry<String, Topic> entry : map.entrySet()) {

            sb = new StringBuilder(entry.getKey());
            sb.insert(0, 'T');
            String topicNodeName = sb.toString();

            Node topicNode = nodeMap.get(topicNodeName);
            if (topicNode == null) {
                topicNode = new Node(topicNodeName);
                nodeMap.put(topicNodeName, topicNode);
            }

            Topic topic = entry.getValue();

            for (Agent subscriber : topic.getSubs()) {
                sb = new StringBuilder(subscriber.getName());
                sb.insert(0, 'A');
                String agentNodeName = sb.toString();

                Node agentNode = nodeMap.get(agentNodeName);
                if (agentNode == null) {
                    agentNode = new Node(agentNodeName);
                    nodeMap.put(agentNodeName, agentNode);
                }
                topicNode.addEdge(agentNode);
            }

            for (Agent publisher : topic.getPubs()) {
                sb = new StringBuilder(publisher.getName());
                sb.insert(0, 'A');
                String agentNodeName = sb.toString();

                if (!nodeMap.containsKey(agentNodeName)) {
                    Node agentNode = new Node(agentNodeName);
                    nodeMap.put(agentNodeName, agentNode);
                }
            }
        }
        addAll(nodeMap.values());
    }
}
