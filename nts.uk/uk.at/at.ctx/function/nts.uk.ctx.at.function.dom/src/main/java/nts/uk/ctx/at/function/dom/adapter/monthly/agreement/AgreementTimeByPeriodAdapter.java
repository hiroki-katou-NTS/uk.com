package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;

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
	 * 指定年36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 36協定年間時間
	 */
	// RequestList549
	Optional<AgreementTimeYearImport> timeYear(String employeeId, GeneralDate criteria, Year year);
}
