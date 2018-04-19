package studio.istart.algorithm.tree;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class BasicTree {

    public static int treeSize(TreeNode root) {
        if (root.getChildren() == null || root.getChildren().size() == 0) {
            return 0;
        } else {
            int childrenSize = 0;
            for (TreeNode child : root.getChildren()) {
                childrenSize += treeSize(child);
            }
            return 1 + childrenSize;
        }
    }

    public static int treeHeight(TreeNode root) {
        if (root.getChildren() == null || root.getChildren().size() == 0) {
            return 0;
        } else {
            int childrenHeight = 0;
            for (TreeNode child : root.getChildren()) {
                int childHeight = treeHeight(child);
                if (childrenHeight < childHeight) {
                    childrenHeight = childHeight;
                }
            }
            return 1 + childrenHeight;
        }
    }
}
