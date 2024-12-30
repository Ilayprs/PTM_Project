package test;
import java.util.HashMap;
import java.util.Map;


public class TopicManagerSingleton {

    public static class TopicManager{
        private static final TopicManager instance = new TopicManager();
        private Map<String, Topic> topicsMap;
        private TopicManager(){
            topicsMap = new HashMap<>();
        }
        public Topic getTopic(String name) {
            if (topicsMap.containsKey(name)) {
                return topicsMap.get(name);
            }
            Topic newTopic = new Topic(name);
            topicsMap.put(name, newTopic);
            return newTopic;
        }
        public void clear(){
            this.topicsMap = new HashMap<>();
        }
    }
    public static TopicManager get(){
        return TopicManager.instance;
    }



}
