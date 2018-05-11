package studio.istart.tracker.entity;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * todo:深拷贝，一次写入
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@Data
public class MonitorUnit {
    private String instanceId;
    private String classMethod;
    private long startTime;
    private long endTime;
    private Set<String> subInstanceIds;

    @Override
    public String toString() {
        String subInstanceIdsString = subInstanceIds.stream().map(n -> n.toString())
            .collect(Collectors.joining(","));
        return "MonitorUnit{" +
            "instanceId='" + instanceId + '\'' +
            ", classMethod='" + classMethod + '\'' +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", subInstanceIds=" + subInstanceIdsString +
            '}';
    }
}
