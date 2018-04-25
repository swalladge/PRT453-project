package Car;

import java.io.Serializable;

public class Manufacturer implements Serializable {

    private String name;

    public Manufacturer(String name) {
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
