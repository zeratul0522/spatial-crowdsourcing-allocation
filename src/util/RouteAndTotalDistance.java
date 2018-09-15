package util;

import java.util.List;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/14 下午4:49
 */
public class RouteAndTotalDistance {
    private List<String> route;
    private double distance;

    public RouteAndTotalDistance(List<String> route, double distance) {
        this.route = route;
        this.distance = distance;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
