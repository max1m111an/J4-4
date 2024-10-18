package SoVA;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tests_dir extends Test{
    private String name;
    private boolean visible;
    private int key_ad;
    private Set<Test> testSet;

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Tests_dir(Test[] tests, int key_ad, String name) {
        if(key_ad <= 0xfffff && key_ad > 0) {
            this.key_ad = key_ad;
        }
        this.testSet = new HashSet<>(List.of(tests));
        this.name = name;
    }

    public Set<Test> getTestSet() {
        return testSet;
    }

    public void setTestSet(Set<Test> s){
        this.testSet = s;
    }

    public void insertTest(Test test) {
        this.testSet.add(test);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printSet(){
        for(Test b: this.getTestSet()){
            System.out.print("{" + String.format("%X", b.getKey_a()) + "}; ");
        }
        System.out.println();
    }

    public int getKey_ad() {
        return key_ad;
    }

    public void setKey_ad(int key_ad) {
        this.key_ad = key_ad;
    }
}
