/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getsendingperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoResult;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInformation;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPerCondPeriodParam;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPersonalConditionsPeriod;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ExtractTargetEmployeesParam;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ScreenQueryExtractTargetEmployees;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.GetScheduleActualOfShift;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftDataResult;
import nts.uk.screen.at.app.ksu001.getschedulesbyshift.SchedulesbyShiftParam;

/**
 * @author laitv
 * 表示期間を変更する（シフト）
 */
@Stateless
public class ChangePeriodInWorkInfomation {
	
	@Inject
	private EventInfoAndPersonalConditionsPeriod eventInfoAndPersonalCondPeriod;
	@Inject
	private ScreenQueryExtractTargetEmployees extractTargetEmployees;
	@Inject
	private DisplayInWorkInformation displayInWorkInfo;
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	public ChangePeriodInWorkInfoResult getData(ChangePeriodInWorkInfoParam param) {
		
		GeneralDate start = GeneralDate.fromString(param.startDate, DATE_FORMAT);
		GeneralDate end = GeneralDate.fromString(param.endDate, DATE_FORMAT);
		
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

		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(end, targetOrgIdenInfor);
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.getListEmp(param2);
		
		List<String> sids = resultStep2.stream().map(i -> i.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param1 = new EventInfoAndPerCondPeriodParam(
				start, end, sids, targetOrgIdenInfor);
		DataSpecDateAndHolidayDto resultStep1 = eventInfoAndPersonalCondPeriod.getData(param1);
		
		DisplayInWorkInfoParam param4 = new DisplayInWorkInfoParam(sids, start, end, param.getActualData);
		DisplayInWorkInfoResult  resultStep4 = new DisplayInWorkInfoResult();
		resultStep4 = displayInWorkInfo.getDataWorkInfo(param4);
		
		return new ChangePeriodInWorkInfoResult(resultStep2, resultStep1, resultStep4.listWorkScheduleWorkInfor);
	}
}
