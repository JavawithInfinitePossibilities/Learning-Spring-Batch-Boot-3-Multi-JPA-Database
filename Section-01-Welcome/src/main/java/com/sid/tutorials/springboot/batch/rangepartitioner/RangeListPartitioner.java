package com.sid.tutorials.springboot.batch.rangepartitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kunmu On 15-08-2024
 */
@Component
public class RangeListPartitioner implements Partitioner {
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long min = 0, max = 1000, targetsize = (max - min) / gridSize + 1, counter = 0;
        long start = min, end = start + targetsize - 1;
        Map<String, ExecutionContext> result = new HashMap<>();
        while (start <= max) {
            if (end >= max) {
                end = max;
            }
            ExecutionContext executionContext = new ExecutionContext();
            executionContext.putLong("partition_number", counter);
            executionContext.putLong("minValue", start);
            executionContext.putLong("maxValue", end);
            executionContext.putLong("targetsize", targetsize);
            result.put("partition-" + counter, executionContext);
            start += targetsize;
            end += targetsize;
            counter++;
        }
        return result;
    }
}
