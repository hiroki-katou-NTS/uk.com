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

    private DailyAmountRangeDto dailyAmountRange;
    
    private MonthlyAmountRangeDto monthlyAmountRange;
    
    public AmountRange todomain() {
        return new AmountRange(
                Optional.ofNullable(dailyAmountRange.toDomain()), 
                Optional.ofNullable(monthlyAmountRange.toDomain()));
    }
    
    public static AmountRangeDto fromDomain(AmountRange domain) {
        return new AmountRangeDto(
                domain.getDailyAmountRange().isPresent() ? DailyAmountRangeDto.fromDomain(domain.getDailyAmountRange().get()) : new DailyAmountRangeDto(), 
                domain.getMonthlyAmountRange().isPresent() ? MonthlyAmountRangeDto.fromDomain(domain.getMonthlyAmountRange().get()) : new MonthlyAmountRangeDto());
    }
}
