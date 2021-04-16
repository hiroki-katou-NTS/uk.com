package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.スケジュール日次・月次・年間.スケジュール年間のアラームリストのチェック条件.アルゴリズム.スケジュール年間の集計処理
 *
 */
public interface ScheYearCheckService {
	
	/**
	 * スケジュール年間の集計処理
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorCheckId
	 * @param extractConditionWorkRecord
	 * @param errorDailyCheckCd
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 * @param counter
	 * @param shouldStop
	 */
	void extractScheYearCheck(String cid, List<String> lstSid, DatePeriod dPeriod,	
			String errorCheckId, String listOptionalItem,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition, 
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop); 
}
