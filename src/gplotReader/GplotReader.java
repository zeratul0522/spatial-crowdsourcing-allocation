package gplotReader;

import config.Parameters;
import org.omg.Dynamic.Parameter;
import util.HashMapUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/5 下午8:38
 */
public class GplotReader {
    private HashMap<Integer,String> nodename;
    private int[][] matrix;
    private int separator;

    /**
     * 从txt格式的拓扑图中读取需要的数据
     * @param filename
     */
    public void readFromGplotFile(String filename){
        try{
            String filepath = "gplot/"+ filename + ".txt";
            File file = new File(filepath);
            if(file.isFile() && file.exists()){
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(inputStreamReader);
                String line = "";
                int flag1 = 0, flag2 = 0;
                int count = 0;
                nodename = new HashMap<Integer, String>();
                //locations and services
                //while的条件非常重要，如果&&前后互换的话会出错，第二条分割线=====后面的第一条数据会读不出来
                while(flag2 == 0 && (line = br.readLine()) != null ){
                    // System.out.println("line="+line);
                    if(line.equals("=====")){
                        if(flag1 == 0){
                            flag1 = 1;
                            separator = count;
                        }else{
                            flag2 = 1;
                        }
                        continue;
                    }
                    //TODO: 我删除了去数据库里面检查地名和服务名是否真实存在的代码部分，所以这方面代码的鲁棒性有所欠缺.

//                    if(!(DatabaseOperation.databaseQueryOperation("SELECT idlocation FROM test_crowdsourcingdb.location " +
//                            "where namelocation='"+line+"';")||
//                            DatabaseOperation.databaseQueryOperation("SELECT idservice FROM test_crowdsourcingdb.offline_service " +
//                                    "where nameservice='"+line+"';")) ){
//                        System.out.println("Location "+line+" was not included in gplot: "+ filename +".txt");
//                        System.exit(0);
//                    }
                    nodename.put(count++, line);
                }
                matrix = new int[nodename.size()][nodename.size()];
                for(int i = 0; i < nodename.size(); i++){
                    for(int j = 0; j < nodename.size();j++){
                        matrix[i][j] = -1;
                    }
                }
                //links
                while((line = br.readLine()) != null){
                    //System.out.println("line="+line);
                    String[] split = line.split("-");
                    String src = split[0];
                    String dest = split[1];
                    matrix[HashMapUtil.findIndexInHashmap(src,nodename).
                            get(0)][HashMapUtil.findIndexInHashmap(dest,nodename).get(0)] = 1;

                }
//                for(int i = 0; i < nodename.size(); i++){
//                    for(int j = 0; j < nodename.size();j++){
//                        System.out.print(matrix[i][j]);
//                    }
//                    System.out.println("");
//                }
                br.close();
            }else{
                System.out.println("文件不存在！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public HashMap<Integer, String> getNodename() {
        return nodename;
    }

    public void setNodename(HashMap<Integer, String> nodename) {
        this.nodename = nodename;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSeparator() {
        return separator;
    }

    public void setSeparator(int separator) {
        this.separator = separator;
    }

    public static void main(String[] args){

//        GplotReader gplotReader = new GplotReader();
//        gplotReader.readFromGplotFile("motivation");
//        HashMap<Integer,String> hm = gplotReader.getNodename();
//        int[][] matrix = new int[hm.size()][hm.size()];
//        matrix = gplotReader.getMatrix();
//        for(int i = 0; i < hm.size(); i++){
//            for(int j = 0; j < hm.size();j++){
//                System.out.println(matrix[i][j]);
//            }
//        }
    }
}
