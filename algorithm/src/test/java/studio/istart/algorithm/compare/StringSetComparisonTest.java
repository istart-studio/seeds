package studio.istart.algorithm.compare;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 字符串集合比较
 *
 * @author DongYan
 * @since 1.8
 **/
@Slf4j(topic = "字符串集合比较")
class StringSetComparisonTest {

    @Test
    @DisplayName("是否存在不同的元素")
    void containsNonexistentElements() {
        String[] setA = {"apple", "banana", "grape", "mango"};
        String[] setB = {"apple", "banana", "orange"};
        StringSetComparison stringSetComparison = new StringSetComparison();
        Assertions.assertTrue(stringSetComparison.containsNonexistentElements(setA, setB));

        setA = new String[]{"apple", "banana", "grape", "mango"};
        setB = new String[]{"apple", "banana", "banana", "orange"};
        Assertions.assertTrue(stringSetComparison.containsNonexistentElements(setA, setB));
    }
}