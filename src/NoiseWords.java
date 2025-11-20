import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class NoiseWords {

    private Map<String, Integer> wordFrequency;
    private ArrayList<String> stopWords = new ArrayList<>();

    public NoiseWords(Map<String, Integer> wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public ArrayList<String> getStopWords() {
        stopWords.clear();

        for(Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            String word = entry.getKey();
            int frequency = entry.getValue();

            if(frequency >= 20){
                stopWords.add(word);
            }
        }
        return stopWords;
    }

    public void printStopWords(){
        getStopWords();
        System.out.println("Stop words (count>=20) ");
        System.out.println(stopWords);
    }

}

