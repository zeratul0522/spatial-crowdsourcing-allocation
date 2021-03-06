package algorithm;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/16 下午1:44
 */
public class AlgorithmInit {
    public static void main(String[] args) {
        DataCarrier dataCarrier = new DataCarrier();

        long start = System.currentTimeMillis();
        Algorithm1 algorithm1 = new Algorithm1();
        algorithm1.init(dataCarrier);
        long end = System.currentTimeMillis();
        long duration = end-start;
        System.out.println("算法1总计耗时："+duration+"ms");

        long start3 = System.currentTimeMillis();
        Algorithm1TwoEnded a = new Algorithm1TwoEnded();
        a.init(dataCarrier);
        long end3 = System.currentTimeMillis();
        long duration3 = end3 - start3;
        System.out.println("算法1(双端法)总计耗时："+duration+"ms");

        long start2 = System.currentTimeMillis();
        Algorithm2 algorithm2 = new Algorithm2();
        algorithm2.init(dataCarrier);
        long end2 = System.currentTimeMillis();
        long duration2 = end2-start2;
        System.out.println("算法2总计耗时："+duration2+"ms");



    }
}
