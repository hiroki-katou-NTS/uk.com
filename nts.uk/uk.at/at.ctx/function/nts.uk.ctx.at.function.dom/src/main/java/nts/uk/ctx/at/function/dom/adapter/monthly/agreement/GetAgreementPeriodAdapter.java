package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;

public interface GetAgreementPeriodAdapter {

	/**
	 * 年度を指定して36協定期間を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 期間
	 */
	// RequestList554
	Optional<DatePeriod> byYear(String companyId, Year year);
}
