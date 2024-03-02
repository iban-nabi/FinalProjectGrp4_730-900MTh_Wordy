package Server_Java.server_tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLettersGenerator {
    public RandomLettersGenerator(){}

    /**
     * This method generates the 17 characters through randomizing and validating the number of vowels to be used
     * @return list of characters to be used in a specfic game
     */
    public List<Character> generateLetters(){
        boolean flag=false;
        Random r = new Random();
        char[] vowelList = {'A','E','I','O','U'};
        List<Character> listOfLetter = new ArrayList<>();

        while(true){
            int countVowels=0;
            boolean alternate=true;
            while(listOfLetter.size()<17){
                char c = (char)(r.nextInt(26) + 'A');
                if(alternate){
                    if(Collections.frequency(listOfLetter,c)<2){
                        listOfLetter.add(c);
                        alternate=false;
                    }
                }else{
                    if(Collections.frequency(listOfLetter,c)<1){
                        listOfLetter.add(c);
                        alternate=true;
                    }
                }
            }

            for (char c : vowelList) {
                if (listOfLetter.contains(c)) {
                    countVowels = countVowels + Collections.frequency(listOfLetter, c);
                }
            }

            if(countVowels>4  && countVowels<8){
                break;
            }
            listOfLetter.clear();
        }

        return listOfLetter;
    }
}
