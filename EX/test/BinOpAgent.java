package test;

import java.util.function.BinaryOperator;

import test.TopicManagerSingleton.TopicManager;

import static java.lang.Double.isNaN;

public class BinOpAgent implements Agent{

    private Node n;
    private Topic topic1;
    private Topic topic2;
    private Topic resultTopic;
    private BinaryOperator<Double> op;

    private Double value1;
    private Double value2;


    public BinOpAgent(String name, String topic1, String topic2, String resultTopic, BinaryOperator<Double> op) {
        this.n = new Node(name);
        this.topic1 = new Topic(topic1);
        this.topic2 = new Topic(topic2);
        this.resultTopic = new Topic(resultTopic);
        this.op = op;

        this.topic1.subscribe(this);
        this.topic2.subscribe(this);
        this.resultTopic.subscribe(this);

        if(!isNaN((n.getMsg().asDouble))){
            this.topic1.publish(this.n.getMsg());
            this.topic2.publish(this.n.getMsg());
        }

        Double result = this.op.apply(this.n.getMsg().asDouble, this.n.getMsg().asDouble);
        this.resultTopic.publish(new Message(String.valueOf(result)));

        this.value1 = null;
        this.value2 = null;
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
        if(msg != null){
            if(topic1.name.equals(topic)){
                value1 = (Double) msg.asDouble;
            }
            if(topic2.name.equals(topic)){
                value2 = (Double) msg.asDouble;
            }
        }
        if(!isNaN(value1) && !isNaN(value2)){
            double result = op.apply(value1, value2);
            this.resultTopic.publish(new Message(String.valueOf(result)));
        }
        value1 = null;
        value2 = null;
    }

}
