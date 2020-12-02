package nts.uk.ctx.at.record.app.command.optitem;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyTimesRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class DailyTimesRangeDto {

    private BigDecimal upperLimit;
    
    private BigDecimal lowerLimit;
    
    public DailyTimesRange toDomain() {
        return new DailyTimesRange(upperLimit, lowerLimit);
    }
}
