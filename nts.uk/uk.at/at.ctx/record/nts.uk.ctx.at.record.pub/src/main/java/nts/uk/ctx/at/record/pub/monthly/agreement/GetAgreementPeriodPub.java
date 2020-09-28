package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;

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
	Optional<DatePeriod> byYear(String companyId, Year year);
	
	/**
	 * 指定日を含む年期間を取得
	 * @param companyId 会社ID
	 * @param baseDate 指定年月日
	 * @param agreementOperationSet 36協定運用設定
	 * @return 年月期間
	 */
	// RequestList579
	Optional<YearMonthPeriod> containsDate(String companyId, GeneralDate baseDate, 
			Optional<AgreementOperationSetting> agreementOperationSet);
}
