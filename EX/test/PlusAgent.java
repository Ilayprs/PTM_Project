package test;

import java.util.ArrayList;

public class PlusAgent implements Agent {

    public ArrayList<Topic> subs;
    public ArrayList<Topic> pubs;
    public PlusAgent(ArrayList<Topic> subs , ArrayList<Topic> pubs){
        this.subs = subs;
        this.pubs = pubs;
    }
    @Override
    public String getName() {
        return "PlusAgent";
    }

    @Override
    public void reset() {

    }

    @Override
    public void callback(String topic, Message msg) {
        double num = 0.0;
        //subs.get(0).subscribe(this);
        subs.getFirst().subscribe(this);
        String content = msg.asText;
        if (topic.equals(subs.getFirst().name)) {
            num = IncAgent.parseToInt(content, num);
        }
        if(num != 0.0){
            num++;
            pubs.getFirst().publish(new Message(num));
        }

    }

    @Override
    public void close() {
        //subs.get(0).unsubscribe(this);
        subs.getFirst().unsubscribe(this);
        reset();
    }
}
