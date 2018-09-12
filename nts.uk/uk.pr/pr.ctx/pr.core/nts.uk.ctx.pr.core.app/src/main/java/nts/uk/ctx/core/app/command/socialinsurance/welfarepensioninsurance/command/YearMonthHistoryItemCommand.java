package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

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
