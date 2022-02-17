package nts.uk.screen.com.app.find.cmm030.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentAppHistoryItem;

@Data
@AllArgsConstructor
public class EmploymentAppHistoryItemDto {

	/** 履歴ID */
	private String historyId;

	/** 期間 */
	private DatePeriod datePeriod;
	
	public static EmploymentAppHistoryItemDto fromDomain(EmploymentAppHistoryItem domain) {
		return new EmploymentAppHistoryItemDto(domain.getHistoryId(), domain.getDatePeriod());
	}
}
