package nts.uk.ctx.pr.core.app.find.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

@Data
@AllArgsConstructor
public class YearMonthHistoryItemDto {
	public String historyID;
	public Integer startMonth;
	public Integer endMonth;
	public static YearMonthHistoryItemDto fromDomainToDto (YearMonthHistoryItem yearMonth){
		return new YearMonthHistoryItemDto(yearMonth.identifier(), yearMonth.start().v(), yearMonth.end().v());
	}
}
