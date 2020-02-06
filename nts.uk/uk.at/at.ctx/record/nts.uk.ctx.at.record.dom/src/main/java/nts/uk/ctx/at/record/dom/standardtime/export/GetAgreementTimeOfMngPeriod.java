package nts.uk.ctx.at.record.dom.standardtime.export;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 管理期間の36協定時間を取得
 * @author shuichi_ishida
 */
public interface GetAgreementTimeOfMngPeriod {

	/**
	 * 管理期間の36協定時間を取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	List<AgreementTimeOfManagePeriod> algorithm(String employeeId, Year year);
	
	
	/**
	 * 管理期間の36協定時間を取得
	 * @param employeeId List 社員ID
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	Map<String,List<AgreementTimeOfManagePeriod>> algorithm(List<String> employeeId, Year year);
	
	/**
	 * RequestList 612
	 * [NO.612]年月期間を指定して管理期間の36協定時間を取得する
	 * @param employeeIDLst 社員ID(List)
	 * @param yearMonthPeriod 年月期間
	 * @return
	 */
	List<AgreementTimeOfManagePeriod> getAgreementTimeByMonths(List<String> employeeIDLst, YearMonthPeriod yearMonthPeriod);
	
}
