package studio.istart.algorithm.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree Node
 *
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Data
public class TreeNode {
    public static final int NUM_CHILDREN = 2;
    private String key;
    private List<TreeNode> children = new ArrayList<>(NUM_CHILDREN);
}
