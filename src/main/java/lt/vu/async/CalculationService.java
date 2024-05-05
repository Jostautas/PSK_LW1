package lt.vu.async;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;

@Stateless
public class CalculationService {

    @Asynchronous
    public Future<String> performLongCalculation() {
        try {
            Thread.sleep(1000);
            System.out.println("Long task completed.");
            return new AsyncResult<>("Success");
        } catch (InterruptedException e) {
            System.out.println("Long task interrupted.");
            Thread.currentThread().interrupt();
            return new AsyncResult<>("InterruptedException");
        }
    }
}