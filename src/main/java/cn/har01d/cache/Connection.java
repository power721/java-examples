package cn.har01d.cache;

public class Connection {
    private int id;

    public Connection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                '}';
    }
}
