package algorithm;

import util.DistanceCalculator;
import util.Location;
import util.Segment;
import util.Worker;

import java.util.ArrayList;
import java.util.List;

import static algorithm.AlgorithmUtil.getAllVaildWorkers;
import static algorithm.AlgorithmUtil.preprocess;

/**
 * @Desc 基于贪婪算法
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/16 下午1:43
 */
public class Algorithm1 {
    public void init(DataCarrier dataCarrier){
        List<List<Segment>> segmentCollections = preprocess(dataCarrier);
        allocate(segmentCollections,dataCarrier);
    }

    public void allocate(List<List<Segment>> segmentCollections, DataCarrier dataCarrier){
        for(int i = 0; i < segmentCollections.size();i++){

            boolean flag = workOnParticularSegments(segmentCollections.get(i),dataCarrier);
            System.out.println("处理第"+i+"条路径 "+flag);
            if(flag){
                return;
            }
        }
        System.out.println("分配失败==============");
        return;

    }

    public boolean workOnParticularSegments(List<Segment> segments, DataCarrier dataCarrier){
        List<Worker> allocation = new ArrayList<Worker>();
        List<Double> extraMD = new ArrayList<Double>();
        extraMD.add(0.0);
        helper(allocation, extraMD, segments, 0, segments.size()-1,dataCarrier);
        if(allocation.size()==segments.size()){
            System.out.println("分配成功！");
            System.out.println("分配方案长度为"+allocation.size()+", 最小额外移动距离为"+extraMD.get(0));
            for(Worker w : allocation){
                System.out.print(w.getId()+" ");
            }
            System.out.println();
        }
        return allocation.size()==segments.size();
    }

    public void helper(List<Worker> allocation, List<Double> minExtraMovingDistance, List<Segment> segments, int lo, int hi, DataCarrier dataCarrier){
        if(lo>hi)
            return;

        int farthest = lo;
        while (farthest<=hi){
            List<Worker> farthestReachable = new ArrayList<Worker>();
            List<Segment> temp = new ArrayList<Segment>();
            for(int i = lo; i <= farthest; i++){
                temp.add(segments.get(i));
            }
            farthestReachable = getAllVaildWorkers(temp,dataCarrier);
            if(farthestReachable.size()==0){
                break;
            }
            farthest++;
        }

        farthest--;
        if(farthest<lo){
            System.out.println("分配失败，无法找到符合条件的参与者");
            return;
        }

        List<Segment> temp = new ArrayList<Segment>();
        for(int i = lo; i <= farthest; i++){
            temp.add(segments.get(i));
        }
        List<Worker> localReachable = getAllVaildWorkers(temp,dataCarrier);
        Location src = temp.get(0).getSrc();

        Worker winner = null;
        double localmin = Double.MAX_VALUE;
        for(Worker w : localReachable){
            double tempDistance = DistanceCalculator.calculate(src,w);
            if(tempDistance<localmin){
                winner = w;
                localmin = tempDistance;
            }
        }

        for(int j = lo; j <= farthest; j++){
            allocation.add(winner);
        }

        double t = minExtraMovingDistance.get(0);
        minExtraMovingDistance.clear();
        minExtraMovingDistance.add(t+localmin);

        helper(new ArrayList<Worker>(allocation),new ArrayList<Double>(minExtraMovingDistance),segments,farthest+1,hi,dataCarrier);

    }

}
