package observability.scanner;


import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

public class LoggerScanner extends AbstractProcessor<CtMethod<?>> {

    @Override
    public void process(CtMethod<?> ctMethod) {
        String methodName = ctMethod.getSimpleName();
        CtType<?> caller = ctMethod.getDeclaringType();

    }
}
