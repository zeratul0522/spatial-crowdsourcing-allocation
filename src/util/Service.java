package util;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/14 下午4:06
 */
public class Service {
    private String name;
    private long id;
    private double xordinate;
    private double yordinate;

    public Service(String name, long id, double xordinate, double yordinate) {
        this.name = name;
        this.id = id;
        this.xordinate = xordinate;
        this.yordinate = yordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getXordinate() {
        return xordinate;
    }

    public void setXordinate(double xordinate) {
        this.xordinate = xordinate;
    }

    public double getYordinate() {
        return yordinate;
    }

    public void setYordinate(double yordinate) {
        this.yordinate = yordinate;
    }
}
