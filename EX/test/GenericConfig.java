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
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(configFile));

            // Initialize list and other variables
            List<String> lines = new LinkedList<>();
            String line;

            // Read lines from the configuration file
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            // Ensure that lines are divisible by 3, each agent should have a set of 3 lines (class name, subscribe topics, publish topics)
            if (lines.size() % 3 == 0) {
                Iterator<String> iterator = lines.iterator();

                // Process the configuration in chunks of 3 lines: className, toSub, toPub
                while (iterator.hasNext()) {
                    String className = iterator.next(); // Get the class name of the agent
                    String toSub = iterator.next();     // Get the subscribe topics (comma separated)
                    String toPub = iterator.next();     // Get the publish topics (comma separated)

                    // Load the agent class using reflection
                    try {
                        // Reflectively load the agent class
                        Class<?> clazz = Class.forName(className);
                        // Get the constructor with the appropriate parameters
                        Constructor<?> ctor = clazz.getConstructor(Topic[].class, Topic[].class);

                        // Create an instance of the agent
                        Object myAgent = ctor.newInstance((Object) toSub.split(","), (Object) toPub.split(","));
                        // Wrap the agent in a ParallelAgent
                        ParallelAgent pa = new ParallelAgent((Agent) myAgent);
                        // Add the created ParallelAgent to the list
                        list.add(pa);
                    } catch (ClassNotFoundException e) {
                        // Handle exception if class is not found
                    } catch (NoSuchMethodException e) {
                        // Handle exception if the constructor is not found
                    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        // Handle any errors during agent instantiation
                    }
                }
            } else {
                // Handle case where the configuration file has an invalid number of lines (not divisible by 3)
            }

            reader.close();
        } catch (FileNotFoundException e) {
            // Handle case where the file is not found
        } catch (IOException e) {
            // Handle IO exception during file reading
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
        list = new ArrayList<>();
    }
}
