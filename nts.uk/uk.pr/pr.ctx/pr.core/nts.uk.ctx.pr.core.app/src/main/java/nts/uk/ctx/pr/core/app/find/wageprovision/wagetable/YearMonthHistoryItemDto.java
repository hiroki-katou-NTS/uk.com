package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Data
@AllArgsConstructor
public class YearMonthHistoryItemDto {
	private String historyID;
	private Integer startMonth;
	private Integer endMonth;
	public static YearMonthHistoryItemDto fromDomainToDto (YearMonthHistoryItem domain){
		return new YearMonthHistoryItemDto(domain.identifier(), domain.start().v(), domain.end().v());
	}
}
