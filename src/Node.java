public class Node implements Comparable<Node>{

    private double gVal;
    private double hVal;
    private double fVal;
    private Node prior;

    private Point pos;
    private Point end;

    public Node (Point start, Point end)
    {
        this.pos = start;
        this.end = end;
        this.gVal = 0;
        this.hVal = Math.sqrt(Math.pow((start.x - end.x), 2)
                + Math.pow((start.y - end.y), 2));
        this.fVal = gVal + hVal;
        this.prior = null;
    }
    public Node(Point pos, Point end, Node prior, Point start)
    {
        this.pos = pos;
        this.end = end;
        this.gVal = calculategVal(prior, pos, start);
        this.hVal = Math.sqrt(Math.pow((pos.x - end.x), 2)
                + Math.pow((pos.y - end.y), 2));
        this.fVal = gVal + hVal;
        this.prior = prior;
    }
    public int hashCode()
    {
        return (int)fVal;
    }

    public int compareTo(Node other)
    {
        return (int)(this.fVal - other.fVal);
    }

    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }
        if (this.getClass() != o.getClass())
        {
            return false;
        }
        Node n = (Node) o;
        return this.gVal == gVal && this.hVal == hVal &&
                this.fVal == fVal && this.prior.equals(prior);
    }

    public static double calculategVal(Node current, Point adj, Point start)
    {
        double curToStart = Math.sqrt(Math.pow((current.getPos().x - start.x), 2)
                + Math.pow((current.getPos().y - start.y), 2));
        double curToAdj = Math.sqrt(Math.pow((current.getPos().x - adj.x), 2)
                + Math.pow((current.getPos().y - adj.y), 2));

        return curToStart + curToAdj;
    }
    public double getgVal() {
        return gVal;
    }
    public double gethVal() {
        return hVal;
    }

    public Point getPos() {
        return pos;
    }

    public Point getEnd() {
        return end;
    }

    public double getfVal() {
        return fVal;
    }

    public Node getPrior() {
        return prior;
    }

    public void setgVal(double gVal) {
        this.gVal = gVal;
        this.fVal = this.gVal + this.hVal;
    }


}
