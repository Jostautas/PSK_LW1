package lt.vu.async;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.concurrent.Future;
import javax.inject.Named;

@Named
@RequestScoped
public class CalculationContainer {
    @EJB
    private CalculationService calculationService;
    private Future<String> result;

    public void startCalculation() {
        System.out.println("Long task started.");
        result = calculationService.performLongCalculation();
    }

    public boolean getIsCalculationDone() throws Exception{
        System.out.println("getIsCalculationDone");
        if(result != null) {
            System.out.println("result: " + result.get());
        }
        return result != null && result.isDone();
    }

    public String getTaskResult() throws Exception {
        System.out.println("getTaskResult.");
        return result.get();
    }

}
