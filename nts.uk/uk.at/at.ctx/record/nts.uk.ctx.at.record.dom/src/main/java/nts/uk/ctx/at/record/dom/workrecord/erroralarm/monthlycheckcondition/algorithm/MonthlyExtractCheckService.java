package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmExtractionCondition;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface MonthlyExtractCheckService {
	/**
	 * 月次の集計処理
	 * @param cid 会社ID
	 * @param lstSid　社員ID
	 * @param mPeriod　期間
	 * @param fixConId　固定抽出条件ID
	 * @param lstAnyConID　任意抽出条件ID
	 * @param getWplByListSidAndPeriod　List＜社員IDと職場履歴＞
	 * @param lstResultCondition　　各チェック条件の結果
	 * @param lstCheckType　List＜チェック種類、コード＞
	 */
	void extractMonthlyAlarm(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,List<String> lstAnyConID,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeList,
			List<AlarmExtractionCondition> alarmExtractConditions, String alarmCheckConditionCode);

}
