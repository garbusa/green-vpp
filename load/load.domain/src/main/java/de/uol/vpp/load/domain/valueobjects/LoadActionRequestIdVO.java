package de.uol.vpp.load.domain.valueobjects;

import de.uol.vpp.load.domain.exceptions.LoadException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadActionRequestIdVO {

    private final String id;

    public LoadActionRequestIdVO(String id) throws LoadException {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new LoadException("validation for action request id failed");
        }
        this.id = id.toUpperCase();
    }
}
