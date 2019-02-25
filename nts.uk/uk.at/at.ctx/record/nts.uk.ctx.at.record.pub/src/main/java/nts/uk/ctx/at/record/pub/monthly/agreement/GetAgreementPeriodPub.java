package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
public interface GetAgreementPeriodPub {

	/**
	 * 年度を指定して36協定期間を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 期間
	 */
	// RequestList554
	Optional<DatePeriod> byYear(String companyId, String employeeId, GeneralDate criteria, Year year);
}
