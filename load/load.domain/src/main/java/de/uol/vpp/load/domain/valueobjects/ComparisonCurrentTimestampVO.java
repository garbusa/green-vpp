package de.uol.vpp.load.domain.valueobjects;

import de.uol.vpp.load.domain.exceptions.LoadException;
import de.uol.vpp.load.domain.utils.TimestampUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ComparisonCurrentTimestampVO {

    private String timestamp;

    public ComparisonCurrentTimestampVO(Long ts) throws LoadException {
        if (ts == null) {
            throw new LoadException("load start timestamp validation failed");
        }

        try {
            this.timestamp = TimestampUtils.toBerlinTimestamp(ts);
        } catch (Exception e) {
            throw new LoadException("failed to create timestamp", e);
        }
    }


}
