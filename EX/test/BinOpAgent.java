package test;

import test.TopicManagerSingleton.TopicManager;
import java.util.function.BinaryOperator;

public class BinOpAgent implements Agent {

    private Node n;
    private Topic t1, t2, t3;
    private BinaryOperator<Double> b;
    private Double value1 = null;
    private Double value2 = null;

    public BinOpAgent(String name, String firstTopic, String secondTopic, String out, BinaryOperator<Double> b) {
        n = new Node(name);
        t1 = TopicManagerSingleton.get().getTopic(firstTopic);
        t2 = TopicManagerSingleton.get().getTopic(secondTopic);
        t3 = TopicManagerSingleton.get().getTopic(out);

        t1.subscribe(this);
        t2.subscribe(this);

        this.b = b;
    }

    @Override
    public void callback(String topic, Message msg) {
        if (topic.equals(t1.name)) {
            value1 = msg.asDouble;
        } else if (topic.equals(t2.name)) {
            value2 = msg.asDouble;
        }

        if (value1 != null && value2 != null) {
            double result = b.apply(value1, value2);
            t3.publish(new Message(result));
            t3.addPublisher(this);
        }
    }

    @Override
    public void reset() {
        value1 = null;
        value2 = null;
    }

    @Override
    public String getName() {
        return n.getName();
    }

    @Override
    public void close() {
        t1.unsubscribe(this);
        t2.unsubscribe(this);
        t3.removePublisher(this);
    }
}
