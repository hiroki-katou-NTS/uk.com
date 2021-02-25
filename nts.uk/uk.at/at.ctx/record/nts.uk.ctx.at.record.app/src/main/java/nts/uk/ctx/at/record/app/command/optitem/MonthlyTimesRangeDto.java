package nts.uk.ctx.at.record.app.command.optitem;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyTimesRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyTimesRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTimesRangeDto {

    private BigDecimal upperLimit;
    
    private BigDecimal lowerLimit;
    
    public MonthlyTimesRange toDomain() {
        return new MonthlyTimesRange(
                upperLimit == null ? null : upperLimit.doubleValue(), 
                lowerLimit == null ? null : lowerLimit.doubleValue());
    }
    
    public static MonthlyTimesRangeDto fromDomain(MonthlyTimesRange domain) {
        return new MonthlyTimesRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
