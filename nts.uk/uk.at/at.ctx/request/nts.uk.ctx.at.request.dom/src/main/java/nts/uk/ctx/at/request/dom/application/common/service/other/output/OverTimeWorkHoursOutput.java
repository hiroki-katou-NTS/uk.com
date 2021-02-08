package nts.uk.ctx.at.request.dom.application.common.service.other.output;
/**
 * Refactor5
 * 申請用時間外労働時間パラメータ
 * 
 * @author hoangnd
 *
 */

import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;

@Data
@Builder
public class OverTimeWorkHoursOutput {
	// 当月のありなし
	private Boolean isCurrentMonth;
	
	// 当月の時間外時間
	private AgreementTimeOfManagePeriod currentTimeMonth;
	
	// 当月の年月
	private YearMonth currentMonth;
	
	// 翌月のありなし
	private Boolean isNextMonth;
	
	// 翌月の時間外時間
	private AgreementTimeOfManagePeriod nextTimeMonth;
	
	// 翌月の年月
	private YearMonth nextMonth;
	
}
