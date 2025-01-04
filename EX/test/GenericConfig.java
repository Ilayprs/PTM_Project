package test;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GenericConfig implements Config{
    public String configFile;
    List<ParallelAgent> list;
    @Override
    public void create() {
        String[] parts = null;
        String className = null;
        List<Topic> topicSet = new ArrayList<>();
        Class<?> clazz = null;
        ParallelAgent agent = null;
        Scanner scanner = null;
        int i_subs = 0;
        int i_pubs = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader("configFile"));
            List<String> lines = Files.readAllLines(Paths.get("configFile"));

            if (lines.size() % 3 != 0) {
                throw new IllegalArgumentException("Invalid number of lines in the configuration file.");
            }

            for (int i = 0; i < lines.size(); i++) {
                parts = lines.get(i).split("\\.");
                className = parts[parts.length - 1];
                clazz = Class.forName(className);

                if (clazz.getDeclaredConstructors().length == 0) {
                    throw new NoSuchMethodException("Class " + className + " does not have a no-argument constructor.");
                }

                agent = (ParallelAgent) clazz.getDeclaredConstructor().newInstance(); // created the specific Agent

                for (int j = i; j < i + 2; j++, i++) {
                    scanner = new Scanner(lines.get(j));
                    scanner.useDelimiter(",");
                    while (scanner.hasNext()) {
                        String topicName = scanner.next();
                        Topic newTopic = new Topic(topicName);
                        topicSet.add(newTopic);
                        newTopic.subscribe(agent);
                        i_subs++;
                    }

                    scanner = new Scanner(lines.get(j + 1));
                    while (scanner.hasNext()) {
                        String topicName = scanner.next();
                        Topic newTopic = new Topic(topicName);
                        topicSet.add(newTopic);
                        newTopic.addPublisher(agent);
                        i_pubs++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getVersion() {
        return 0;
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
