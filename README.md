# TP 2 Web de Hafidi

Chat Jakarta EE 11 avec JSF, PrimeFaces et LangChain4j. Il utilise Gemini et garde les 20 derniers messages dans la mémoire LangChain4j d'une vue. Le bouton « Nouveau chat » reprend la conversation avec une nouvelle vue.

## Lancer le projet

1. Définir la variable d'environnement `GEMINI_KEY`, puis relancer le terminal ou IntelliJ.
2. Construire le WAR avec `mvn clean package` et Java 25.
3. Démarrer Payara 7 et déployer `target/tp2-web-hafidi-1.0-SNAPSHOT.war`.
4. Accéder à `http://localhost:8080/tp2-web-hafidi-1.0-SNAPSHOT/`.

Pour vérifier le rôle système, choisir « Traducteur Anglais-Français » et envoyer « bonjour ». Pour vérifier la mémoire, commencer un nouveau chat avec le rôle « Assistant », se présenter, puis demander son nom dans un deuxième message.

[Consignes du TP 2](http://richard.grin.free.fr/emsi/rabat-ia/tp/tp2-langchain4j/index.html#appliweb)
