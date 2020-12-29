package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.algorithm.MonthlyExtractCheckServiceImpl.DataCheck;
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
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType);
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
			List<String> errorMasterCheckId,
			List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType);
	/**
	 * 月次
	 */
	void extractMonthlyCheckResult(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,
			List<String> lstAnyConID, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType);
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
}
