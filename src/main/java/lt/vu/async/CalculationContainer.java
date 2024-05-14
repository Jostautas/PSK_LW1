package lt.vu.async;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CalculationContainer {
    @Inject
    CalculationService calculationService;
    private CompletableFuture<String> result = null;

    public void startCalculation() {
        System.out.println("called startCalculation");
        result = CompletableFuture.supplyAsync(() -> calculationService.performLongCalculation2());
        System.out.println("supplyAsync made.");
    }

    public boolean getIsCalculationDone() throws Exception{
        System.out.println("called getIsCalculationDone");
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
