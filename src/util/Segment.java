package util;

/**
 * @Desc
 * @Author Fan Zejun E-mail:fzj0522@outlook.com
 * @Version Created at: 2018/9/15 下午9:29
 */
public class Segment {
    private Location src;
    private Location dest;

    public Segment(Location src, Location dest) {
        this.src = src;
        this.dest = dest;
    }

    public Location getSrc() {
        return src;
    }

    public void setSrc(Location src) {
        this.src = src;
    }

    public Location getDest() {
        return dest;
    }

    public void setDest(Location dest) {
        this.dest = dest;
    }
}
