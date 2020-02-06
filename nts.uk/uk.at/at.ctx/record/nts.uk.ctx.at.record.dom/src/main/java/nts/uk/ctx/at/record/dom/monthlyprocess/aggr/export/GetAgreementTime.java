package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOutput;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * ドメインサービス：36協定時間の取得
 * @author shuichi_ishida
 */
public interface GetAgreementTime {

	/**
	 * 36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeIds 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 36協定時間一覧
	 */
	List<AgreementTimeDetail> get(String companyId, List<String> employeeIds, YearMonth yearMonth, ClosureId closureId);
	
	/**
	 * 36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @param criteria 基準日
	 * @return 36協定年間時間
	 */
	Optional<AgreementTimeYear> getYear(String companyId, String employeeId, YearMonthPeriod period, GeneralDate criteria);
	
	/**
	 * 36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 指定年月
	 * @param criteria 基準日
	 * @return 36協定上限複数月平均時間
	 */
	Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId, YearMonth yearMonth, GeneralDate criteria);

	/**
	 * 36協定上限複数月平均時間と年間時間の取得（日指定）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param averageMonth 指定年月　（複数月平均時間の基準年月）
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定時間Output
	 */
	AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, YearMonth averageMonth,
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
	AgreementTimeOutput getAverageAndYear(String companyId, String employeeId, GeneralDate criteria,
			Year year, YearMonth averageMonth, ScheRecAtr scheRecAtr);
}
