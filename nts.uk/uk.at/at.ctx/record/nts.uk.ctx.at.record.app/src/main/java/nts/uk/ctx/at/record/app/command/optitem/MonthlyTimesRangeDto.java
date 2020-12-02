package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyTimesRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class MonthlyTimesRangeDto {

    private Double upperLimit;
    
    private Double lowerLimit;
    
    public MonthlyTimesRange toDomain() {
        return new MonthlyTimesRange(upperLimit, lowerLimit);
    }
}
