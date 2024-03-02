package Server_Java.server_tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordReader {
    public WordReader(){}

    /**
     * This method populates a List with words from the words.txt text file. The list will be used to verify the entered
     * words by players
     * @return list of valid words
     * @throws IOException
     */
    public List<String> populateWordList() throws IOException {
        List<String>listOfWords = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/Server_Java/server_res/words.txt"));

        String line = reader.readLine();
        while(line!=null){
            listOfWords.add(line);
            line = reader.readLine();
        }
        reader.close();
        return listOfWords;
    }
}
