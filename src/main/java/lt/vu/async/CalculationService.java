package lt.vu.async;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculationService {

    public String performLongCalculation2() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Success.");
        return "Success";
    }

}