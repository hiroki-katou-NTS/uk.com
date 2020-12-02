package nts.uk.ctx.at.record.app.command.optitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.NumberRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class NumberRangeDto {

    private DailyTimesRangeDto dailytimeRange;
    
    private MonthlyTimesRangeDto monthlyTimeRange;
    
    public NumberRange toDomain() {
        return new NumberRange(
                Optional.ofNullable(dailytimeRange.toDomain()), 
                Optional.ofNullable(monthlyTimeRange.toDomain())
                );
    }
}
