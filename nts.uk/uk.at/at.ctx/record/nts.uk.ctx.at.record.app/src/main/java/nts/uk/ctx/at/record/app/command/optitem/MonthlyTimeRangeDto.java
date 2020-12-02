package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyTimeRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class MonthlyTimeRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public MonthlyTimeRange toDomain() {
        return new MonthlyTimeRange(upperLimit, lowerLimit);
    }
}
