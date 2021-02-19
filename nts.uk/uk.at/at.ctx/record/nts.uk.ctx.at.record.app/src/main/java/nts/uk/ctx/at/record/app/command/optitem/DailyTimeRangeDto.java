package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyTimeRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyTimeRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public DailyTimeRange toDomain() {
        return new DailyTimeRange(upperLimit, lowerLimit);
    }
    
    public static DailyTimeRangeDto fromDomain(DailyTimeRange domain) {
        return new DailyTimeRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
