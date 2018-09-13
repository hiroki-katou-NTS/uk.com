package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Data
@AllArgsConstructor
public class YearMonthHistoryItemDto {
	public String historyId;
	public Integer startMonth;
	public Integer endMonth;
	public static YearMonthHistoryItemDto fromDomainToDto (YearMonthHistoryItem domain){
		return new YearMonthHistoryItemDto(domain.identifier(), domain.start().v(), domain.end().v());
	}
}
