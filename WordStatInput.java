import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class WordStatInput {
    public static class Checker implements CharChecker {
        public boolean check(char c) {
            return c == '\'' || Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION;
        }
    }

    public static void addWord(String word, Map<String, Integer> answer, List<String> words) {
        if (answer.containsKey(word)) {
            answer.put(word, answer.get(word) + 1);
        } else {
            words.add(word);
            answer.put(word, 1);
        }
    }

    public static void main(String[] args) {
        try {
            Map<String, Integer> answer = new HashMap<>();
            List<String> words = new ArrayList<>();

            Scanner in = new Scanner(new FileInputStream(args[0]), new Checker());
            try {
                while (in.hasNext()) {
                    addWord(in.next().toLowerCase(), answer, words);
                }
            } finally {
                in.close();
            }

            try {
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                    for (String word : words) {
                        writer.write(word + " " + answer.get(word));
                        writer.newLine();
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Output error: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }
}