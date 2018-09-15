package algorithm;

import util.HashMapUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Desc 利用邻接矩阵，求有向图中两点间的所有可达路径
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/5 下午8:42
 */
public class PathFinder {

    private int[][] graphMatrix;
    private HashMap<Integer, String> nodeName;
    private boolean[] hasFlag;//means if a node has been visited
    private ArrayList<String> result;
    private int src;
    private int dest;
    private String srcStr;
    private String destStr;


    public PathFinder(int[][] graphMatrix, HashMap<Integer, String> nodeName, String srcStr, String destStr) {
        this.graphMatrix = graphMatrix;
        this.nodeName = nodeName;
        hasFlag = new boolean[graphMatrix.length];
        result = new ArrayList<String>();
        this.srcStr = srcStr;
        this.destStr = destStr;

    }

    public void getPath(){

        if(HashMapUtil.findIndexInHashmap(srcStr,nodeName).size() == 0){
            System.out.println("PathFinder failed: source not found!");
            System.exit(0);
        }
        if(HashMapUtil.findIndexInHashmap(destStr,nodeName).size() == 0){
            System.out.println("PathFinder failed: destination not found!");
            System.exit(0);
        }
        src = HashMapUtil.findIndexInHashmap(srcStr,nodeName).get(0);
        dest = HashMapUtil.findIndexInHashmap(destStr,nodeName).get(0);
//        /System.out.println("getPath() called, src = "+src+" dest = "+dest);
        getPath(src,dest,""+nodeName.get(src));

    }

    public void getPath(int src, int dest, String path){
        hasFlag[src] = true;
        for(int i = 0; i < graphMatrix.length; i++){
            if(graphMatrix[src][i] == -1 || hasFlag[i]) continue; //如果路不通或者已经被访问过，则找下一个节点
            if(i == dest){ //如果找到了一条路径
                result.add(path+"->"+nodeName.get(dest));
                continue;
            }
            getPath(i, dest, path+"->"+nodeName.get(i));
            hasFlag[i]=false;

        }
    }



    public ArrayList<String> getResult() {
        return result;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

}