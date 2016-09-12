import jodd.json.JsonSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Reads a csv file and maps country name to a list of people who are from that country. 
 * Then, for each list sorts by last name.
 */
public class People {
    public static HashMap<String ,ArrayList<Person>> countriesMap = new HashMap<>();
    public static ArrayList<Person> personList = new ArrayList<>();
    public static ArrayList<Person> list1 = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        populateHashMapFromFile();
        sortMap();
        System.out.println(countriesMap);
        for (Map.Entry<String, ArrayList<Person>> entry : countriesMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Person> value = entry.getValue();
            list1.addAll(value);

        }
        writeFile("People.json", list1.toString() );



    }

    public static void populateHashMapFromFile() throws FileNotFoundException {

        File f = new File("people.csv");
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String[] columns = line.split(",");
            Person person = new Person(columns[0], columns[1], columns[2], columns[3], columns[4], columns[5]);
            String key = person.getCountry();

            if(countriesMap.containsKey(key)) {
                personList = countriesMap.get(key);
                personList.add(person);
            } else {
                personList = new ArrayList<>();
                personList.add(person);
                countriesMap.put(key, personList);

            }
        }
    }
    
    public static void sortMap() {
        for (Map.Entry<String, ArrayList<Person>> entry : countriesMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Person> value = entry.getValue();

            Collections.sort(value);


        }
    }

    static void writeFile(String fileName, String fileContent) throws IOException {
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.serialize(list1);
        File People = new File("People.json");
        FileWriter fw = new FileWriter(People);
        fw.write(json);
        fw.close();
    }
}
