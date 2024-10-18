package SoVA;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Set<Integer> keys;
    private String name;
    private String password;

    public User(String name, String password) {
        this.keys = new HashSet<>();
        this.name = name;
        this.password = password;
    }

    public Set<Integer> getKeys() {
        return keys;
    }

    public void insertKey(int keys) {
        this.keys.add(keys);
    }

    public void setKeys(Set<Integer> keys){
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void printSet(){
        for(Integer b: this.getKeys()){
            System.out.print("{" + String.format("%X", b) + "}; ");
        }
        System.out.println();
    }
}
