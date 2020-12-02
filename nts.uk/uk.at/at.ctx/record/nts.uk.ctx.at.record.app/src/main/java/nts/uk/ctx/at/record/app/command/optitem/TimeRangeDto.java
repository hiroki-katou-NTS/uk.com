package nts.uk.ctx.at.record.app.command.optitem;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.optitem.TimeRange;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class TimeRangeDto {

    private DailyTimeRangeDto dailyTimeRange;
    
    private MonthlyTimeRangeDto monthlyTimeRange;
    
    public TimeRange toDomain() {
        return new TimeRange(
                Optional.ofNullable(dailyTimeRange.toDomain()), 
                Optional.ofNullable(monthlyTimeRange.toDomain()));
    }
}
