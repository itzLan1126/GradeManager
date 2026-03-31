// CLI formatting utilities
public class CLI {

    // screen width for CLI output
    public static final int CLI_WIDTH = 56;

    // print a separator line using the given character
    public static void printSeparator(char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CLI_WIDTH; i++) {
            sb.append(c);
        }
        System.out.println(sb.toString());
    }

    // print a centered header with separator lines
    public static void printHeader(String title) {
        System.out.println();
        printSeparator('=');
        int padding = (CLI_WIDTH - title.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++)
            sb.append(" ");
        sb.append(title);
        System.out.println(sb.toString());
        printSeparator('=');
    }

    // truncate string to max length with ellipsis
    public static String truncate(String str, int length) {
        if (str == null)
            return "";
        if (str.length() <= length)
            return str;
        return str.substring(0, length - 3) + "...";
    }
}
