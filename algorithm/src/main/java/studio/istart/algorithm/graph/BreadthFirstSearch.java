package studio.istart.algorithm.graph;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BFS 广度优先搜索
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class BreadthFirstSearch {
    /**
     * 是否能抵达
     */
    public void canArrival() {

    }

    /**
     * 最小距离(路径)，会有多个
     */
    public void minDistance() {

    }

    Set<GraphNode> checkedNode = new HashSet<>();
    Queue<GraphNode> searchQueue = new LinkedBlockingQueue<>();

    /**
     * 搜索第一广度
     * 不能用迭代?第一度，第二度
     */
    public boolean core(GraphNode start, GraphNode end) {
        searchQueue.addAll(start.getForwards());
        while (!searchQueue.isEmpty()) {
            GraphNode graphNode = searchQueue.poll();
            if (!checkedNode.contains(graphNode)) {
                if (graphNode.equals(end)) {
                    return true;
                }
                searchQueue.addAll(graphNode.getForwards());
                checkedNode.add(graphNode);
            }
        }
        return false;
    }
}
