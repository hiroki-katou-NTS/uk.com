package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyTimeRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyTimeRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public MonthlyTimeRange toDomain() {
        return new MonthlyTimeRange(upperLimit, lowerLimit);
    }
    
    public static MonthlyTimeRangeDto fromDomain(MonthlyTimeRange domain) {
        return new MonthlyTimeRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
