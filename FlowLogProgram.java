import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowLogProgram {
    /**
     * Compile your Java code by typing “javac [filename].java” in the command prompt/terminal
     *
     * A static variable representing a look-up table mapping integers to maps of strings.
     * Each integer key corresponds to a map that stores string key-value pairs in lower case.
     * Assumptions made: For every port&protocol combination, there will be only one tag.
     */
    static Map<Integer, Map<String, String>> lookUpTable;
    static Map<String,Integer> tagCounts;
    static Map<String,Map<String, Integer>> portProtocolCounts;
    public static void main(String[] args) {

        // input structure: dstport, protocol
        // lookup has the structure:  dstport,protocol,tag
        // output: Count of matches for each tag, sample o/p shown below
        // 		   Count of matches for each port/protocol combination

        lookUpTable = new HashMap<>();

        // Read the lookup and flowlog data file. NOTE: Please provide the input ( look up table and flow log data) file in the same location where this program run from
        readLookup("lookup.csv");
        List<String> input = readFlowData("flowlogdata.csv");
        // maps to compute the required counts
        tagCounts = new HashMap<>();
        portProtocolCounts = new HashMap<>();

        // Iterate through the flow log data
        for (int i=0; i<input.size(); i++) {

            String[] parts = input.get(i).split(",");
            String port = parts[0]; String protocol = parts[1].toLowerCase(); // convert to lowercase by default
            if(lookUpTable.containsKey(Integer.parseInt(port)) && lookUpTable.get(Integer.parseInt(port)).containsKey(protocol) ) {
                String tag = lookUpTable.get(Integer.parseInt(port)).get(protocol);
                tagCounts.put(tag, tagCounts.getOrDefault(tag, 0)+1);

                Map<String,Integer> protocolMap = portProtocolCounts.getOrDefault(port, new HashMap<>());
                protocolMap.put(protocol, protocolMap.getOrDefault(protocol, 0)+1);
                portProtocolCounts.put(port, protocolMap);
            } else {
                tagCounts.put("untagged",tagCounts.getOrDefault("untagged",0)+1);
            }
        }

        tagCounts.entrySet().stream().forEach(entry -> System.out.println("Tag: "+ entry.getKey() + " : " + entry.getValue()));
        portProtocolCounts.entrySet().stream().forEach(entry -> System.out.println("Port: "+ entry.getKey() + ", Protocol/Count: " + entry.getValue()));

        // write output counts to a file as per problem statement
        writeTabCounts("tag_counts.csv");
        writePortProtocolCounts("port_protocol_counts.csv");

    }

    private static void writePortProtocolCounts(String file) {
        try (FileWriter csvWriter = new FileWriter(file)) {
            // Write header row
            csvWriter.append("Port,Protocol,Count\n");
            // Write data row
            for (Map.Entry<String,Map<String, Integer>> entry : portProtocolCounts.entrySet()) {
                Map<String, Integer> protocolMap = entry.getValue();
                for (Map.Entry<String, Integer> protocolEntry : protocolMap.entrySet()) {
                    csvWriter.append(entry.getKey()).append(",").append(protocolEntry.getKey()).append(",").append(String.valueOf(protocolEntry.getValue())).append("\n");
                }
            }
            // Close the csvWriter to save the data
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeTabCounts(String file) {
        try (FileWriter csvWriter = new FileWriter(file)) {
            // Write header row
            csvWriter.append("Tag,Count\n");
            // Write data row
            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                csvWriter.append(entry.getKey()).append(",").append(String.valueOf(entry.getValue())).append("\n");
            }
            // Close the csvWriter to save the data
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readLookup(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); //skip the headers
            String line;
            while ((line=br.readLine()) !=null) {
                String[] values = line.split(",");
                lookUpTable.put(Integer.parseInt(values[0]), Map.of(values[1].toLowerCase(), values[2].toLowerCase())); // convert to lowercase by default
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> readFlowData(String file) {
        List<String> input = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); //skip the headers
            String line;
            while ((line=br.readLine()) !=null) {
                input.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return input;
    }

}
