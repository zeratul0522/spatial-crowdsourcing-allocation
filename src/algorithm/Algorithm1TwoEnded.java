package algorithm;

import util.DistanceCalculator;
import util.Location;
import util.Segment;
import util.Worker;

import java.util.ArrayList;
import java.util.List;

import static algorithm.AlgorithmUtil.getAllVaildWorkers;
import static algorithm.AlgorithmUtil.preprocess;
import static algorithm.AlgorithmUtil.retainAll;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/10/9 下午6:22
 */
public class Algorithm1TwoEnded {
    public void init(DataCarrier dataCarrier){
        List<List<Segment>> segmentCollections = preprocess(dataCarrier);
        allocate(segmentCollections,dataCarrier);
    }

    public void allocate(List<List<Segment>> segmentCollections, DataCarrier dataCarrier){
        System.out.println();
        System.out.println("算法1(双端法)开始分配：");
        for(int i = 0; i < segmentCollections.size();i++){
            boolean flag = workOnParticularSegments(segmentCollections.get(i),dataCarrier);
            System.out.println("双端法处理第"+i+"条路径 "+flag);
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
        helper(allocation, extraMD, new ArrayList<Worker>(), new ArrayList<Worker>(), segments, 0, segments.size()-1,dataCarrier);
        if(allocation.size()==segments.size()){
            System.out.println("分配成功！");
            System.out.println("分配方案长度为"+allocation.size()+", 最短额外移动距离="+extraMD.get(0));
            for(Worker w : allocation){
                System.out.print(w.getId()+" ");
            }
            System.out.println();
        }
        System.out.println("allocation.size()="+allocation.size()+"segments.size()="+segments.size());
        return allocation.size()==segments.size();
    }

    public void helper(List<Worker> allocation, List<Double> minExtraMovingDistance, List<Worker> leftAdded, List<Worker> rightAdded, List<Segment> segments, int lo, int hi, DataCarrier dataCarrier){
        if(lo>hi){
            allocation.clear();
            for(Worker w : leftAdded){
                allocation.add(w);
            }
            for(Worker w : rightAdded){
                allocation.add(w);
            }
            return;
        }

        int leftFarthest = lo;
        int rightFarthest = hi;

        //从左往右推算
        while (leftFarthest<=hi){
            List<Worker> farthestReachable = new ArrayList<Worker>();
            List<Segment> temp = new ArrayList<Segment>();
            for(int i = lo; i <= leftFarthest; i++){
                temp.add(segments.get(i));
            }
            farthestReachable = getAllVaildWorkers(temp,dataCarrier);
            if(farthestReachable.size()==0){
                break;
            }
            leftFarthest++;
        }

        leftFarthest--;
        if(leftFarthest<lo){
            System.out.println("左端分配失败，无法找到符合条件的参与者");
            return;
        }

        //从右往左推算
        while (rightFarthest>=lo){
            List<Worker> farthestReachable = new ArrayList<Worker>();
            List<Segment> temp = new ArrayList<Segment>();
            for(int i = hi; i >= rightFarthest; i--){
                temp.add(segments.get(i));
            }
            farthestReachable = getAllVaildWorkers(temp,dataCarrier);
            if(farthestReachable.size()==0){
                break;
            }
            rightFarthest--;
        }
        rightFarthest++;
        if(rightFarthest>hi){
            System.out.println("右端分配失败，无法找到符合条件的参与者");
            return;
        }


        //未相交
        if(leftFarthest<rightFarthest){
            //left
            List<Worker> left = new ArrayList<Worker>();
            List<Worker> right = new ArrayList<Worker>();

            List<Segment> temp = new ArrayList<Segment>();
            for(int i = lo; i <= leftFarthest; i++){
                temp.add(segments.get(i));
            }
            List<Worker> localLeftReachable = getAllVaildWorkers(temp,dataCarrier);
            Location src = temp.get(0).getSrc();

            Worker winner = null;
            double localminLeft = Double.MAX_VALUE;
            for(Worker w : localLeftReachable){
                double tempDistance = DistanceCalculator.calculate(src,w);
                if(tempDistance<localminLeft){
                    winner = w;
                    localminLeft = tempDistance;
                }
            }

            for(int j = lo; j <= leftFarthest; j++){
                left.add(winner);
            }

            //right
            List<Segment> temp2 = new ArrayList<Segment>();
            for(int i = rightFarthest; i <= hi; i++){
                temp2.add(segments.get(i));
            }
            List<Worker> localRightReachable = getAllVaildWorkers(temp2,dataCarrier);
            Location src2 = temp2.get(0).getSrc();

            Worker winner2 = null;
            double localminRight = Double.MAX_VALUE;
            for(Worker w : localRightReachable){
                double tempDistance = DistanceCalculator.calculate(src2,w);
                if(tempDistance<localminRight){
                    winner2 = w;
                    localminRight = tempDistance;
                }
            }

            for(int j = rightFarthest; j <= hi; j++){
                right.add(winner2);
            }

            for(Worker w : left){
                leftAdded.add(w);
            }
            List<Worker> tempRight = new ArrayList<Worker>();
            for(Worker w : right){
                tempRight.add(w);
            }
            for(Worker w : rightAdded){
                tempRight.add(w);
            }

            double t = minExtraMovingDistance.get(0);
            minExtraMovingDistance.clear();
            minExtraMovingDistance.add(t+localminLeft+localminRight);

            helper(allocation, minExtraMovingDistance, leftAdded, tempRight,segments,leftFarthest+1, rightFarthest-1,dataCarrier);
        }else {
            //相交
            List<Worker> left = new ArrayList<Worker>();
            List<Worker> right = new ArrayList<Worker>();

            List<Segment> temp = new ArrayList<Segment>();
            for(int i = lo; i <= leftFarthest; i++){
                temp.add(segments.get(i));
            }
            List<Worker> localLeftReachable = getAllVaildWorkers(temp,dataCarrier);
            Location src = temp.get(0).getSrc();

            Worker winner = null;
            double localminLeft = Double.MAX_VALUE;
            for(Worker w : localLeftReachable){
                double tempDistance = DistanceCalculator.calculate(src,w);
                if(tempDistance<localminLeft){
                    winner = w;
                    localminLeft = tempDistance;
                }
            }

            for(int j = lo; j <= leftFarthest; j++){
                left.add(winner);
            }

            //right
            double localminRight = Double.MAX_VALUE;
            if(leftFarthest<hi){
                List<Segment> temp2 = new ArrayList<Segment>();
                for(int i = leftFarthest+1; i <= hi; i++){
                    temp2.add(segments.get(i));
                }
                List<Worker> localRightReachable = getAllVaildWorkers(temp2,dataCarrier);
                Location src2 = temp2.get(0).getSrc();

                Worker winner2 = null;

                for(Worker w : localRightReachable){
                    double tempDistance = DistanceCalculator.calculate(src2,w);
                    if(tempDistance<localminRight){
                        winner2 = w;
                        localminRight = tempDistance;
                    }
                }

                for(int j = leftFarthest+1; j <= hi; j++){
                    right.add(winner2);
                }
            }else {
                localminRight = 0;
                right = new ArrayList<Worker>();
            }


            for(Worker w : left){
                leftAdded.add(w);
            }
            List<Worker> tempRight = new ArrayList<Worker>();
            for(Worker w : right){
                tempRight.add(w);
            }
            for(Worker w : rightAdded){
                tempRight.add(w);
            }

            double t = minExtraMovingDistance.get(0);
            minExtraMovingDistance.clear();
            minExtraMovingDistance.add(t+localminLeft+localminRight);

            allocation.clear();
            for(Worker w : leftAdded){
                allocation.add(w);
            }
            for(Worker w : tempRight){
                allocation.add(w);
            }
            return;

        }
    }
}
