package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Data
@AllArgsConstructor
public class StatementLayoutHistCommand {
    private String statementCode;
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public int layoutPattern;

    public YearMonthHistoryItem toYearMonthDomain() {
        return new YearMonthHistoryItem(historyId, new YearMonthPeriod(new YearMonth(startMonth), new YearMonth(endMonth)));
    }
}
