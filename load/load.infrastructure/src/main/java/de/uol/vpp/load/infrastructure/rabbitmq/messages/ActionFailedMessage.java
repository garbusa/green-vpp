package de.uol.vpp.load.infrastructure.rabbitmq.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RabbitMQ Austauschobjekt
 * Benachrichtigt Maßnahmen-Service, wenn etwas bei der Lastgenerierung fehlgeschlagen ist
 */
@Data
@NoArgsConstructor
public class ActionFailedMessage implements Serializable {
    @JsonProperty("actionRequestId")
    private String actionRequestId;
}
