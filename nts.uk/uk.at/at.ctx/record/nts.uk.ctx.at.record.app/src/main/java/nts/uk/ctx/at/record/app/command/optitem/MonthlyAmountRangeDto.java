package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyAmountRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class MonthlyAmountRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public MonthlyAmountRange toDomain() {
        return new MonthlyAmountRange(upperLimit, lowerLimit);
    }
}
