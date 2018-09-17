package config;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/5 下午10:07
 */
public class Parameters {
    public static final String filename = "test1";
    public static final OptimalGoal goal = OptimalGoal.MIN_EXTRA_MOVING_DISTANCE;
    public static final String[] src_array ={"A","C","E"};
    public static final String[] dest_array ={"Service1","Service2","L"};


    public static final long worker_amount = 1000;
    public static final double location_worker_valid_threshold = 0.5;//随机生成参与者的合法地点集合的时候用到的阈值
}

