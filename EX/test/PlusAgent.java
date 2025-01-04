package test;

import java.util.ArrayList;

public class PlusAgent implements Agent {

    public Topic subs;
    public Topic pubs;

    public Double x , y;
    public PlusAgent(Topic subs , Topic pubs){
        this.subs = subs;
        this.pubs = pubs;
        x = 0.0;
        y = 0.0;
    }
    @Override
    public String getName() {
        return "PlusAgent";
    }

    @Override
    public void reset() {
        subs = null;
        pubs = null;
        x = 0.0;
        y = 0.0;
    }

    @Override
    public void callback(String topic, Message msg) {
        if(topic.equals(subs.name)){
            x = msg.asDouble;
        }
        if(topic.equals(subs.name)){
            y = msg.asDouble;
        }
        if(x != 0.0 && y != 0.0){
            double result = x + y;
            pubs.publish(new Message(result));
        }

    }

    @Override
    public void close() {
        subs.unsubscribe(this);
        pubs.removePublisher(this);
        reset();
    }
}
