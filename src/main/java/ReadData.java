import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by larsh on 3-6-2016.
 */
public class ReadData {

    public ArrayList<Integer> read() {
        String csvFile = getClass().getResource("swordforecast-data.csv").getFile();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<Integer> data = new ArrayList<Integer>();
        int i = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] value = line.split(cvsSplitBy);
                data.add(i, Integer.parseInt(value[1]));
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }
}