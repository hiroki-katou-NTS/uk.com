/**
 * 
 */
package nts.uk.screen.at.app.ksu001.changemode;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoResult;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInformation;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.WorkTypeInfomation;
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
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScreenQueryGetInforOfInitStartup;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.TargetOrgIdenInforDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftResult;
import nts.uk.screen.at.app.ksu001.start.DataBasicDto;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Dto;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Param;

/**
 * @author laitv 初期起動 path:
 *         UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正（職場別）.メニュー別OCD.初期起動
 *
 */
@Stateless
public class NextMonthFinder {

	@Inject
	private ScreenQueryGetInforOfInitStartup getInforOfInitStartup;
	@Inject
	private ScreenQueryExtractTargetEmployees extractTargetEmployees;
	@Inject
	private EventInfoAndPersonalConditionsPeriod eventInfoAndPersonalCondPeriod;
	@Inject
	private DisplayInWorkInformation displayInWorkInfo;
	@Inject
	private GetWorkScheduleShift getWorkScheduleShift;

	public StartKSU001Dto getDataStartScreen(StartKSU001Param param) {

		// step 1 start
		GeneralDate startDate = GeneralDate.ymd(2020, 7, 1);
		GeneralDate endDate = GeneralDate.ymd(2020, 7, 31);

		TargetOrgIdenInforDto targetOrgIdenInfor = new TargetOrgIdenInforDto(TargetOrganizationUnit.WORKPLACE.value,
				"dea95de1-a462-4028-ad3a-d68b8f180412", null);

		DisplayInfoOrganization displayInforOrganization = new DisplayInfoOrganization("designation", "code", "name",
				"WorkPlaceName", "genericTerm");

		DataScreenQueryGetInforDto resultStep1 = new DataScreenQueryGetInforDto(startDate, endDate, targetOrgIdenInfor,
				displayInforOrganization);
		// step 1 end

		// step 2 start
		String workplaceId = "dea95de1-a462-4028-ad3a-d68b8f180412";
		String workplaceGroupId = null;
		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(endDate,
				new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE, workplaceId, workplaceGroupId));
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.getListEmp2(param2);
		// step 2 end

		// step 3 start
		List<String> listSid = resultStep2.stream().map(mapper -> mapper.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param3 = new EventInfoAndPerCondPeriodParam(startDate, endDate, workplaceId,
				workplaceGroupId, listSid);
		DataSpecDateAndHolidayDto resultStep3 = eventInfoAndPersonalCondPeriod.get(param3);
		// step 3 end

		// step 4 || step 5.2 start
		DisplayInWorkInfoParam param4 = new DisplayInWorkInfoParam(listSid, startDate, endDate, false);
		DisplayInWorkInfoResult resultStep4 = new DisplayInWorkInfoResult();
		resultStep4 = displayInWorkInfo.getData(param4);
		List<WorkTypeInfomation> listWorkTypeInfo = resultStep4.listWorkTypeInfo;
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = resultStep4.listWorkScheduleWorkInfor;

		// step5.1
		WorkScheduleShiftResult resultStep51 = getWorkScheduleShift.getData(new GetWorkScheduleShiftParam());
		List<WorkScheduleShiftDto> listWorkScheduleShift = resultStep51.listWorkScheduleShift;

		if (param.viewMode == "shift") {
			// step5.1

		} else if (param.viewMode == "time" || param.viewMode == "shortName") {
			// step4 || step 5.2

		}

		StartKSU001Dto result = convertData(resultStep1, resultStep2, resultStep3, listWorkTypeInfo,
				listWorkScheduleWorkInfor, listWorkScheduleShift);
		return result;
	}

	private StartKSU001Dto convertData(DataScreenQueryGetInforDto resultStep1,
			List<EmployeeInformationImport> resultStep2, DataSpecDateAndHolidayDto resultStep3,
			List<WorkTypeInfomation> listWorkTypeInfo, List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor,
			List<WorkScheduleShiftDto> listWorkScheduleShift) {
		StartKSU001Dto result = new StartKSU001Dto();

		// data tra ve cua step1
		DataBasicDto dataBasicDto = new DataBasicDto(resultStep1);
		result.setDataBasicDto(dataBasicDto);

		// data tra ve cua step2
		List<EmployeeInformationDto> listEmpInfo = resultStep2.stream().map(item -> {
			return new EmployeeInformationDto(item.getEmployeeId(), item.getEmployeeCode(),
					item.getBusinessName().toString());
		}).collect(Collectors.toList());
		result.setListEmpInfo(listEmpInfo);

		// data tra ve cua step3
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

		// data tra ve cua step4
		result.setListWorkTypeInfo(listWorkTypeInfo);
		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		// 5.1
		result.setListWorkScheduleShift(listWorkScheduleShift);
		return result;

	}
}
