package nts.uk.ctx.pr.core.app.command.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Data
@AllArgsConstructor
public class YearMonthHistoryItemCommand {
	public String historyID;
	public Integer startMonth;
	public Integer endMonth;
	public YearMonthHistoryItem fromCommandToDomain (){
		return new YearMonthHistoryItem(this.historyID, new YearMonthPeriod(new YearMonth(this.startMonth), new YearMonth(this.endMonth)));
	}
}
