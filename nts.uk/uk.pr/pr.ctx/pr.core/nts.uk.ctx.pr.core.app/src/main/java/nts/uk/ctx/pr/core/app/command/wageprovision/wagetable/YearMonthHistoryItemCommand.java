package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Data
@AllArgsConstructor
public class YearMonthHistoryItemCommand {
	private String historyID;
	private Integer startMonth;
	private Integer endMonth;
	public YearMonthHistoryItem fromCommandToDomain (){
		return new YearMonthHistoryItem(historyID, new YearMonthPeriod(new YearMonth(startMonth), new YearMonth(endMonth)));
	}
}
