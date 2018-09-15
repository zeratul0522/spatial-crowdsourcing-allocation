package algorithm;

import config.Parameters;
import gplotReader.GplotReader;
import util.DistanceCalculator;
import util.Location;
import util.TaskPhase;

import java.util.*;

/**
 * @Desc 对分段的合法路径进行组合
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/5 下午7:48
 */
public class ExecutivePathsFinder {

    public static List<List<String>> execute() {
        List<List<String>> legalPaths = new ArrayList<List<String>>();
        /*
        读图，找路径
         */

        //The task phases are specified by requester.
        ArrayList<TaskPhase> taskPhaseArrayList = new ArrayList<TaskPhase>();
        int len_src = Parameters.src_array.length;
        int len_dest = Parameters.dest_array.length;
        if(len_src<1 || len_dest<1 || len_src!=len_dest){
            System.out.println("Illegal Parameters: Src and Dest!");
            System.exit(0);
        }
        for(int i = 0; i < len_src; i++){
            taskPhaseArrayList.add(new TaskPhase(Parameters.src_array[i],Parameters.dest_array[i]));
        }

        ArrayList<String> phases = calculatePathCollections(Parameters.filename,taskPhaseArrayList);

//        for(String str : phases){
//            System.out.println(str);
//        }

        /*
        把所有合法路径存入legalPaths,并返回
         */

        for(String singlepath : phases){
            List<String> temp = new ArrayList<String>();
            String[] strs = singlepath.split("->");
            for(String str : strs){
                temp.add(str);
            }
            legalPaths.add(temp);
//            double tempTotalDistance = 0;
//            if(temp.size()<2){
//                tempTotalDistance = 0;
//            }else {
//                for(int i = 0; i < temp.size()-1;i++){
//                    long aid = dataCarrier.queryTableByName.get(temp.get(i));
//                    long bid = dataCarrier.queryTableByName.get(temp.get(i+1));
//
//                    Location a = dataCarrier.queryTableById.get(aid);
//                    Location b = dataCarrier.queryTableById.get(bid);
//
//                    double distance = DistanceCalculator.calculate(a,b);
//                    tempTotalDistance += distance;
//                }
//            }
        }




        return legalPaths;

    }

    public static ArrayList<String> calculatePathCollections(String filename, ArrayList<TaskPhase> taskPhaseArrayList){

        ArrayList<String> taskpaths = new ArrayList<String>();
        int phaseNum = taskPhaseArrayList.size();
        HashMap<Integer,ArrayList<String>> phaseMap = new HashMap<Integer,ArrayList<String >>();
        String lastDest = "INITIAL";
        int cnt = 0;
        for(TaskPhase tp : taskPhaseArrayList){
            if(lastDest.equals("INITIAL")) {

                phaseMap.put(cnt,ExecutivePathsFinder.convertToTaskPath(filename,tp.getSrc(),tp.getDest()));
                cnt++;
                lastDest = tp.getDest();
            }else{
                if(tp.getSrc().equals("")){
                    ArrayList<String> temppaths = ExecutivePathsFinder.convertToTaskPath(filename,lastDest,tp.getDest());
                    phaseMap.put(cnt,temppaths);
                    cnt++;


                }else{
                    ArrayList<String> temppaths1 = ExecutivePathsFinder.convertToTaskPath(filename,lastDest,tp.getSrc());
                    phaseMap.put(cnt,temppaths1);
                    cnt++;

                    ArrayList<String> temppaths2 = ExecutivePathsFinder.convertToTaskPath(filename,tp.getSrc(),tp.getDest());
                    phaseMap.put(cnt,temppaths2);
                    cnt++;

                    phaseNum++;

                }
                lastDest = tp.getDest();

            }

        }
        taskpaths = conbination(phaseMap);
        for(int i = 0; i < taskpaths.size(); i++){
            taskpaths.set(i,taskpaths.get(i).substring(0,taskpaths.get(i).length()-2));
        }
        return taskpaths;
    }

    public static ArrayList<String> convertToTaskPath(String gplotFileName, String src, String dest){
        ArrayList<String> result = new ArrayList<String>();
        GplotReader gplotReader = new GplotReader();
        gplotReader.readFromGplotFile(gplotFileName);
        HashMap<Integer, String> hm = gplotReader.getNodename();
        //HashMapUtil.printMapValue(hm);
        int[][] matrix = new int[hm.size()][hm.size()];
        matrix = gplotReader.getMatrix();

        // System.out.println(matrix[0][1]);
        PathFinder pf = new PathFinder(matrix, hm, src, dest);
        pf.getPath();
        //System.out.println("executed");
        for (String s : pf.getResult()) {
            result.add(s);
        }
        return result;
    }

    public static ArrayList<String> conbination(HashMap<Integer,ArrayList<String>> inputMap){
        ArrayList<String> result = new ArrayList<String>();
        String current = "";
        conbination(inputMap, 0, current, result);
        return result;
    }

    public static void conbination(HashMap<Integer,ArrayList<String>> inputMap, int index, String current, ArrayList<String> result){
        if(index == inputMap.size()){
            result.add(current);
            return;
        }else{
            ArrayList<String> list = inputMap.get(index);
            for(String s : list){
                String currentCopy = current;
                if(index > 0){
                    currentCopy+=(cutPath(s)+"->");
                }else{
                    currentCopy+=(s+"->");
                }

                conbination(inputMap, index+1, currentCopy, result);
            }
        }

    }

    /**
     * Cut the start of the path
     * For example, change Hallway B->Administration Building->Material Registration
     * into Administration Building->Material Registration
     * @param input
     * @return
     */
    public static String cutPath(String input){
        String[] strings = input.split("->");
        String result = "";
        for(int i = 1; i < strings.length-1;i++){
            result += strings[i] + "->";
        }
        result += strings[strings.length-1];
        return result;
    }

    public static void modifyArray(ArrayList<String> inputStr,int a,int b){
        int len = inputStr.size();
        String temp = inputStr.get(b);
        inputStr.set(b,inputStr.get(a));
        inputStr.set(a,temp);
    }


}
