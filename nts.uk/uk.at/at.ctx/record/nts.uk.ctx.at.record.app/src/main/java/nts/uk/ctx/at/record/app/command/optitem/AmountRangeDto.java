package nts.uk.ctx.at.record.app.command.optitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.AmountRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class AmountRangeDto {

    private DailyAmountRangeDto dailyamountRange;
    
    private MonthlyAmountRangeDto monthlyAmountRange;
    
    public AmountRange todomain() {
        return new AmountRange(
                Optional.ofNullable(dailyamountRange.toDomain()), 
                Optional.ofNullable(monthlyAmountRange.toDomain()));
    }
}
