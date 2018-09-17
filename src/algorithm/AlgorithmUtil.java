package algorithm;

import util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/16 下午1:52
 */
public class AlgorithmUtil {
    /*
    给定连续的几段segment，返回所有能单独完成的worker
     */
    public static List<Worker> getAllVaildWorkers(List<Segment> segments, DataCarrier dataCarrier){
        List<List<Worker>> temp = new ArrayList<List<Worker>>();
        for(Segment s : segments){
            Location src = s.getSrc();
            Location dest = s.getDest();
            temp.add(getAllValidWorkers4Location(src,dataCarrier));
            temp.add(getAllValidWorkers4Location(dest,dataCarrier));
        }

        List<Worker> validWorkers = retainAll(temp);
        return validWorkers;
    }

    /*
    给定一个Location，返回所有能单独完成的worker
     */
    public static List<Worker> getAllValidWorkers4Location(Location l, DataCarrier dataCarrier){
        List<Worker> ret = new ArrayList<Worker>();
        for(Worker w : dataCarrier.getWorkers()){
            if(w.getValidLocations().contains(l)){
                ret.add(w);
            }
        }
        return ret;
    }

    /*
    给定多个List<Worker>，返回它们的交集
     */
    public static List<Worker> retainAll(List<List<Worker>> input){
        List<Worker> ret = new ArrayList<Worker>();
        if(input.size()==0){
            return ret;
        }else if(input.size()==1){
            return input.get(0);
        }else {
            ret = input.get(0);
            for(int i = 1; i < input.size(); i++){
                ret.retainAll(input.get(i));
            }
            return ret;
        }
    }

    /*
    给定一个参与者的列表和一个地点，找到其中离该地点最近的参与者
     */
    public static Worker findClosest(List<Worker> workers, Location l){
        Worker winner = null;
        double min = Double.MAX_VALUE;
        for(Worker w : workers){
            if(DistanceCalculator.calculate(l,w)<min){
                winner = w;
                min = DistanceCalculator.calculate(l,w);
            }
        }
        return winner;
    }

    public static double findClosestDistance(List<Worker> workers, Location l){
        Worker winner = null;
        double min = Double.MAX_VALUE;
        for(Worker w : workers){
            if(DistanceCalculator.calculate(l,w)<min){
                winner = w;
                min = DistanceCalculator.calculate(l,w);
            }
        }
        return min;
    }


    /*
    对数据进行预处理，返回segment列表的集合
     */
    public static List<List<Segment>> preprocess(DataCarrier dataCarrier){
        List<List<Segment>> ret = new ArrayList<List<Segment>>();
        List<RouteAndTotalDistance> temp = LegalPathsHandler.execute(dataCarrier);

        for(RouteAndTotalDistance r : temp){
            List<String> locationStrs = r.getRoute();
            List<Location> locationsOfSingleRoute = new ArrayList<Location>();
            for(String s : locationStrs){
                locationsOfSingleRoute.add(dataCarrier.queryTableById.get(dataCarrier.queryTableByName.get(s)));
            }
            List<Segment> segments = new ArrayList<Segment>();
            if(locationsOfSingleRoute.size()>=2){
                for(int i = 0; i < locationsOfSingleRoute.size()-1; i++){
                    segments.add(new Segment(locationsOfSingleRoute.get(i),locationsOfSingleRoute.get(i+1)));
                }
            }
            ret.add(segments);

        }
        return ret;
    }
}
