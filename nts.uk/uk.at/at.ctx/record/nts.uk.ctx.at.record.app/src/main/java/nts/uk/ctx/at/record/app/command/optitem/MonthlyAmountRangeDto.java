package nts.uk.ctx.at.record.app.command.optitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.MonthlyAmountRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyAmountRangeDto {

    private Integer upperLimit;
    
    private Integer lowerLimit;
    
    public MonthlyAmountRange toDomain() {
        return new MonthlyAmountRange(upperLimit, lowerLimit);
    }
    
    public static MonthlyAmountRangeDto fromDomain(MonthlyAmountRange domain) {
        return new MonthlyAmountRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
