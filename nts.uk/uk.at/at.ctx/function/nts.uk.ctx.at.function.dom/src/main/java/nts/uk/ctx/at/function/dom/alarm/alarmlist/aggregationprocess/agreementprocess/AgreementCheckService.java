package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.agreementprocess;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AlarmChkCondAgree36;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult.*;

public interface AgreementCheckService {

	void check(List<AlarmCheckConditionByCategory> agreementErAl, List<PeriodByAlarmCategory> periodAlarms,
			Optional<AgreementOperationSettingImport> agreementSetObj, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop, List<ValueExtractAlarm> result,
			Map<String, Integer> empIdToClosureId, List<Closure> closureList,
			Map<String, EmployeeSearchDto> mapEmployee, List<String> employeeIds);
	
	void countFinishedEmp(Consumer<Integer> counter, List<String> employeeIds);
	public void get36AlarmCheck(String cid, AlarmChkCondAgree36 alarmChkCon36, List<PeriodByAlarmCategory> periodAlarms
			, Consumer<Integer> counter,
			Supplier<Boolean> shouldStop,
			List<WorkPlaceHistImport> getWplByListSidAndPeriod, List<String> employeeIds,
			List<ResultOfEachCondition> lstResultCondition, List<AlarmListCheckInfor> lstCheckInfor,
			List<AlarmEmployeeList> alarmEmployeeList, List<AlarmExtractionCondition> alarmExtractConditions,
			String alarmCheckConditionCode);
}
