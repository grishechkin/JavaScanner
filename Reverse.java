import java.util.Arrays;
import java.io.IOException;

public class Reverse {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            try {
                IntList numbers = new IntList();
                IntList lineNumbers = new IntList();

                while (in.hasNext()) {
                    numbers.add(in.nextInt());
                    lineNumbers.add(in.getValueLineNumber());
                }

                int prevLine = in.getLastLineNumber() - 1;
                for (int i = numbers.size() - 1; i >= 0; i--) {
                    for (int j = 0; j < prevLine - lineNumbers.get(i); j++) {
                        System.out.println();
                    }
                    System.out.print(numbers.get(i) + " ");
                    prevLine = lineNumbers.get(i);
                }
                
                if (prevLine != 0) {
                    for (int i = 0; i < prevLine + 1; i++) {
                        System.out.println();
                    }
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.out.println("I/O exception " + e.getMessage());
        }
    }
}