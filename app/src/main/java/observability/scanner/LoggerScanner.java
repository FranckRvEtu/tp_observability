package observability.scanner;


import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

public class LoggerScanner extends AbstractProcessor<CtMethod<?>> {

    @Override
    public void process(CtMethod<?> ctMethod) {
        String methodName = ctMethod.getSimpleName();

    }
}
