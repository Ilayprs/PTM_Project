package test;

import test.TopicManagerSingleton.TopicManager;

import java.util.ArrayList;

public class IncAgent implements Agent {

    public ArrayList<Topic> subs;
    public ArrayList<Topic> pubs;
    public Double x , y;
    public IncAgent(ArrayList<Topic> subs , ArrayList<Topic> pubs){
        this.subs = subs;
        this.pubs = pubs;
        x = 0.0;
        y = 0.0;
    }

    @Override
    public String getName() {
        return "IncAgent";
    }

    @Override
    public void reset() {
        x = 0.0;
        y = 0.0;
    }

    @Override
    public void callback(String topic, Message msg) {
        subs.get(0).subscribe(this);
        subs.get(1).subscribe(this);
        String content = msg.asText;
        if (topic.equals(subs.get(0).name)) {
            x = parseToInt(content, x);
        } else if (topic.equals(subs.get(1).name)) {
            y = parseToInt(content, y);
        }

        if (x != 0.0 && y != 0.0) {
            double result = x + y;
            pubs.getFirst().publish(new Message(result));
            //pubs.get(0).publish(new Message(result));
        }

    }

    public static Double parseToInt(String content, Double x) {
        try {
            return Double.parseDouble(content);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void close() {
        subs.get(0).unsubscribe(this);
        subs.get(1).unsubscribe(this);
        this.reset();
    }
}
