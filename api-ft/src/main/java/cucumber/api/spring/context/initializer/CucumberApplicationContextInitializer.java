package cucumber.api.spring.context.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import cucumber.api.spring.context.SpringApplicationContextBridge;

public class CucumberApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        applicationContext.setParent(SpringApplicationContextBridge.getApplicationContext());
    }

}
