package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 次回年休付与を計算
 * @author shuichu_ishida
 */
public interface CalcNextAnnualLeaveGrantDate {

	/**
	 * 次回年休付与を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 次回年休付与リスト
	 */
	List<NextAnnualLeaveGrant> algorithm(String companyId, String employeeId, Optional<DatePeriod> period);
}
