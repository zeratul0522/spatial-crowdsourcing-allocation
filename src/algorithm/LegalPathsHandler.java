package algorithm;

import config.OptimalGoal;
import config.Parameters;
import util.DistanceCalculator;
import util.Location;
import util.RouteAndTotalDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/14 下午7:06
 */
public class LegalPathsHandler {
    public static List<RouteAndTotalDistance> execute(DataCarrier dataCarrier) {
        long startTime=System.currentTimeMillis();
        System.out.println("开始初步计算排序后的合法路径集合=================================当前优化目标："+ Parameters.goal +"=====================================");
        List<List<String>> list = ExecutivePathsFinder.execute();
        LegalPathsHandler legalPathsHandler = new LegalPathsHandler();
        List<RouteAndTotalDistance> routes = new ArrayList<RouteAndTotalDistance>();
        //TODO:此处选择根据不同的优化目标执行不同的排序方法
        if(Parameters.goal== OptimalGoal.MIN_EXTRA_MOVING_DISTANCE){
            routes = legalPathsHandler.sortByMinTotalDistance(list,dataCarrier);
        }
        for(RouteAndTotalDistance route : routes){
            List<String> tmp = route.getRoute();
            System.out.print(tmp.toString());
            System.out.print(route.getDistance());
            System.out.println();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("计算完成，耗时: "+ (endTime-startTime)+"ms ==================================================================================================================");

        return routes;

    }
    public List<RouteAndTotalDistance> sortByMinTotalDistance(List<List<String>> list, DataCarrier dataCarrier){
        List<RouteAndTotalDistance> routesWtihDistance = new ArrayList<RouteAndTotalDistance>();
        for(List<String> temp : list){
            double tempTotalDistance = 0;
            if(temp.size()>=2){
                for(int i = 0; i < temp.size()-1;i++){
                    long aid = dataCarrier.queryTableByName.get(temp.get(i));
                    long bid = dataCarrier.queryTableByName.get(temp.get(i+1));

                    Location a = dataCarrier.queryTableById.get(aid);
                    Location b = dataCarrier.queryTableById.get(bid);

                    double distance = DistanceCalculator.calculate(a,b);
                    tempTotalDistance += distance;
                }
            }
            RouteAndTotalDistance routeAndTotalDistance = new RouteAndTotalDistance(temp,tempTotalDistance);
            routesWtihDistance.add(routeAndTotalDistance);
        }
        Collections.sort(routesWtihDistance, new Comparator<RouteAndTotalDistance>() {
            @Override
            public int compare(RouteAndTotalDistance o1, RouteAndTotalDistance o2) {
                double distance1 = o1.getDistance();
                double distance2 = o2.getDistance();
                return (int)(distance1-distance2);
            }
        });

//        for(RouteAndTotalDistance r : routesWtihDistance){
//            System.out.println(r.getDistance());
//        }
        return routesWtihDistance;
    }
}
