package test;
import java.util.ArrayList;
import java.util.List;

public class Topic {
    public final String name;
    public ArrayList<Agent> subs;
    public ArrayList<Agent> pubs;

    Topic(String name){
        this.name=name;
        this.subs = new ArrayList<Agent>();
        this.pubs = new ArrayList<Agent>();
    }

    public void subscribe(Agent a){
        if(!this.subs.contains(a))
            subs.add(a);
    }
    public void unsubscribe(Agent a){
        for(Agent aa : this.subs){
            if(aa == a){
                this.subs.remove(aa);
            }
        }
    }

    public void publish(Message m){
        for(Agent a : this.subs){
            a.callback(this.name, m);
        }
    }

    public void addPublisher(Agent a){
        if(!this.pubs.contains(a))
            this.pubs.add(a);
    }

    public void removePublisher(Agent a){
        for(Agent p : this.subs){
            if(p == a){
                this.subs.remove(p);
            }
        }
    }

    public ArrayList<Agent> getSubs(){
        return this.subs;
    }

    public ArrayList<Agent> getPubs(){
        return this.pubs;
    }


}
