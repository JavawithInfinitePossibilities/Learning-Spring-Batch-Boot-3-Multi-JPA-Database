package com.sid.tutorials.springboot.batch.job;

import com.sid.tutorials.springboot.batch.bean.Car;
import com.sid.tutorials.springboot.batch.entity.CarEntity;
import com.sid.tutorials.springboot.batch.rangepartitioner.RangeListPartitioner;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author kunmu On 15-08-2024
 */
@Configuration
public class CarStepConfig {

    @Autowired
    private ItemReader<Car> itemReader;
    @Autowired
    private ItemProcessor<Car, CarEntity> itemProcessor;
    @Autowired
    private ItemWriter<CarEntity> itemWriter;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TaskExecutor partitionTaskExecutor;

    @Autowired
    private RangeListPartitioner rangeListPartitioner;

    @Autowired
    @Qualifier("platformTransectionManager")
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
        taskExecutorPartitionHandler.setGridSize(10);
        taskExecutorPartitionHandler.setTaskExecutor(partitionTaskExecutor);
        taskExecutorPartitionHandler.setStep(carStep());
        return taskExecutorPartitionHandler;
    }

    @Bean
    public Step masterStep() {
        return new StepBuilder("Master-Step", jobRepository)
                .partitioner(carStep().getName(), rangeListPartitioner)
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public Step carStep() {
        StepBuilder stepBuilder = new StepBuilder("Car-Step", jobRepository);
        TaskletStep step = stepBuilder.<Car, CarEntity>chunk(10, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .faultTolerant()
                .skipPolicy((Throwable t, long skipCount) -> {
                    return true;
                })
                .build();
        return step;
    }

    @Bean
    public Step finalStep() {
        StepBuilder builder = new StepBuilder("Final-Step", jobRepository);
        TaskletStep step = builder.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
            System.out.println("This is the Last Step!!!!");
            return RepeatStatus.FINISHED;
        }, platformTransactionManager).build();
        return step;
    }
}
