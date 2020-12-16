package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyAmountRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAmountRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public DailyAmountRange toDomain() {
        return new DailyAmountRange(upperLimit, lowerLimit);
    }
    
    public static DailyAmountRangeDto fromDomain(DailyAmountRange domain) {
        return new DailyAmountRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
