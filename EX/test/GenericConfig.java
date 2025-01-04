package test;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GenericConfig implements Config{
    public String configFile;
    List<ParallelAgent> list;
    @Override
    public void create() {
        String[] parts = null;
        String className = null;
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        try(BufferedReader in = new BufferedReader(new FileReader(configFile))){
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            if(lines.size() % 3 == 0){
                for(int i = 0 ; i < lines.size() ; i++){
                    parts = lines.get(i).split("\\.");
                    className = parts[parts.length - 1];
                    clazz = Class.forName(className);
                    constructor = clazz.getConstructor(Topic.class , Topic.class);

                    String[] subs = lines.get(i + 1).split(",");
                    String[] pubs = lines.get(i + 2).split(",");

                    List<Topic> subTopics = new ArrayList<>();
                    List<Topic> pubTopics = new ArrayList<>();

                    for (String subTopicName : subs) {
                        Topic subTopic = new Topic(subTopicName);
                        subTopics.add(subTopic);
                    }

                    for (String pubTopicName : pubs) {
                        Topic pubTopic = new Topic(pubTopicName);
                        pubTopics.add(pubTopic);
                    }

                    Agent agentInstance = (Agent) constructor.newInstance(subTopics.get(0), pubTopics.get(0));
                    ParallelAgent parallelAgent = new ParallelAgent(agentInstance);

                    for (Topic subTopic : subTopics) {
                        subTopic.subscribe(parallelAgent);
                    }
                    for (Topic pubTopic : pubTopics) {
                        pubTopic.addPublisher(parallelAgent);
                    }

                    list.add(parallelAgent);
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public String getName() {
        return "GenericConfig";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void close() {
        for(ParallelAgent agent : list) {
            agent.close();
        }
    }

    public void setConfFile(String configFile) {
        this.configFile = configFile;
    }
}
