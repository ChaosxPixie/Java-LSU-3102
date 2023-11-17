package dendrologist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A testbed for an augmented implementation of an AVL tree
 * @author William Duncan, Malana Fuentes
 * @see AVLTreeAPI
 * <pre>
 * Date: 10/19/2022
 * Course: CSC 3102
 * Programming Project # 2
 * Instructor: Dr. Duncan
 * </pre>
 */
public class Dendrologist {
    public static void main(String[] args) throws FileNotFoundException {
        String usage = "Dendrologist <order-code> <command-file>\n";
        usage += "  <order-code>:\n";
        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  -1 for reverse lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 ordered by decreasing string\n";
        usage += "  2 ordered by increasing string\n";
        usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";
        if (args.length != 2) {
            System.out.println(usage);
            throw new IllegalArgumentException("There should be 2 command line arguments.");
        }
        //complete the implementation of this method
        Function<String, PrintStream> fn = (x) -> System.out.printf("%s\n", x);
        FileReader f = new FileReader("strings.avl");
        Scanner fileReader = new Scanner(f);
        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };
        if (args[0].equals("0")) {
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()) {
                    return 1;
                } else if (str1.length() < str2.length()) {
                    return -1;
                } else if (str1.length() == str2.length()) {
                    return str2.compareTo(str1);
                }
                return str2.compareTo(str1);
            };
        }
        if (args[0].equals("-1")) {
            cmp = Comparator.reverseOrder();
        }
        if (args[0].equals("1")) {
            cmp = (str1, str2) -> str1.compareTo(str2);
        }
        if (args[0].equals("-2")) {
            cmp = (str1, str2) -> {
                if (str1.length() < str2.length()) {
                    return 1;
                } else if (str1.length() > str2.length()) {
                    return -1;
                } else
                    return 0;
            };
        }
        if (args[0].equals("2")) {
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()) {
                    return 1;
                } else if (str1.length() < str2.length()) {
                    return -1;
                } else
                    return 0;
            };
        }
        if (args[0].equals("-3")) {
            cmp = (str1, str2) -> {
                if (str1.length() < str2.length()) {
                    return 1;
                } else if (str1.length() > str2.length()) {
                    return -1;
                } else
                    return str2.compareTo(str1);
            };
        }
        if (args[0].equals("3")) {
            cmp = (str1, str2) -> {
                if (str1.length() > str2.length()) {
                    return 1;
                } else if (str1.length() < str2.length()) {
                    return -1;
                } else
                    return str1.compareTo(str2);
            };
        }

        AVLTree<String> tree = new AVLTree<>(cmp);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] statement = line.split(" ");
            if (statement[0].equals("stats")) {
                System.out.println("Stats: size = " + tree.size() + ", height = " + tree.height() + ", #full-nodes = " + tree.fullCount() + ", fibonacci? = " + tree.isFibonacci());
            } else if (statement[0].equals("insert")) {
                tree.insert(statement[1]);
                System.out.println("Inserted: " + statement[1]);
            } else if (statement[0].equals("paths")) {
                System.out.println("#Root-to-Leaf Paths: " + tree.genPaths().size());
                tree.genPaths().forEach(System.out::println);
            } else if (statement[0].equals("traverse")) {
                System.out.println("In-Order Traversal: ");
                tree.traverse(fn);
            } else if (statement[0].equals("delete")) {
                tree.remove(statement[1]);
                System.out.println("Deleted: " + statement[1]);
            } else
                throw new IllegalArgumentException("File Parsing Error");
        }
    }
    }
