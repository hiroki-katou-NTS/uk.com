package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
public interface AgreementTimeByPeriodPub {

	/**
	 * 指定期間36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param startMonth 起算月
	 * @param year 年度
	 * @param periodAtr 期間区分
	 * @return 指定期間36協定時間リスト
	 */
	// RequestList453
	List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr);


	// RequestList453
	List<AgreementTimeByEmpExport> algorithmImprove(String companyId, List<String> employeeIds, GeneralDate criteria,
									   Month startMonth, Year year, List<PeriodAtrOfAgreement> periodAtrs, Map<String, YearMonthPeriod> periodWorking);

	/**
	 * 指定月36協定上限月間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 月別実績の36協定上限時間リスト
	 */
	// RequestList548
	List<AgreMaxTimeMonthOut> maxTime(String companyId, String employeeId, YearMonthPeriod period);

	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList547
	Optional<AgreMaxAverageTimeMulti> maxAverageTimeMulti(String companyId, String employeeId, GeneralDate criteria,
			YearMonth yearMonth);

	/**
	 * 指定年36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 36協定年間時間
	 */
	// RequestList549
	Optional<AgreementTimeYear> timeYear(String companyId, String employeeId, GeneralDate criteria, Year year);
	
	List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr, Object basicSetGetter);
	
	Object getCommonSetting(String companyId, List<String> employeeId, DatePeriod criteria);
}
