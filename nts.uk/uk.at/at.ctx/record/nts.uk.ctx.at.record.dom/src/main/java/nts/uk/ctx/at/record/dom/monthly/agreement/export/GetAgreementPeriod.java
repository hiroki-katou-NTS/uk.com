package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 36協定期間を取得
 * @author shuichi_ishida
 */
public interface GetAgreementPeriod {

	/**
	 * 年度を指定して36協定期間を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 期間
	 */
	Optional<DatePeriod> byYear(String companyId, String employeeId, GeneralDate criteria, Year year);
	
	/**
	 * 指定日を含む年期間を取得
	 * @param companyId 会社ID
	 * @param criteria 指定年月日
	 * @param agreementOperationSet 36協定運用設定
	 * @param closure 締め
	 * @return 年月期間
	 */
	Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate criteria,
			Optional<AgreementOperationSetting> agreementOperationSet, Closure closure);
}
