package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyTimeRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class DailyTimeRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public DailyTimeRange toDomain() {
        return new DailyTimeRange(upperLimit, lowerLimit);
    }
}
