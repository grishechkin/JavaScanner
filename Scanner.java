import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Scanner {
    public static final int MAIN_BUFFER_SIZE = 1024;
    private final Reader reader;
    private char[] buffer;
    private int curPos;
    private int readLength;
    private int lineNumber;
    private int userLineNumber;
    private int lineSepPos;
    private StringBuilder token;
    private final CharChecker checker;
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static class DefaultChecker implements CharChecker {
        public boolean check(char c) {
            return !Character.isWhitespace(c);
        }
    }

    private void init() throws IOException {
        buffer = new char[MAIN_BUFFER_SIZE];
        token = new StringBuilder();
        curPos = 0;
        lineNumber = 0;
        readLength = 0;
        userLineNumber = 0;
        lineSepPos = 0;

        nextToken();
    }

    public Scanner(InputStream input) throws IOException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input, StandardCharsets.UTF_8));

        this.checker = new DefaultChecker();
        init();
    }

    public Scanner(InputStream input, CharChecker checker) throws IOException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input, StandardCharsets.UTF_8));

        this.checker = checker;
        init();
    }

    private void read() throws IOException {
        readLength = reader.read(buffer);
        curPos = 0;
    }

    private boolean isLineSeparator() {
        if (lineSepPos == LINE_SEPARATOR.length()) {
            lineSepPos = 0;
            return true;
        }
        return false;
    }

    private boolean isSep(char c) {
        if (!checker.check(c)) {
            if (c == LINE_SEPARATOR.charAt(lineSepPos)) {
                lineSepPos++;
            }
            return true;
        }
        return false;
    }

    private boolean checkPos() throws IOException {
        if (curPos >= readLength) {
            read();
            return readLength != -1;
        }
        return true;
    }

    private void nextToken() throws IOException {
        if (isLineSeparator()) {
            lineNumber++;
            curPos++;
        }
        while (checkPos() && isSep(buffer[curPos])) {
            if (isLineSeparator()) {
                lineNumber++;
            }
            curPos++;
        }
        if (isLineSeparator()) {
            lineNumber++;
            curPos++;
        }
        while (checkPos() && !isSep(buffer[curPos])) {
            token.append(buffer[curPos++]);
        }
    }

    private void updateToken() throws IOException {
        token.delete(0, token.length());
        userLineNumber = lineNumber;
        nextToken();
    }

    public boolean hasNext() throws IOException {
        return !token.isEmpty();
    }

    public String next() throws IOException {
        String ret = new String(token);
        updateToken();
        return ret;
    }

    public int nextInt() throws NumberFormatException, IOException {
        int ret = Integer.parseInt(token.toString());
        updateToken();
        return ret;
    }

    public int getValueLineNumber() {
        return userLineNumber;
    }

    public int getLastLineNumber() {
        return lineNumber;
    }

    public void close() throws IOException {
        reader.close();
    }
}
