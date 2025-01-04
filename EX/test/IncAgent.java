package test;

import test.TopicManagerSingleton.TopicManager;

import java.util.ArrayList;

public class IncAgent implements Agent {

    public Topic subs;
    public Topic pubs;
    public Double num;
    public IncAgent(Topic subs , Topic pubs){
        this.subs = subs;
        this.pubs = pubs;
        num = 0.0;
    }

    @Override
    public String getName() {
        return "IncAgent";
    }

    @Override
    public void reset() {
        num = 0.0;
        subs = null;
        pubs = null;
    }

    @Override
    public void callback(String topic, Message msg) {
        if(topic.equals(subs.name)){
            num = msg.asDouble + 1;
            pubs.publish(new Message(num));
            pubs.addPublisher(this);
        }
    }

    @Override
    public void close() {
        subs.unsubscribe(this);
        pubs.removePublisher(this);
        this.reset();
    }
}
