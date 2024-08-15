package com.sid.tutorials.springboot.batch.job.process;

import com.sid.tutorials.springboot.batch.bean.Car;
import com.sid.tutorials.springboot.batch.entity.CarEntity;
import com.sid.tutorials.springboot.batch.mockdata.MockDataPrep;
import com.sid.tutorials.springboot.batch.repository.CarRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * @author kunmu On 15-08-2024
 */
@Configuration
public class CarStepBuilderConfig {

    @Autowired
    private MockDataPrep mockDataPrep;

    @Autowired
    private CarRepository carRepository;

    @Bean
    public ItemReader<Car> carItemReader() throws IOException {
        List<Car> cars = mockDataPrep.getCars();
        ItemReader<Car> itemReader = () -> {
            if (cars.size() > 0) {
                return cars.remove(0);
            }
            return null;
        };
        return itemReader;
    }

    @Bean
    public ItemProcessor<Car, CarEntity> carItemProcessor() {
        ItemProcessor<Car, CarEntity> itemProcessor = (Car car) -> {
            CarEntity carEntity = new CarEntity();
            BeanUtils.copyProperties(car, carEntity);
            return carEntity;
        };
        return itemProcessor;
    }

    @Bean
    public ItemWriter<CarEntity> carEntityItemWriter() {
        ItemWriter<CarEntity> itemWriter = (Chunk<? extends CarEntity> chunk) -> {
            chunk.getItems().stream().forEach((CarEntity carEntity) -> {
                System.out.println(carEntity);
                carRepository.saveAndFlush(carEntity);
            });
        };
        return itemWriter;
    }
}
