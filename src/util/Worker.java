package util;

import java.util.List;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/15 下午7:23
 */
public class Worker {
    private long id;
    private String name;
    private double xordinate;
    private double yordinate;
    private List<Location> validLocations;

    public Worker(long id, String name, double xordinate, double yordinate, List<Location> validLocations) {
        this.id = id;
        this.name = name;
        this.xordinate = xordinate;
        this.yordinate = yordinate;
        this.validLocations = validLocations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Location> getValidLocations() {
        return validLocations;
    }

    public void setValidLocations(List<Location> validLocations) {
        this.validLocations = validLocations;
    }
}
