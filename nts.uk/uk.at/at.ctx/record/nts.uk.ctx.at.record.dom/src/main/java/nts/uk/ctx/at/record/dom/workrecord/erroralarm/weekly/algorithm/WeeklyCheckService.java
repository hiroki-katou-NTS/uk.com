package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.週次のアラームリストのチェック条件.アルゴリズム.週次の集計処理
 */
public interface WeeklyCheckService {
	void extractWeeklyCheck(
			String cid, 
			List<String> lstSid, 
			DatePeriod period, 
			List<WorkPlaceHistImportAl> wplByListSidAndPeriods, 
			String listOptionalItem,
			List<ResultOfEachCondition> lstResultCondition, 
			List<AlarmListCheckInfor> lstCheckType, 
			Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
}
