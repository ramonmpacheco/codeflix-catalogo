package com.codeflix.catalogo.infrastructure.kafka;

import com.codeflix.catalogo.infrastructure.genre.GenreGateway;
import org.springframework.stereotype.Component;

@Component
public class GenreListener {

    private static final Logger LOG = LoggerFactory.getLogger(GenreListener.class);
    private static final TypeReference<MessageValue<GenreEvent>> GENRE_MESSAGE_TYPE = new TypeReference<>() {
    };

    private final GenreGateway genreGateway;
    private final SaveGenreUseCase saveGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;

    public GenreListener(
            final GenreGateway genreGateway,
            final SaveGenreUseCase saveGenreUseCase,
            final DeleteGenreUseCase deleteGenreUseCase
    ) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.saveGenreUseCase = Objects.requireNonNull(saveGenreUseCase);
        this.deleteGenreUseCase = Objects.requireNonNull(deleteGenreUseCase);
    }

    @KafkaListener(
            concurrency = "${kafka.consumers.genres.concurrency}",
            containerFactory = "kafkaListenerFactory",
            topics = "${kafka.consumers.genres.topics}",
            groupId = "${kafka.consumers.genres.group-id}",
            id = "${kafka.consumers.genres.id}",
            properties = {
                    "auto.offset.reset=${kafka.consumers.genres.auto-offset-reset}"
            }
    )
    @RetryableTopic(
            backoff = @Backoff(delay = 1000, multiplier = 2),
            attempts = "${kafka.consumers.genres.max-attempts}",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    public void onMessage(@Payload final String payload, final ConsumerRecordMetadata metadata) {
        LOG.info("Message received from Kafka [topic:{}] [partition:{}] [offset:{}]: {}", metadata.topic(), metadata.partition(), metadata.offset(), payload);
        final var messagePayload = Json.readValue(payload, GENRE_MESSAGE_TYPE).payload();
        final var op = messagePayload.operation();

        if (Operation.isDelete(op)) {
            this.deleteGenreUseCase.execute(new DeleteGenreUseCase.Input(messagePayload.before().id()));
        } else {
            this.genreGateway.genreOfId(messagePayload.after().id())
                    .map(it -> new SaveGenreUseCase.Input(it.id(), it.name(), it.isActive(), it.categoriesId(), it.createdAt(), it.updatedAt(), it.deletedAt()))
                    .ifPresentOrElse(this.saveGenreUseCase::execute, () -> {
                        LOG.warn("Genre was not found {}", messagePayload.after().id());
                    });
        }
    }

    @DltHandler
    public void onDLTMessage(@Payload final String payload, final ConsumerRecordMetadata metadata) {
        LOG.warn("Message received from Kafka at DLT [topic:{}] [partition:{}] [offset:{}]: {}", metadata.topic(), metadata.partition(), metadata.offset(), payload);
    }
}
