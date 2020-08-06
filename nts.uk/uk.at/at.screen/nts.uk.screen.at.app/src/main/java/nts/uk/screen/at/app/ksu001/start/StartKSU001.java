/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.screen.at.app.ksu001.displayinshift.DisplayInShift;
import nts.uk.screen.at.app.ksu001.displayinshift.DisplayInShiftParam;
import nts.uk.screen.at.app.ksu001.displayinshift.DisplayInShiftResult;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
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
import nts.uk.screen.at.app.ksu001.getshiftpalette.PageInfo;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftPalletUnit;
import nts.uk.screen.at.app.ksu001.getshiftpalette.TargetShiftPalette;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftResult;

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
	@Inject
	private DisplayInWorkInformation displayInWorkInfo;
	@Inject
	private DisplayInShift displayInShift;
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	public StartKSU001Dto getDataStartScreen(StartKSU001Param param) {
		
		
		// step 1 
		DataScreenQueryGetInforDto resultStep1 = getInforOfInitStartup.dataSample();
		
		// step 2 start
		String workplaceId = resultStep1.targetOrgIdenInfor.workplaceId == null ? null
				: resultStep1.targetOrgIdenInfor.workplaceId;
		String workplaceGroupId = resultStep1.targetOrgIdenInfor.workplaceGroupId == null ? null
				: resultStep1.targetOrgIdenInfor.workplaceGroupId;
		GeneralDate startDate = param.startDate == null || param.startDate == "" ? resultStep1.startDate : GeneralDate.fromString(param.startDate, DATE_FORMAT);
		GeneralDate endDate   = param.endDate   == null || param.endDate   == "" ? resultStep1.endDate   : GeneralDate.fromString(param.endDate, DATE_FORMAT);
		
		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(workplaceGroupId == null ? TargetOrganizationUnit.WORKPLACE : TargetOrganizationUnit.WORKPLACE_GROUP,
				workplaceId, workplaceGroupId);
		
		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(endDate, targetOrgIdenInfor);
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.dataSample(param2);
		// step 2 end
		
		// step 3 start
		List<String> listSid = resultStep2.stream().map(mapper -> mapper.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param3 = new EventInfoAndPerCondPeriodParam(startDate, endDate, workplaceId,
				workplaceGroupId, listSid);
		DataSpecDateAndHolidayDto resultStep3 = eventInfoAndPersonalCondPeriod.dataSample(param3);
		// step 3 end

		// data tra ve cua step4 || step 5.2
		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = new ArrayList<>();

		// data tra ve cua step 5.1
		List<PageInfo> listPageInfo = new ArrayList<>();
		TargetShiftPalette targetShiftPalette = null;
		List<ShiftMasterMapWithWorkStyle> shiftMasterWithWorkStyleLst = new ArrayList<>();
		// data cua Grid
		List<WorkScheduleShiftDto> listWorkScheduleShift = new ArrayList<>();
		
		if (param.viewMode.equals("shift")) {
			// step5.1
			DisplayInShiftParam param51 = new DisplayInShiftParam();
			param51.setListSid(listSid);
			param51.setStartDate(startDate);
			param51.setEndDate(endDate);
			param51.setWorkplaceId(workplaceId);
			param51.setWorkplaceGroupId(workplaceGroupId);
			param51.setListShiftMasterNotNeedGetNew(new ArrayList<>());
			param51.setShiftPaletteWantGet(new ShiftPaletteWantGet(param.shiftPalletUnit, param.pageNumber ));
			param51.setGetActualData(param.getActualData);
			
			DisplayInShiftResult resultStep51 =  displayInShift.dataSample(param51);
			listPageInfo = resultStep51.listPageInfo;
			targetShiftPalette = resultStep51.targetShiftPalette;
			shiftMasterWithWorkStyleLst = resultStep51.shiftMasterWithWorkStyleLst;
			listWorkScheduleShift = resultStep51.listWorkScheduleShift;
			
		} else if (param.viewMode.equals("time") || param.viewMode.equals("shortName")) {
			// step4 || step 5.2
			//step 4  || step 5.2 start
			DisplayInWorkInfoParam param4 = new DisplayInWorkInfoParam(listSid, startDate, endDate, false);
			DisplayInWorkInfoResult  resultStep4 = new DisplayInWorkInfoResult();
			resultStep4 = displayInWorkInfo.getData(param4);
			listWorkTypeInfo = resultStep4.listWorkTypeInfo;
			listWorkScheduleWorkInfor = resultStep4.listWorkScheduleWorkInfor;
		}
		
		StartKSU001Dto result = convertData(resultStep1, resultStep2, resultStep3,
				listWorkTypeInfo, listWorkScheduleWorkInfor, 
				listPageInfo,targetShiftPalette,shiftMasterWithWorkStyleLst,listWorkScheduleShift);
		return result;
	}

	private StartKSU001Dto convertData(DataScreenQueryGetInforDto resultStep1,List<EmployeeInformationImport> resultStep2, 
			DataSpecDateAndHolidayDto resultStep3, List<WorkTypeInfomation> listWorkTypeInfo, 
			List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor,
			List<PageInfo> listPageInfo, TargetShiftPalette targetShiftPalette, List<ShiftMasterMapWithWorkStyle> shiftMasterWithWorkStyleLst, List<WorkScheduleShiftDto> listWorkScheduleShift) {
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
		
		//  data tra ve cua step4
		result.setListWorkTypeInfo(listWorkTypeInfo);
		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		// 5.1
		result.setListPageInfo(listPageInfo);
		result.setTargetShiftPalette(targetShiftPalette);
		result.setShiftMasterWithWorkStyleLst(shiftMasterWithWorkStyleLst);
		result.setListWorkScheduleShift(listWorkScheduleShift);
		return result;
	}
}
