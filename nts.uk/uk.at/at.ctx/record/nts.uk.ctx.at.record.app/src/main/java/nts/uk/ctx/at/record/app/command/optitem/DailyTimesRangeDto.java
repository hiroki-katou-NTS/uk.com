package nts.uk.ctx.at.record.app.command.optitem;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.DailyTimesRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyTimesRangeDto {

    private BigDecimal upperLimit;

    private BigDecimal lowerLimit;

    public DailyTimesRange toDomain() {
        return new DailyTimesRange(upperLimit, lowerLimit);
    }

    public static DailyTimesRangeDto fromDomain(DailyTimesRange domain) {
        return new DailyTimesRangeDto(
                domain.getUpperLimit().isPresent() ? domain.getUpperLimit().get().v() : null, 
                domain.getLowerLimit().isPresent() ? domain.getLowerLimit().get().v() : null);
    }
}
