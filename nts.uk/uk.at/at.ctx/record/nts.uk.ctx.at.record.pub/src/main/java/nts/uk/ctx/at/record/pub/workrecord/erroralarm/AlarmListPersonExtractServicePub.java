package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;

public interface AlarmListPersonExtractServicePub {
	/**
	 * マスタチェック
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorMasterCheckId
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	void extractMasterCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
	/**
	 * 日次
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorMasterCheckId
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	void extractDailyCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod,
			String errorDailyCheckId, List<String> extractConditionWorkRecord, List<String> errorDailyCheckCd,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
	/**
	 * 月次
	 */
	void extractMonthlyCheckResult(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,
			List<String> lstAnyConID, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
	/**
	 * 複数月次
	 * @param cid
	 * @param lstSid
	 * @param mPeriod
	 * @param lstAnyConID
	 * @param getWplByListSidAndPeriod
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	void extractMultiMonthlyResult(String cid, List<String> lstSid, YearMonthPeriod mPeriod,
			List<String> lstAnyConID,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType);
	
	/**
	 * スケジュール日次
	 * @param cid
	 * @param lstSid
	 * @param dPeriod
	 * @param errorMasterCheckId
	 * @param getWplByListSidAndPeriod
	 * @param lstStatusEmp
	 * @param lstResultCondition
	 * @param lstCheckType
	 */
	void extractScheDailyCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod,
			String errorDailyCheckId, String listOptionalItem, String listFixedItem,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop);
}
