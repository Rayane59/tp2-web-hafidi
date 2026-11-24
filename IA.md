# Utilisation de l’IA — TP 2 Web

## 1. Erreur de compilation sur ChatMemory

Maven ne trouvait pas la classe `ChatMemory` avec l’import initial. L’IA a corrigé l’import vers `dev.langchain4j.memory.ChatMemory`. Maven a ensuite compilé le projet et généré le fichier WAR.

## 2. Vérification avant le commit

`git diff --cached --check` a signalé une ligne vide supplémentaire à la fin de trois fichiers. L’IA a retiré les lignes en trop. La vérification Git, le commit et le push du projet ont ensuite réussi.

Sur Payara, l’IA a vérifié que Gemini retenait le nom « Noria » entre deux échanges et que le bouton « Nouveau chat » vidait la conversation. La mémoire est liée à la vue JSF pour que chaque conversation ait son propre historique.
