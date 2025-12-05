package observability.scanner;


import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

public class LoggerScanner extends AbstractProcessor<CtMethod<?>> {
    @Override
    public boolean isToBeProcessed(CtMethod<?> candidate) {
        CtClass<?> parent = candidate.getParent(CtClass.class);
        return (parent != null && parent.getSimpleName().equals("ProductService") && candidate.isPublic());
    }

    @Override
    public void process(CtMethod<?> ctMethod) {
        String methodName = ctMethod.getSimpleName().toLowerCase();
        String actionType = deduceAction(methodName);

        if (actionType == null) return;

        String logCode = String.format(
                "LoggerFactory.getLogger(\"profile.logger\").info(" +
                        "\"{\\\"timestamp\\\": \\\"\" + java.time.Instant.now() + \"\\\", " +
                        "\\\"user\\\": \\\"\" +UserContext.getCurrentUser() + \"\\\", " +
                        "\\\"action\\\": \\\"%s\\\", " +
                        "\\\"method\\\": \\\"%s\\\"}\");",
                actionType,
                ctMethod.getSimpleName()
        );
        CtCodeSnippetStatement snippet = getFactory().Code().createCodeSnippetStatement(logCode);
        ctMethod.getBody().insertBegin(snippet);

        System.out.println("✅ Log injecté dans : " + ctMethod.getSimpleName() + " [" + actionType + "]");
    }

    private String deduceAction(String name){
        if (name.contains("expensive")) {
            return "EXPENSIVE";
        }
        if (name.contains("find")){
            return "READ";
        }
        if (name.contains("insert") ||  name.contains("update") ||  name.contains("delete")) {
            return "WRITE";
        }
        return null;
    }
}
