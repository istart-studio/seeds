package studio.istart.activiti.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
public class AutomatedDataDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Date now = new Date();
        execution.setVariable("autoWelcomeTime", now);
        System.out.println("Faux call to backend for ["
            + execution.getVariable("fullName") + "]");
    }
}
