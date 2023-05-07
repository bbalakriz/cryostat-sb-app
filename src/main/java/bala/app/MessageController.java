package bala.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class MessageController {

    @RequestMapping("/no-issues")
    public Message hello() {
        return new Message("no-issues");
    }

    @RequestMapping("/cpu-intensive")
    public Message triggerCpuIntensive() {
        cpuIntensive();
        return new Message("cpu-intensive task executed");
    }

    @RequestMapping("/gc-intensive")
    public Message triggerGcIntensive() {
        gcIntensive();
        return new Message("gc-intensive task executed");
    }

    @RequestMapping("/lock-contention")
    public Message triggerLockContention() {
        lockContention();
        return new Message("lock-contention executed");
    }

    public Object value;

    private void gcIntensive() {
        for (int i = 0; i < 1000; i++) {
            value = new Object[1024 * 1024];
        }
    }

    public long number;

    private void cpuIntensive() {
        for (long l = 0; l < 1000_000_000; l++) {
            number += l;
        }
    }

    public Object lock = new Object();

    private void lockContention() {
        synchronized (lock) {
            try {
                lock.wait(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
