/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DateInformationDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DisplayControlPersonalCondDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPerCondPeriodParam;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPersonalConditionsPeriod;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.PersonalConditionsDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.EmployeeInformationDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ExtractTargetEmployeesParam;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ScreenQueryExtractTargetEmployees;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.DataScreenQueryGetInforDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.DisplayInforOrganization;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScreenQueryGetInforOfInitStartup;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.TargetOrgIdenInforDto;

/**
 * @author laitv 初期起動 path:
 *         UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正（職場別）.メニュー別OCD.初期起動
 *
 */
@Stateless
public class StartKSU001 {

	@Inject
	private ScreenQueryGetInforOfInitStartup getInforOfInitStartup;
	@Inject
	private ScreenQueryExtractTargetEmployees extractTargetEmployees;
	@Inject
	private EventInfoAndPersonalConditionsPeriod eventInfoAndPersonalCondPeriod;

	public StartKSU001Dto getDataStartScreen(StartKSU001Param param) {
		
		/*
		// step 1 call ScreenQuery
		DataScreenQueryGetInforDto resultStep1 = getInforOfInitStartup.getDataInit();

		// step 2
		String workplaceId = resultStep1.targetOrgIdenInfor.workplaceId == null ? null
				: resultStep1.targetOrgIdenInfor.workplaceId;
		String workplaceGroupId = resultStep1.targetOrgIdenInfor.workplaceGroupId == null ? null
				: resultStep1.targetOrgIdenInfor.workplaceGroupId;
		GeneralDate startDate = resultStep1.startDate;
		GeneralDate endDate = resultStep1.endDate; */
		String workplaceId = "dea95de1-a462-4028-ad3a-d68b8f180412";
		String workplaceGroupId = null;
		GeneralDate startDate = GeneralDate.ymd(2020, 7, 1);
		GeneralDate endDate =  GeneralDate.ymd(2020, 7, 31);
		
		TargetOrgIdenInforDto targetOrgIdenInfor = new TargetOrgIdenInforDto(TargetOrganizationUnit.WORKPLACE.value,
				"dea95de1-a462-4028-ad3a-d68b8f180412", null);

		DisplayInforOrganization displayInforOrganization = new DisplayInforOrganization("designation", "code", "name",
				"showName", "genericTerm");

		DataScreenQueryGetInforDto resultStep1 = new DataScreenQueryGetInforDto(startDate, endDate, targetOrgIdenInfor,
				displayInforOrganization);

		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(endDate, workplaceId, workplaceGroupId);
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.getListEmp(param2);

		// step 3
		List<String> listSid = resultStep2.stream().map(mapper -> mapper.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param3 = new EventInfoAndPerCondPeriodParam(startDate, endDate, workplaceId,
				workplaceGroupId, listSid);
		DataSpecDateAndHolidayDto resultStep3 = eventInfoAndPersonalCondPeriod.get(param3);

		
		if (param.modeDisplay == ModeDisPlay.SHIFT.value) {
			// step5.1

		} else if (param.modeDisplay == ModeDisPlay.DETAIL.value || param.modeDisplay == ModeDisPlay.WORKING.value) {
			// step4 || step 5.2

		}
		
		StartKSU001Dto result = convertData(resultStep1, resultStep2, resultStep3);
		return result;
	}

	private StartKSU001Dto convertData(DataScreenQueryGetInforDto resultStep1,
			List<EmployeeInformationImport> resultStep2, DataSpecDateAndHolidayDto resultStep3) {
		StartKSU001Dto result = new StartKSU001Dto();
		
		//	data tra ve cua step1	
		DataBasicDto dataBasicDto = new DataBasicDto(resultStep1);
		result.setDataBasicDto(dataBasicDto);
		
		//  data tra ve cua step2
		List<EmployeeInformationDto> listEmpInfo = resultStep2.stream().map(item -> {
			return new EmployeeInformationDto(item.getEmployeeId(), item.getEmployeeCode(), item.getBusinessName().toString());
		}).collect(Collectors.toList());
		result.setListEmpInfo(listEmpInfo);
		
		//  data tra ve cua step3
		List<DateInformationDto> listDateInfo = resultStep3.getListDateInfo().stream().map(i -> {
			return new DateInformationDto(i);
		}).collect(Collectors.toList());
		result.setListDateInfo(listDateInfo);
		
		List<PersonalConditionsDto> listPersonalConditions = resultStep3.getListPersonalConditions().stream().map(i -> {
			return new PersonalConditionsDto(i);
		}).collect(Collectors.toList());
		result.setListPersonalConditions(listPersonalConditions);
		
		DisplayControlPersonalCondDto displayControlPersonalCond = resultStep3.optDisplayControlPersonalCond.isPresent()
				? new DisplayControlPersonalCondDto(resultStep3.optDisplayControlPersonalCond.get()) : null;
		result.setDisplayControlPersonalCond(displayControlPersonalCond);
				
		return result;
		
	}
}
