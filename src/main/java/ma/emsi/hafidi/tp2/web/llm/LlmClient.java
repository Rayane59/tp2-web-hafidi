package ma.emsi.hafidi.tp2.web.llm;

import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.Dependent;

import java.io.Serializable;
import java.time.Duration;

@Dependent
public class LlmClient implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int TAILLE_MEMOIRE = 20;

    private String systemRole;
    // L'historique reste disponible si Payara sérialise la vue JSF.
    private String historique;
    private transient Assistant assistant;
    private transient ChatMemory chatMemory;

    public void setSystemRole(String systemRole) {
        this.systemRole = systemRole;
        this.historique = null;
        if (this.chatMemory != null) {
            this.chatMemory.clear();
            this.chatMemory.add(SystemMessage.from(systemRole));
        }
    }

    public String envoyer(String question) {
        if (assistant == null) {
            initialiser();
        }
        try {
            String reponse = assistant.chat(question);
            historique = ChatMessageSerializer.messagesToJson(chatMemory.messages());
            return reponse;
        } catch (RuntimeException e) {
            // Au prochain essai, on repart du dernier échange réussi.
            this.assistant = null;
            this.chatMemory = null;
            throw e;
        }
    }

    private void initialiser() {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null || cle.isBlank()) {
            throw new IllegalStateException("GEMINI_KEY est absente. Configurez-la et redémarrez Payara.");
        }

        var model = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.2)
                .timeout(Duration.ofSeconds(45))
                .maxRetries(2)
                .build();

        var memoire = MessageWindowChatMemory.withMaxMessages(TAILLE_MEMOIRE);
        if (historique != null) {
            ChatMessageDeserializer.messagesFromJson(historique).forEach(memoire::add);
        } else if (systemRole != null && !systemRole.isBlank()) {
            memoire.add(SystemMessage.from(systemRole));
        }

        this.chatMemory = memoire;
        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(memoire)
                .build();
    }
}
