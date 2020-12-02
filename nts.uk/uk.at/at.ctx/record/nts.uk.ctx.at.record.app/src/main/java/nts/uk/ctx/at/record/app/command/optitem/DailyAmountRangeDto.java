package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyAmountRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class DailyAmountRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public DailyAmountRange toDomain() {
        return new DailyAmountRange(upperLimit, lowerLimit);
    }
}
