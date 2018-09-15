package algorithm;

import util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 基于动态规划
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/15 下午7:50
 */
public class Algorithm2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Algorithm2 algorithm2 = new Algorithm2();
        algorithm2.init();
        long end = System.currentTimeMillis();
        long duration = end-start;
        System.out.println("算法总计耗时："+duration+"ms");
    }
    public void init(){
        DataCarrier dataCarrier = new DataCarrier();
        List<List<Segment>> segmentCollections = preprocess(dataCarrier);
        allocate(segmentCollections,dataCarrier);

    }

    public void allocate(List<List<Segment>> segmentCollections, DataCarrier dataCarrier){
        for(List<Segment> segments : segmentCollections){
            boolean flag = workOnParticularSegments(segments,dataCarrier);
            if(flag){
                return;
            }
        }
        System.out.println("分配失败==============");
        return;

    }

    public boolean workOnParticularSegments(List<Segment> segments, DataCarrier dataCarrier){
        int segment_number = segments.size();
        if(segment_number==0){
            return false;
        }


        //dp[n]表示前n段segment的最优解
        AllocationWithMinExtraMD[] dp = new AllocationWithMinExtraMD[segment_number+1];
        dp[0] = null;

        //计算dp[1]
        List<Segment> temp = new ArrayList<Segment>();
        temp.add(segments.get(0));
        Location src = segments.get(0).getSrc();
        List<Worker> workersForSegment1 = getAllVaildWorkers(temp,dataCarrier);
        if(workersForSegment1.size()==0){
            return false;
        }
        double currMin = Double.MAX_VALUE;
        Worker curr = null;
        for(Worker w : workersForSegment1){
            double distance = DistanceCalculator.calculate(src,w);
            if(distance<currMin){
                currMin = distance;
                curr = w;
            }
        }
        List<Worker> tmp = new ArrayList<Worker>();
        tmp.add(curr);
        dp[1] = new AllocationWithMinExtraMD(tmp,currMin);


        //动态规划计算dp[i]
        for(int i = 2; i <= segment_number; i++){
            double minExtraMD = Double.MAX_VALUE;
            List<Worker> winner = new ArrayList<Worker>();
            for(int j = 1; j < i; j++){
                List<Segment> missingParts = new ArrayList<Segment>();
                for(int k = j+1; k <= i; k++){
                    missingParts.add(segments.get(k-1));
                }

                //System.out.println("此时计算的是前"+i+"段的最佳方案 "+"j="+j+" dp[j]的长度为"+dp[j].getAllocation().size()+" 缺失部分的长度为"+missingParts.size());

                List<Worker> workers4MissingParts = getAllVaildWorkers(missingParts,dataCarrier);
                double temp_extraMD = 0;
                List<Worker> temp_allocation = new ArrayList<Worker>();

                //如果没有人能够单独补全缺失的部分
                if(workers4MissingParts.size()==0){
                    temp_extraMD = Double.MAX_VALUE;
                }else {
                    //如果有

                    //看这个worker是否为前面的分配方案的延续
                    List<Worker> prevAllocation = dp[j].getAllocation();
                    Worker prevWorker = prevAllocation.get(prevAllocation.size()-1);

                    if(workers4MissingParts.contains(prevWorker)){
                        //是前面分配方案的延续
                        temp_extraMD = dp[j].getMinExtraMovingDistance();
                        temp_allocation.clear();
                        for(Worker w : dp[j].getAllocation()){
                            temp_allocation.add(w);
                        }
                        for(int h = 0; h < missingParts.size(); h++){
                            temp_allocation.add(prevWorker);
                        }

                    }else {
                        //不是前面分配方案的延续
                        Worker w = findClosest(workers4MissingParts,missingParts.get(0).getSrc());
                        temp_extraMD = dp[j].getMinExtraMovingDistance()+findClosestDistance(workers4MissingParts,missingParts.get(0).getSrc());
                        temp_allocation.clear();
                        for(Worker ww : dp[j].getAllocation()){
                            temp_allocation.add(ww);
                        }
                        for(int h = 0; h < missingParts.size(); h++){
                            temp_allocation.add(w);
                        }

                    }

                }

                //System.out.println("临时分配方案长度="+temp_allocation.size());
//                for(Worker w : temp_allocation){
//                    System.out.print(w.getId()+" ");
//                }
//                System.out.println();
                if(temp_extraMD<minExtraMD){
                    winner = temp_allocation;
                    minExtraMD = temp_extraMD;
                }

            }


            if(minExtraMD==Double.MAX_VALUE){
                return false;
            }


            dp[i] = new AllocationWithMinExtraMD(winner,minExtraMD);
            //System.out.println("前"+i+"段的分配结果的长度="+winner.size());

        }

        //动态规划计算完成
        System.out.println("得到结果=====");
        List<Worker> list = dp[segment_number].getAllocation();
        System.out.println("参与者分配结果的长度="+list.size());
        for(Worker w : list){
            System.out.print(w.getId()+" ");
        }
        System.out.println();
        System.out.println(dp[segment_number].getMinExtraMovingDistance());
        return true;

    }

    /*
    给定连续的几段segment，返回所有能单独完成的worker
     */
    public List<Worker> getAllVaildWorkers(List<Segment> segments, DataCarrier dataCarrier){
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
    public List<Worker> getAllValidWorkers4Location(Location l, DataCarrier dataCarrier){
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


    public List<List<Segment>> preprocess(DataCarrier dataCarrier){
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
