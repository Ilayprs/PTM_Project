package test;

import java.util.function.BinaryOperator;

import test.TopicManagerSingleton.TopicManager;

public class BinOpAgent implements Agent{

    private Node n;
    private Topic topic1;
    private Topic topic2;
    private Topic resultTopic;
    private BinaryOperator<Double> op;


    public BinOpAgent(String name, String topic1, String topic2, String resultTopic, BinaryOperator<Double> op) {
        this.n = new Node(name);
        this.topic1 = new Topic(topic1);
        this.topic2 = new Topic(topic2);
        this.resultTopic = new Topic(resultTopic);
        this.op = op;
        this.topic1.subscribe(this);
        this.topic2.subscribe(this);
        this.resultTopic.subscribe(this);
        if(!Double.isNaN(n.getMsg().asDouble)){
            this.topic1.publish(this.n.getMsg());
            this.topic2.publish(this.n.getMsg());
        }
        resultTopic = String.valueOf(op.apply(n.getMsg().asDouble, n.getMsg().asDouble));
        this.resultTopic.publish(new Message(resultTopic));


    }

    @Override
    public String getName() {
        return n.getName();
    }
    @Override
    public void reset(){
        this.n = new Node("0");
        this.topic1 = new Topic("0");
        this.topic2  = new Topic("0");
        this.resultTopic  = new Topic("0");
        this.op = null;
    }
    @Override
    public void close(){
        this.topic1.unsubscribe(this);
        this.topic2.unsubscribe(this);
        this.resultTopic.unsubscribe(this);
        this.topic1.removePublisher(this);
        this.topic2.removePublisher(this);
        this.resultTopic.removePublisher(this);
        this.reset();
    }
    @Override
    public void callback(String topic, Message msg){

    }

}
