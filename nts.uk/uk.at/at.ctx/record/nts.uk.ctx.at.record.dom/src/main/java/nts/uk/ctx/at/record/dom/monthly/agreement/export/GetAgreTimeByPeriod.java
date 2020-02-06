package nts.uk.ctx.at.record.dom.monthly.agreement.export;

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
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
public interface GetAgreTimeByPeriod {

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
	List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr);

	List<AgreementTimeByEmp> algorithmImprove(String companyId, List<String> employeeIds, GeneralDate criteria,
											  Month startMonth, Year year, List<PeriodAtrOfAgreement> periodAtrs, Map<String, YearMonthPeriod> periodWorking);

	/**
	 * 指定月36協定上限月間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 月別実績の36協定上限時間リスト
	 */
	List<AgreMaxTimeMonthOut> maxTime(String companyId, String employeeId, YearMonthPeriod period);

	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
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
	Optional<AgreementTimeYear> timeYear(String companyId, String employeeId, GeneralDate criteria, Year year);
	
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
	List<AgreementTimeByPeriod> algorithm(String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr, AgeementTimeCommonSetting basicSetGetter);
}
