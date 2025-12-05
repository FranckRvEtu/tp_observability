package observability;

import observability.scanner.LoggerScanner;
import spoon.Launcher;

public class SpoonLauncher {

    public static void main(String[] args) {
        System.err.println("Démarrage de Spoon");

        Launcher spoon = new Launcher();

        spoon.addInputResource("src/main/java");

        spoon.setSourceOutputDirectory("target/generated-sources/spoon");

        spoon.addProcessor(new LoggerScanner());

        spoon.getEnvironment().setAutoImports(true);
        spoon.getEnvironment().setCommentEnabled(true); // Garde les commentaires
        spoon.run();
    }
}
