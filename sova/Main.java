package SoVA;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Set<Integer> key_s = new HashSet<>();
        int k1 = 0x1a;
        int k2 = 0x2b;
        int k3 = 0x3c;
        int k3_ = 0x55;
        int k4 = 0x7f;
        int k5 = 0xfffff;
        System.out.println(String.format("%05x", k5) + " " + k5);

        key_s.add(k1);
        key_s.add(k2);
        key_s.add(k3);
        key_s.add(k4);

        User user1 = new User("Jorjor Well", "12345");
        user1.setKeys(key_s);

        Test tt = new Test(k1);
        Tests_dir t1 = new Tests_dir(new Test[]{
                new Test(k1),
                new Test(k2)}, k4, "Dir_1");
        t1.insertTest(new Test(k3_));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input the password: ");
        String a = reader.readLine();
        if (!(a.equals(user1.getPassword()))) {
            return;
        }

        user1.printSet();
        t1.printSet();
        System.out.printf("%X%n", t1.getKey_ad());

        check_the_keys(user1, tt); //true
        check_the_keys_dir(user1, t1); //true
        check_the_keys_all_dir(user1, t1); //false
    }

    //verify the access for only dir
    public static void check_the_keys_dir(User u1, Tests_dir td1) {
        System.out.print("Verify your access key for the directory...     ");
        boolean con = u1.getKeys().contains(td1.getKey_ad());
        td1.setVisible(con);
        System.out.println("visible=" + con + "; Access " + (con ? "confirmed." : "denied."));

    }

    //verify the access for all test in dir
    public static void check_the_keys_all_dir(User u1, Tests_dir td1) {
        System.out.print("Verify your access key for the whole directory...     ");
        boolean con = true;
        for (Test t : td1.getTestSet()) {
            if (!u1.getKeys().contains(t.getKey_a())) {
                con = false;
                break;
            }
        }
        td1.setVisible(con);
        System.out.println("visible=" + con + "; Access " + (con ? "confirmed." : "denied."));
    }

    //verify the access for the test
    public static void check_the_keys(User u1, Test t1) {
        System.out.print("Verify your access key for the test...     ");
        boolean con = u1.getKeys().contains(t1.getKey_a());
        t1.setVisible(con);
        System.out.println("visible=" + con + "; Access " + (con ? "confirmed." : "denied."));
    }
}