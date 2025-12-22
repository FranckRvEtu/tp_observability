# TP OBSERVABILITY

## Installation & Version

- Java 21
- Maven 3.6.3

### Installation de Jaeger. 

Jaeger tourne en local dans cette application. Pour l'installer, allez sur https://www.jaegertracing.io/download/ et télécharger la version 2.x.x correspondante à votre machine.


## Démarrage

__Sous Intellij Idea__, deux configurations existent déjà : 

- App : Démarre l'application sur le port par défaut.
- SpoonLauncher : Génère le code modifié par Spoon.

Vérifiez que ogs/profile.json soit vide, s'il ne l'est pas, videz le. 

Pour démarrer proprement, lancez d'abord SpoonLauncher. Récupérez le code de target/generated-sources/spoon/service/ProductService.java et copiez le entièrement dans src/java/service/ProductService.java.
Vous pouvez démarrer App. Les simulations vont se lancer. 

Allez voir profile.json, des logs sont normalement apparus.

## Consommer le front-end

La console devrait vous dire sur quel port tourne l'application. Vous pouvez y aller et consommer l'application.

## Partie observabilité 

Avant de vous rendre sur l'application, lancez Jaeger. Jaeger devrait alors être accessible dans votre navigateur au port 16686.

