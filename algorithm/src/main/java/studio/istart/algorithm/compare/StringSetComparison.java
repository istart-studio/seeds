package studio.istart.algorithm.compare;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.UnaryOperator;

/**
 * 字符串集合比较
 * 排序+双指针
 * <p>
 * 适用场景：
 * 两数之和（Two Sum）：在给定数组中找到两个数，使得它们的和等于目标值。
 * 三数之和（3Sum）：在给定数组中找到所有不重复的三个数，使得它们的和等于零。
 * 反转字符串（Reverse String）：反转给定字符串。
 * 有效的字母异位词（Valid Anagram）：判断两个字符串是否是字母异位词。
 * 合并区间（Merge Intervals）：合并重叠的区间。
 * 盛最多水的容器（Container With Most Water）：在给定的非负整数数组中，找到两个柱子，使得它们与x轴围成的容器的容量最大。
 * 三数最近和（3Sum Closest）：在给定的数组中找到三个数，使它们的和最接近目标值。
 * 四数之和（4Sum）：在给定的数组中找到所有不重复的四个数，使得它们的和等于目标值。
 * 反转链表（Reverse Linked List）：反转给定链表。
 * 有效的回文字符串（Valid Palindrome）：判断给定的字符串是否是有效的回文字符串。
 *
 * @author DongYan
 * @since 1.8
 **/
@Slf4j(topic = "字符串集合比较")
public class StringSetComparison {


    /**
     * 排序 算法
     */
    public final UnaryOperator<String[]> sorter;

    public StringSetComparison() {
        this.sorter = strings -> {
            Arrays.sort(strings);
            return strings;
        };
    }

    public StringSetComparison(@NonNull UnaryOperator<String[]> sorter) {
        this.sorter = sorter;
    }

    /**
     * 是否存在不同的元素
     *
     * @param originalSetA 集合A
     * @param originalSetB 集合B
     * @return T 是，存在不同的元素
     */
    public boolean containsNonexistentElements(String[] originalSetA, String[] originalSetB) {
        // 对集合A和集合B进行排序,字典顺序
        String[] setA = sorter.apply(originalSetA.clone());
        String[] setB = sorter.apply(originalSetB.clone());

        // 初始化指针A和指针B
        int pointerA = 0;
        int pointerB = 0;

        // 比较操作
        while (pointerA < setA.length && pointerB < setB.length) {
            int comparison = setA[pointerA].compareTo(setB[pointerB]);
            if (comparison == 0) {
                // 相等，移动两个指针
                pointerA++;
                pointerB++;
            } else if (comparison < 0) {
                // setA[pointerA] < setB[pointerB]
                // setA中的当前字符串不在setB中存在
                log.debug("元素不在B集合中:{}", setA[pointerA]);
                return true;
            } else {
                // setA[pointerA] > setB[pointerB]
                // 移动指针B，继续比较
                pointerB++;
            }
        }

        // 检查是否遍历完集合B中的所有元素
        return pointerB == setB.length;
    }
}
