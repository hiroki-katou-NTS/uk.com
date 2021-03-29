package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;

public interface AgreementTimeByPeriodAdapter {
	
	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList547
	Optional<AgreMaxAverageTimeMultiImport> maxAverageTimeMulti(String companyId, String employeeId, GeneralDate criteria,
			YearMonth yearMonth);
	/**
	 * 年月期間を指定して管理期間の36協定時間を取得する
	 * @param sids
	 * @param ymPeriod
	 * @return 
	 */
	List<AgreementTimeOfManagePeriodImport> get(List<String> sids, YearMonthPeriod ymPeriod);
	/**
	 * 管理期間の36協定時間を取得
	 * @param sids
	 * @param year
	 * @return
	 */
	Map<String, List<AgreementTimeOfManagePeriodImport>> getAgreement(List<String> sids, Year year);
}
