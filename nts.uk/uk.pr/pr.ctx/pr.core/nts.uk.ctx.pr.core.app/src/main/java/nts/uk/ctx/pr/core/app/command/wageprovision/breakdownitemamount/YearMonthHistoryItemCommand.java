package nts.uk.ctx.pr.core.app.command.wageprovision.breakdownitemamount;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Value
public class YearMonthHistoryItemCommand {
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public static YearMonthHistoryItem fromCommandToDomain (YearMonthHistoryItemCommand command){
        return new YearMonthHistoryItem(command.getHistoryId(), new YearMonthPeriod(new YearMonth(command.getStartMonth()), new YearMonth(command.getEndMonth())));
    }
}
