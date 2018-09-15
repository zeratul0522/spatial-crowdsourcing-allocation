package util;

import java.util.List;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/15 下午10:19
 */
public class AllocationWithMinExtraMD {
    private List<Worker> allocation;
    private double minExtraMovingDistance;

    public AllocationWithMinExtraMD(List<Worker> allocation, double minExtraMovingDistance) {
        this.allocation = allocation;
        this.minExtraMovingDistance = minExtraMovingDistance;
    }

    public List<Worker> getAllocation() {
        return allocation;
    }

    public void setAllocation(List<Worker> allocation) {
        this.allocation = allocation;
    }

    public double getMinExtraMovingDistance() {
        return minExtraMovingDistance;
    }

    public void setMinExtraMovingDistance(double minExtraMovingDistance) {
        this.minExtraMovingDistance = minExtraMovingDistance;
    }
}
