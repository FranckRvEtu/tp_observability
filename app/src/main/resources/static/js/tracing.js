/* src/main/resources/static/js/tracing.js */

// On importe directement les versions spécifiques pour éviter les conflits
import { WebTracerProvider } from 'https://esm.sh/@opentelemetry/sdk-trace-web@1.18.1';
import { SimpleSpanProcessor, ConsoleSpanExporter } from 'https://esm.sh/@opentelemetry/sdk-trace-base@1.18.1';
import { OTLPTraceExporter } from 'https://esm.sh/@opentelemetry/exporter-trace-otlp-http@0.45.1';
import { Resource } from 'https://esm.sh/@opentelemetry/resources@1.18.1';
import { registerInstrumentations } from 'https://esm.sh/@opentelemetry/instrumentation@0.45.1';
import { FetchInstrumentation } from 'https://esm.sh/@opentelemetry/instrumentation-fetch@0.45.1';
import { ZoneContextManager } from 'https://esm.sh/@opentelemetry/context-zone@1.18.1';
import { SemanticResourceAttributes } from 'https://esm.sh/@opentelemetry/semantic-conventions@1.18.1';

console.log("🔭 Initialisation d'OpenTelemetry (Version Fixe)...");

// 1. Configurer l'exportateur vers Jaeger
const exporter = new OTLPTraceExporter({
    url: 'http://localhost:4318/v1/traces',
});

// 2. Créer le fournisseur de traces
const provider = new WebTracerProvider({
    resource: new Resource({
        // 'service.name' est la clé standard pour Jaeger
        [SemanticResourceAttributes.SERVICE_NAME]: 'mon-app-thymeleaf-front',
    }),
});

// 3. Ajouter les processeurs (Envoi vers Jaeger + Affichage Console F12)
provider.addSpanProcessor(new SimpleSpanProcessor(exporter));
provider.addSpanProcessor(new SimpleSpanProcessor(new ConsoleSpanExporter()));

// 4. Activer le provider
provider.register({
    contextManager: new ZoneContextManager(),
});

// 5. Activer l'instrumentation automatique (Fetch/AJAX)
registerInstrumentations({
    instrumentations: [
        new FetchInstrumentation({
            ignoreUrls: [/localhost:4318/], // Ne pas tracer les envois vers Jaeger
            propagateTraceHeaderCorsUrls: [/.+/], // Lier le front au back
        }),
    ],
});

console.log("✅ OpenTelemetry actif !");