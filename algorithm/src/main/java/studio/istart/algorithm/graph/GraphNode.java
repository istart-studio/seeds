package studio.istart.algorithm.graph;

import java.util.Set;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class GraphNode {
    private String key;
    private Set<GraphNode> forwards;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Set<GraphNode> getForwards() {
        return forwards;
    }

    public void setForwards(Set<GraphNode> forwards) {
        this.forwards = forwards;
    }

    public void addForwrd(GraphNode forward) {
        this.forwards.add(forward);
    }

    public GraphNode(String key, Set<GraphNode> forwards) {
        this.key = key;
        this.forwards = forwards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphNode)) {
            return false;
        }

        GraphNode graphNode = (GraphNode) o;

        return key.equals(graphNode.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
