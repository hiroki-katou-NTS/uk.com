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

    private DailyTimesRangeDto dailyNumberRange;
    
    private MonthlyTimesRangeDto monthlyNumberRange;
    
    public NumberRange toDomain() {
        return new NumberRange(
                Optional.ofNullable(dailyNumberRange.toDomain()), 
                Optional.ofNullable(monthlyNumberRange.toDomain())
                );
    }
    
    public static NumberRangeDto fromDomain(NumberRange domain) {
        return new NumberRangeDto(
                domain.getDailyTimesRange().isPresent() ? DailyTimesRangeDto.fromDomain(domain.getDailyTimesRange().get()) : new DailyTimesRangeDto(), 
                domain.getMonthlyTimesRange().isPresent() ? MonthlyTimesRangeDto.fromDomain(domain.getMonthlyTimesRange().get()) : new MonthlyTimesRangeDto());
    }
}
