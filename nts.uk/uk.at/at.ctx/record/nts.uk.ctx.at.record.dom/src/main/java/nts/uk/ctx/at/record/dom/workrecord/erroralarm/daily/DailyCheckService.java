package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.StatusOfEmployeeAdapterAl;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm.WorkPlaceHistImportAl;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.toppage.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.toppage.AlarmExtractionCondition;

public interface DailyCheckService {
	public void extractDailyCheck(String cid, List<String> lstSid, DatePeriod dPeriod,
			String errorDailyCheckId, List<String> extractConditionWorkRecord,
			List<String> errorDailyCheckCd, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod,
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition,
			List<AlarmListCheckInfor> lstCheckType, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop, List<AlarmEmployeeList> alarmEmployeeLists,
			List<AlarmExtractionCondition> alarmExtractConditions, String alarmCheckConditionCode);
}
