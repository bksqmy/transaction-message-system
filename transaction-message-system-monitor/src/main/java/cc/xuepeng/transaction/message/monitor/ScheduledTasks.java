package cc.xuepeng.transaction.message.monitor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 5000)
    public void test1() {
        System.out.println("test1: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 2000)
    public void test2() {
        System.out.println("test2: " + LocalDateTime.now());
    }

}
