package nts.uk.ctx.at.function.ac.alarm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.alarm.AlarmListPersonServiceAdapter;
import nts.uk.ctx.at.function.dom.adapter.companyRecord.StatusOfEmployeeAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceIdAndPeriodImportAl;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.AlarmListPersonExtractServicePub;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
@Stateless
public class AlarmListPersonServiceAdapterImpl implements AlarmListPersonServiceAdapter{
	@Inject
	private AlarmListPersonExtractServicePub extractService;

	@Override
	public void extractMasterCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod,
			String errorMasterCheckId, List<WorkPlaceHistImport> lstWplHist,
			List<StatusOfEmployeeAdapter> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckInfor, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop) {
		List<WorkPlaceHistImportAl> lstWkpIdAndPeriod = lstWplHist.stream().map(x -> 
					new WorkPlaceHistImportAl(x.getEmployeeId(), 
							x.getLstWkpIdAndPeriod().stream()
							.map(a -> new WorkPlaceIdAndPeriodImportAl(a.getDatePeriod(), a.getWorkplaceId())).collect(Collectors.toList()))
				).collect(Collectors.toList());
		List<StatusOfEmployeeAdapterAl> lstStaEmp = lstStatusEmp.stream()
				.map(x -> new StatusOfEmployeeAdapterAl(x.getEmployeeId(), x.getListPeriod())).collect(Collectors.toList());
		extractService.extractMasterCheckResult(cid, 
				lstSid, 
				dPeriod,
				errorMasterCheckId,
				lstWkpIdAndPeriod,
				lstStaEmp,
				lstResultCondition,
				lstCheckInfor, counter, shouldStop);
		
	}
	
	/**
	 * 日次
	 */
	@Override
	public void extractDailyCheckResult(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, DailyAlarmCondition dailyAlarmCondition, 
			List<WorkPlaceHistImport> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapter> lstStatusEmp, 
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop) {
		
		List<String> extractConditionWorkRecord = dailyAlarmCondition.getExtractConditionWorkRecord();
		List<String> errorDailyCheckCd = dailyAlarmCondition.getErrorAlarmCode();
		
		List<WorkPlaceHistImportAl> lstWkpIdAndPeriod = getWplByListSidAndPeriod.stream().map(x -> 
					new WorkPlaceHistImportAl(x.getEmployeeId(), 
							x.getLstWkpIdAndPeriod().stream()
							.map(a -> new WorkPlaceIdAndPeriodImportAl(a.getDatePeriod(), a.getWorkplaceId())).collect(Collectors.toList()))
				).collect(Collectors.toList());
		
		List<StatusOfEmployeeAdapterAl> lstStaEmp = lstStatusEmp.stream()
			.map(x -> new StatusOfEmployeeAdapterAl(x.getEmployeeId(), x.getListPeriod())).collect(Collectors.toList());
		
		extractService.extractDailyCheckResult(cid, lstSid, dPeriod, errorDailyCheckId, extractConditionWorkRecord, 
				errorDailyCheckCd, lstWkpIdAndPeriod, lstStaEmp, lstResultCondition, lstCheckType,
				counter, shouldStop);
	}

	@Override
	public void extractMonthCheckResult(String cid, List<String> lstSid, YearMonthPeriod mPeriod, String fixConId,
			List<String> lstAnyConID, List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckInfor, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop) {
		List<WorkPlaceHistImportAl> lstWkpIdAndPeriod = lstWplHist.stream().map(x -> 
			new WorkPlaceHistImportAl(x.getEmployeeId(), 
					x.getLstWkpIdAndPeriod().stream()
					.map(a -> new WorkPlaceIdAndPeriodImportAl(a.getDatePeriod(), a.getWorkplaceId())).collect(Collectors.toList())))
					.collect(Collectors.toList());		
		extractService.extractMonthlyCheckResult(cid,
				lstSid,
				mPeriod,
				fixConId,
				lstAnyConID,
				lstWkpIdAndPeriod,
				lstResultCondition,
				lstCheckInfor,
				counter,
				shouldStop);
	}

	@Override
	public void extractMultiMonthCheckResult(String cid, List<String> lstSid, YearMonthPeriod mPeriod,
			List<String> lstAnyConID, List<WorkPlaceHistImport> lstWplHist,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckInfor) {
		List<WorkPlaceHistImportAl> lstWkpIdAndPeriod = lstWplHist.stream().map(x -> 
			new WorkPlaceHistImportAl(x.getEmployeeId(), 
					x.getLstWkpIdAndPeriod().stream()
					.map(a -> new WorkPlaceIdAndPeriodImportAl(a.getDatePeriod(), a.getWorkplaceId())).collect(Collectors.toList())))
					.collect(Collectors.toList());
		extractService.extractMultiMonthlyResult(cid,
				lstSid,
				mPeriod, 
				lstAnyConID,
				lstWkpIdAndPeriod,
				lstResultCondition,
				lstCheckInfor);
	}

	
}
