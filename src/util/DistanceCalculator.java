package util;

import config.OptimalGoal;
import config.Parameters;

import java.util.Random;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/14 下午4:26
 */
public class DistanceCalculator {
    public static double calculate(Location a, Location b){
        double result1 = Math.sqrt(Math.pow(a.getXordinate()-b.getXordinate(),2)+Math.pow(a.getYordinate()-b.getYordinate(),2));
        if(Parameters.goal==OptimalGoal.MIN_EXTRA_MOVING_DISTANCE){
            return result1;
        }else{
            return result1/1.3;
        }

    }

    public static double calculate(Location l, Worker w){
        double result = Math.sqrt(Math.pow(l.getXordinate()-w.getXordinate(),2)+Math.pow(l.getYordinate()-w.getYordinate(),2));
        if(Parameters.goal==OptimalGoal.MIN_EXTRA_MOVING_DISTANCE){
            return result;
        }else{
            return result/(1+Math.random()*0.5);
        }
    }
}
