package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;

@Data
@Builder
public class YearMonthOvertimeHourse {
	/**対象年月*/
	private YearMonth yearMonth;
	
	/** 対象年月の時間外時間 */
	AgreementTimeOfManagePeriod agreeTime;
	
}
