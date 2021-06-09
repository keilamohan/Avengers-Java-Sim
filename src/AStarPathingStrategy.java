import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Comparator;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        /*open -> hashmap for quick lookups (key could be point or something) and val could be g or maybe
        a bundled up list of node info and then grabbing the best
        f-value u should use a priorityqueue*/

        List<Point> path = new LinkedList<>();
        List<Point> closedList = new LinkedList<>();
        HashMap<Point, Node> openListMap = new HashMap<>();
        Comparator<Node> comp = (n1, n2) -> (int)(n1.getfVal() - n2.getfVal());
        PriorityQueue<Node> openListPQ = new PriorityQueue<>(comp.
                thenComparing(Node :: gethVal).thenComparing(Node :: getgVal));

        Node current = new Node(start, end);
        openListPQ.add(current);
        openListMap.put(current.getPos(), current);

        double g = 0;

        while (current != null && !withinReach.test(current.getPos(), current.getEnd()))
        {

            List<Point> neighbors = potentialNeighbors.apply(current.getPos())
                    .filter(canPassThrough)
                    .filter(pt -> !closedList.contains(pt))
                    .collect(Collectors.toList());

            for (Point p : neighbors)
            {
                Node adj = new Node(p, end, current, start);
                if (!openListMap.containsKey(p)) {
                    openListMap.put(p, adj);
                    openListPQ.add(adj);
                }

                double gNew = Node.calculategVal(current, adj.getPos(), start);
                if (gNew > g)
                {
                    g = gNew;
                    adj.setgVal(gNew);
                    current = adj.getPrior();
                }

            }

            for (Node n : openListPQ) {
                if (n.getgVal() < g)
                    n.setgVal(g);
            }
            closedList.add(current.getPos());
            current = openListPQ.poll();

        }
        while (current != null && current.getPos() != start)
        {
            path.add(0, current.getPos());
            current = current.getPrior();


        }

        return path;
    }
}

