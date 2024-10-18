package SoVA;

public class Test {
    private boolean visible;
    private int key_a;

    public Test(int key_a) {
        this.visible = false;
        if(key_a <= 0xfffff && key_a > 0) {
            this.key_a = key_a;
        }
    }

    public Test() {

    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getKey_a() {
        return key_a;
    }

    public void setKey_a(byte key_a) {
        this.key_a = key_a;
    }
}
