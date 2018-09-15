package algorithm;

import config.Parameters;
import util.Location;
import util.Service;
import util.Worker;

import java.util.*;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/14 下午4:02
 */
public class DataCarrier {
    //FIXME:目前暂时不考虑Location和Service的区别，把他们当成一样的类型处理
    private List<Location> locations = new ArrayList<Location>();
    private List<Worker> workers = new ArrayList<Worker>();
    public HashMap<String,Long> queryTableByName = new HashMap<String, Long>();//反查location的ID
    public HashMap<Long,Location> queryTableById = new HashMap<Long, Location>();//通过Id查询Location的细节

    public List<Worker> getWorkers() {
        return workers;
    }

    /*
        1开头代表地点，2开头代表服务
         */
    public DataCarrier(){
        Location a = new Location("A",11,480,974);
        locations.add(a);
        a = new Location("B",12,254,785);
        locations.add(a);
        a = new Location("C",13,749,720);
        locations.add(a);
        a = new Location("D",14,207,579);
        locations.add(a);
        a = new Location("E",15,808,509);
        locations.add(a);
        a = new Location("F",16,153,479);
        locations.add(a);
        a = new Location("G",17,578,378);
        locations.add(a);
        a = new Location("H",18,218,388);
        locations.add(a);
        a = new Location("I",19,104,274);
        locations.add(a);
        a = new Location("J",110,838,292);
        locations.add(a);
        a = new Location("K",111,137,153);
        locations.add(a);
        a = new Location("L",112,804,199);
        locations.add(a);
        a = new Location("M",113,665,17);
        locations.add(a);

        a = new Location("Service1",21,748,721);
        locations.add(a);
        a = new Location("Service2",22,809,508);
        locations.add(a);
        a = new Location("Service3",23,839,291);
        locations.add(a);

        //构建两个查询表
        for(Location l : locations){
            long id = l.getId();
            String name = l.getName();
            queryTableByName.put(name,id);
            queryTableById.put(id,l);
        }

        //随机生成参与者
        Random r = new Random();
        for(long i = 0; i < Parameters.worker_amount; i++){
            double xordinate = r.nextDouble()*1000;
            double yordinate = r.nextDouble()*1000;
            List<Location> list = new ArrayList<Location>();
            for(Location l : locations){
                if(r.nextDouble()<=Parameters.location_worker_valid_threshold){
                    list.add(l);
                }
            }
            //System.out.println(xordinate+" "+yordinate+" "+list.size());

            Worker worker = new Worker(i,"name",xordinate,yordinate,list);
            workers.add(worker);
        }

    }


}
