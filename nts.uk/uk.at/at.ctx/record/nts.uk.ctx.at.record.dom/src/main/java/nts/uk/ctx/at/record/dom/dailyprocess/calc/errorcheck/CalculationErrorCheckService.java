package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.other.ManagePerPersonDailySet;

/**
 * ドメインサービス：日別計算のエラーチェック処理
 * @author keisuke_hoshina
 *
 */
public interface CalculationErrorCheckService {
	
	/**
	 * エラーチェック
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param managePerPersonDailySet 
	 * @param errorAlarm 
	 */
	public IntegrationOfDaily errorCheck(IntegrationOfDaily integrationOfDaily, ManagePerPersonDailySet managePerPersonDailySet, ManagePerCompanySet master);
	
	/**
	 * エラーチェック (new)
	 * @param companyId 会社ID
	 * @param employeeID 社員ID
	 * @param ymd 対象日
	 * @param integrationList 日別実績(Work)
	 * @param sysfixecategory システム固定区分（型：boolean）
	 * @return
	 */
	public IntegrationOfDaily errorCheck(String companyId,String employeeID,GeneralDate ymd, IntegrationOfDaily integrationOfDaily,boolean sysfixecategory);
}
