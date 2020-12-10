package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreMaxAverageTimeMultiExport;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementTimeYearExport;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
public interface AgreementTimeByPeriodPub {

	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList547
	Optional<AgreMaxAverageTimeMultiExport> maxAverageTimeMulti(String employeeId, GeneralDate criteria, YearMonth yearMonth);

	/**
	 * 指定年36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 36協定年間時間
	 */
	// RequestList549
	Optional<AgreementTimeYearExport> timeYear(String employeeId, GeneralDate criteria, Year year);
	
	
	Object getCommonSetting(String companyId, List<String> employeeId, DatePeriod criteria);
}
