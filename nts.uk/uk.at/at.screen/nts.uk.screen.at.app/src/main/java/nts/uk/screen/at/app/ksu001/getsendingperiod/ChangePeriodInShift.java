/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getsendingperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPerCondPeriodParam;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPersonalConditionsPeriod;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ExtractTargetEmployeesParam;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ScreenQueryExtractTargetEmployees;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.GetScheduleActualOfShift;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftDataResult;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftDataResult;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftParam;

/**
 * @author laitv
 * 表示期間を変更する（シフト）
 */
@Stateless
public class ChangePeriodInShift {
	
	@Inject
	private EventInfoAndPersonalConditionsPeriod eventInfoAndPersonalCondPeriod;
	@Inject
	private ScreenQueryExtractTargetEmployees extractTargetEmployees;
	@Inject
	private GetScheduleActualOfShift getSchedulesAndAchievementsByShift;

	
	public ChangePeriodInShiftResult getData(ChangePeriodInShiftParam param) {
		
		// step 1 va step 2 
		TargetOrgIdenInfor targetOrgIdenInfor = null;
		if (param.unit == TargetOrganizationUnit.WORKPLACE.value) {
			targetOrgIdenInfor = new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,
					Optional.of(param.workplaceId),
					Optional.empty());
		}else{
			targetOrgIdenInfor = new TargetOrgIdenInfor(
					TargetOrganizationUnit.WORKPLACE_GROUP,
					Optional.empty(),
					Optional.of(param.workplaceGroupId));
		}

		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(param.endDate, targetOrgIdenInfor);
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.getListEmp(param2);
		
		List<String> sids = resultStep2.stream().map(i -> i.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param1 = new EventInfoAndPerCondPeriodParam(
				param.startDate,param.endDate, sids, targetOrgIdenInfor);
		DataSpecDateAndHolidayDto resultStep1 = eventInfoAndPersonalCondPeriod.getData(param1);
		
		
		
		// step 3
		SchedulesbyShiftDataResult schedulesbyShiftDataResult_New = 
				getSchedulesAndAchievementsByShift.getData(
						param.getListShiftMasterNotNeedGetNew(),
						param.getSids(),
						new DatePeriod(param.getStartDate(), param.getEndDate()),
						param.getCloseDate(),
						param.getActualData,
						targetOrgIdenInfor,
						Optional.ofNullable(param.getPersonalCounterOp()).flatMap(x -> Optional.of(EnumAdaptor.valueOf(x, PersonalCounterCategory.class))),
						Optional.ofNullable(param.getWorkplaceCounterOp()).flatMap(x -> Optional.of(EnumAdaptor.valueOf(x, WorkplaceCounterCategory.class)))
						);
		
		return new ChangePeriodInShiftResult(
				resultStep2,
				resultStep1,
				schedulesbyShiftDataResult_New.getAggreratePersonal(),
				schedulesbyShiftDataResult_New.getAggrerateWorkplace(),
				schedulesbyShiftDataResult_New.getMapShiftMasterWithWorkStyle(),
				schedulesbyShiftDataResult_New.getListWorkScheduleShift());
	}
}
