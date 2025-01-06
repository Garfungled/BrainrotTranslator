// Possible Synonym object 
// don't know how I'd use this since we can't use lists but I'll see

public class Synonym {
    private String key;
    private String values;

    private int weight;

    public Synonym(String key, String values, int weight) {
        this.key = key;
        this.values = values;
        this.weight = weight;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
