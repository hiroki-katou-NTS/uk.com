package nts.uk.ctx.at.record.pub.monthlyprocess.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ScheRecAtr;

/**
 * 36協定時間の取得
 * @author shuichi_ishida
 */
public interface GetAgreementTimePub {

	/**
	 * 36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeIds 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 36協定時間一覧
	 */
	// RequestList333
	AgreementTimeOfManagePeriod calcAgreementTime(String sid, YearMonth ym,
			List<IntegrationOfDaily> dailyRecord, GeneralDate baseDate, ScheRecAtr scheRecAtr);
	
	/**
	 * 36協定年間時間の取得
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定年間時間
	 */
	// RequestList544
	Optional<AgreementTimeYear> getYear(String employeeId, YearMonthPeriod period, GeneralDate criteria, ScheRecAtr scheRecAtr);
	
	/**
	 * 36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 指定年月
	 * @param criteria 基準日
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList541
	Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(List<IntegrationOfDaily> dailyRecord, 
			String employeeId, YearMonth yearMonth, GeneralDate criteria, ScheRecAtr scheRecAtr);

	/**
	 * 36協定上限複数月平均時間と年間時間の取得（日指定）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param averageMonth 指定年月　（複数月平均時間の基準年月）
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定時間Output
	 */
	// RequestList599
	AgreementTimeExport getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
			GeneralDate criteria, ScheRecAtr scheRecAtr);

	/**
	 * 36協定上限複数月平均時間と年間時間の取得（年度指定）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @param averageMonth 指定年月　（複数月平均時間の基準年月）
	 * @param scheRecAtr 予実区分
	 * @return 36協定時間Output
	 */
	AgreementTimeExport getAverageAndYear(String companyId, String employeeId, GeneralDate criteria,
			Year year, YearMonth averageMonth, ScheRecAtr scheRecAtr);
}
