package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

/**
 * 実績計算を呼び出す用のサービス
 * @author keisuke_hoshina
 *
 */
public interface CalculateDailyRecordServiceCenter{//

	//計算
	//old_process. Don't use!
	public List<IntegrationOfDaily> calculate(List<IntegrationOfDaily> integrationOfDaily);

	//計算(会社共通のマスタを渡せる場合)
	default public List<IntegrationOfDaily> calculatePassCompanySetting(List<IntegrationOfDaily> integrationOfDaily,Optional<ManagePerCompanySet> companySet,ExecutionType reCalcAtr) {
		return calculatePassCompanySetting(CalculateOption.asDefault(), integrationOfDaily, companySet,reCalcAtr);
	}

	//勤務予定情報を計算する
	//計算(会社共通のマスタを渡せる場合)
	public List<IntegrationOfDaily> calculatePassCompanySetting(CalculateOption calcOption, List<IntegrationOfDaily> integrationOfDaily,Optional<ManagePerCompanySet> companySet,ExecutionType reCalcAtr);
	
	//計算(就業計算と集計用)
	@SuppressWarnings("rawtypes")
	public ManageProcessAndCalcStateResult calculateForManageState(List<IntegrationOfDaily> integrationOfDaily,List<ClosureStatusManagement> closureList,ExecutionType reCalcAtr, String empCalAndSumExecLogID);
	
	//エラーチェック
	public List<IntegrationOfDaily> errorCheck(CompanyId companyId, List<IntegrationOfDaily> integrationList);

	//計算(更新処理自動実行用)
	public ManageProcessAndCalcStateResult calculateForclosure(List<IntegrationOfDaily> integrationOfDaily,ManagePerCompanySet companySet, List<ClosureStatusManagement> closureList,String executeLogId);

	//計算(スケジュールからの窓口)
	List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet);
	
}
