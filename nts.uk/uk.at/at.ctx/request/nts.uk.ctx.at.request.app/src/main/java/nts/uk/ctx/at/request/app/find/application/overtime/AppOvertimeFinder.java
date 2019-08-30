package nts.uk.ctx.at.request.app.find.application.overtime;

/*import java.util.Collections;*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.eclipse.persistence.sdo.helper.extension.OPStack;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AgreeOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeMobDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeBreakDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeFrameDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.PreAppOvertimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.Changeable;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter.MailDestinationCache;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeReference;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeFourProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeInstructInfomation;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeAndSiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.RecordWorkOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexExcessUseSetAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class AppOvertimeFinder {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "0:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	@Inject
	private OvertimeSixProcess overtimeSixProcess;
	@Inject
	private OvertimeFourProcess overtimeFourProcess;
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	@Inject
	private AppOvertimeSettingRepository appOvertimeSettingRepository;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeRepo;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private WithdrawalAppSetRepository withdrawalAppSetRepository;
	
	/**
	 * @param url
	 * @param appDate
	 * @param uiType
	 * @return
	 */
	public OverTimeDto getOvertimeByUIType(String url,String appDate,int uiType,Integer timeStart1,Integer timeEnd1,String reasonContent,List<String> employeeIDs,String employeeID){
		
		OverTimeDto result = new OverTimeDto();
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		if(CollectionUtil.isEmpty(employeeIDs) && employeeID == null){
			 employeeID = AppContexts.user().employeeId();
		}else if(!CollectionUtil.isEmpty(employeeIDs)){
			employeeID = employeeIDs.get(0);
			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(employeeIDs);
			if(!CollectionUtil.isEmpty(employees)){
				List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
				for(EmployeeInfoImport emp : employees){
					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
					employeeOTs.add(employeeOT);
				}
				result.setEmployees(employeeOTs);
			}
		}
		
		int rootAtr = 1;
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, 
				EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class),appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		//hoatt
		result.setRequireAppReasonFlg(appCommonSettingOutput.getApplicationSetting().getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED) ? true : false);
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  == 1 ? false : true);
		result.setSendMailWhenApprovalFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true : false);
		result.setSendMailWhenRegisterFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true : false);
		if(CollectionUtil.isEmpty(employeeIDs) || employeeIDs.size() == 1){
			//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
			ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(companyID, employeeID, EmploymentRootAtr.APPLICATION, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), appCommonSettingOutput.generalDate, "", true);
			//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
			 startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate, ApplicationType.OVER_TIME_APPLICATION.value, approvalRootPattern.getApprovalRootContentImport());
		}
		// 02_残業区分チェック : check loai lam them
		int overtimeAtr = overtimeService.checkOvertimeAtr(url);
		result.setOvertimeAtr(overtimeAtr);
		// 01_初期データ取得
		getData(result,uiType,appDate,companyID,employeeID,appCommonSettingOutput,applicationDto,overtimeAtr,overTimeInputs,preAppOvertimeDto,timeStart1,timeEnd1,reasonContent);
		
		result.setApplication(applicationDto);
		String employeeName = "";
		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
			result.setEmployeeID(applicationDto.getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
			result.setEmployeeID(employeeID);
		}
		result.setEmployeeName(employeeName);

		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value).get();
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
		result.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
		result.setPerformanceExcessAtr(performanceExcessAtr.value);
		return result;
	}
	
	public ColorConfirmResult calculationresultConfirm(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		ColorConfirmResult result = new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDateTime inputDate = GeneralDateTime.now();
		int rootAtr = 1;
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
						employeeID,
						rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		
		// 6.計算処理 : 
		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
		// 06-01_色表示チェック
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(approvalFunctionSetting != null){
			result = commonOvertimeHoliday.checkDisplayColorCF(overtimeHours,
					overtimeInputCaculations,
					prePostAtr,
					inputDate,
					appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT),
					ApplicationType.OVER_TIME_APPLICATION.value,
					employeeID, 
					companyID, 
					approvalFunctionSetting,
					siftCD);
		}
		
		// 計算フラグ=0(client setting)
		
		return result;
	}
	
	public OverTimeDto getCalculationResultMob(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes, int prePostAtr, String appDate, String siftCD, String workTypeCode,
			Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests, boolean displayCaculationTime, boolean isFromStepOne) {

		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDate generalAppDate = appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT);
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
		OverTimeDto result = new OverTimeDto();
		
		int rootAtr = 1;
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(
				companyID, employeeID, rootAtr,
				EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class),
				generalAppDate);
		// 承認ルートを取得する
		MailDestinationCache mailDestCache = this.approvalRootStateAdapter
				.createMailDestinationCache(AppContexts.user().companyId());
		ApprovalRootContentImport_New approvalRootContentImport = this.approvalRootStateAdapter.getApprovalRootContent(
				companyID, employeeID, ApplicationType.OVER_TIME_APPLICATION.value, appCommonSettingOutput.generalDate,
				"", true, mailDestCache);

		int errorFlag = approvalRootContentImport.getErrorFlag().value;
		if (errorFlag == ErrorFlagImport.NO_APPROVER.value) {
			throw new BusinessException("Msg_324");
		} else if (errorFlag == ErrorFlagImport.NO_CONFIRM_PERSON.value) {
			throw new BusinessException("Msg_238");
		} else if (errorFlag == ErrorFlagImport.APPROVER_UP_10.value) {
			throw new BusinessException("Msg_237");
		}

		// 事前事後区分を取得する
		if (appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value) {
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(
					EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class),
					appCommonSettingOutput.generalDate, 0);
			if (prePostAtrJudgment != null) {
				prePostAtr = prePostAtrJudgment.value;
			}
		}

		// 勤務情報から残業時間を計算する
		//List<CaculationTime> caculationTimeHours = new ArrayList<CaculationTime>();
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		PreAppCheckResult preAppCheckResult = new PreAppCheckResult();
		ActualStatusCheckResult actualStatusCheckResult = new ActualStatusCheckResult();
		if (prePostAtr == PrePostAtr.POSTERIOR.value) {
			// 07-01_事前申請状態チェック
			preAppCheckResult = preActualColorCheck.preAppStatusCheck(companyID,
					employeeID, generalAppDate, ApplicationType.OVER_TIME_APPLICATION);
			// 07-02_実績取得・状態チェック
			actualStatusCheckResult = preActualColorCheck.actualStatusCheck(companyID,
					employeeID, generalAppDate, ApplicationType.OVER_TIME_APPLICATION,
					workTypeCode, siftCD, withdrawalAppSet.getOverrideSet(),
					Optional.empty());
		}

		if (displayCaculationTime == false) {
			return null;
		} else {
			UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();// 事前申請超過チェック
			AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();// 実績超過チェック
			// 6.計算処理 :
			List<OvertimeInputCaculation> overtimeInputCaculations = new ArrayList<>();
			if (isFromStepOne) {
				overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, 
						workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
			}			
					
			// caculationTimeHours = this.overtimeSixProcess.getCaculationOvertimeHours(companyID, employeeID, appDate, ApplicationType.OVER_TIME_APPLICATION.value, overtimeHours, overtimeInputCaculations);
			/*caculationTimeHours = commonOvertimeHoliday.preActualExceededCheckMob(companyID, appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
					inputDate, prePostAtr, employeeID, siftCD, overtimeInputCaculations, caculationTimeHours);*/
			//事前申請・実績超過チェック
			List<OvertimeColorCheck> overTimeLst  = overtimeHours.stream().map(item -> OvertimeColorCheck.createFromOverTimeInput(item)).collect(Collectors.toList());
			List<OvertimeColorCheck> bonusTimeLst  = bonusTimes.stream().map(item -> OvertimeColorCheck.createFromOverTimeInput(item)).collect(Collectors.toList());
			overTimeLst.addAll(bonusTimeLst);
			
			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
			PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(preExcessDisplaySetting,
					performanceExcessAtr, ApplicationType.OVER_TIME_APPLICATION, PrePostAtr.values()[prePostAtr],
					withdrawalAppSet.getOverrideSet(), Optional.empty(), overtimeInputCaculations, overTimeLst,
					preAppCheckResult.opAppBefore, preAppCheckResult.beforeAppStatus, actualStatusCheckResult.actualLst,
					actualStatusCheckResult.actualStatus);
			result.setPreActualColorResult(preActualColorResult);			

			result.setPerformanceExcessAtr(performanceExcessAtr.value);
			result.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
		}
		
		// 勤務情報確定後のデータを取得する
		// 社員の労働条件を取得する
		// Optional<WorkingConditionItem> workingConditionItem = this.workingConditionService.findWorkConditionByEmployee(employeeID, baseDate);
		
		// 残業枠を取得
		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);

		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			overtimeInputDto.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue());
			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		result.setOverTimeInputs(overTimeInputs);

		if(appTypeDiscreteSetting.isPresent()){
			result.setTypicalReasonDisplayFlg(false);
			// 申請定型理由を取得
			if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setTypicalReasonDisplayFlg(true);
				List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
						companyID,
						appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg(),
						ApplicationType.OVER_TIME_APPLICATION);
				List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
				for (ApplicationReason applicationReason : applicationReasons) {
					ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
							applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
					applicationReasonDtos.add(applicationReasonDto);
				}
				result.setApplicationReasonDtos(applicationReasonDtos);
			}
		}
		
		if(overtimeRestAppCommonSet.isPresent()){
			result.setDisplayDivergenceReasonForm(false);
			// 乖離定型理由を取得
			if(prePostAtr != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
						.getDivergenceReasonForm(
								companyID,
								EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
								overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(),
								ApplicationType.OVER_TIME_APPLICATION);
				convertToDivergenceReasonDto(divergenceReasons,result);
			}
		}
		return result;
	}
	
	/**
	 * @return
	 */
	public List<CaculationTime> getCaculationValue(List<CaculationTime> overtimeHours,
			List<CaculationTime> bonusTimes,
			int prePostAtr,
			String appDate,
			String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		 
		List<CaculationTime> caculationTimes = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDateTime inputDate = GeneralDateTime.now();
		int rootAtr = 1;
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
						employeeID,
						rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		
		// 6.計算処理 : 
		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, siftCD, workTypeCode, startTime, endTime, startTimeRests, endTimeRests);
		// 06-01_色表示チェック
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(approvalFunctionSetting != null){
			overtimeHours = commonOvertimeHoliday.checkDisplayColor(overtimeHours,
					overtimeInputCaculations,
					prePostAtr,
					inputDate,
					appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT),
					ApplicationType.OVER_TIME_APPLICATION.value,
					employeeID, 
					companyID, 
					approvalFunctionSetting,
					siftCD);
		}
		// 06-02_残業時間を取得
		List<CaculationTime> caculationTimeHours = this.overtimeSixProcess.getCaculationOvertimeHours(companyID, employeeID, appDate, ApplicationType.OVER_TIME_APPLICATION.value,overtimeHours,overtimeInputCaculations);
		caculationTimes.addAll(caculationTimeHours);
		
		// 06-03_加給時間を取得
		List<CaculationTime> caculationTimeBonus= commonOvertimeHoliday.getCaculationBonustime(companyID, employeeID, appDate,  ApplicationType.OVER_TIME_APPLICATION.value,bonusTimes);
		caculationTimes.addAll(caculationTimeBonus);
		
		// 計算フラグ=0(client setting)
		
		return caculationTimes;
	}
	
	/**
	 * @return
	 */
	public PreActualColorResult getCalculateValue(String employeeID, String appDate, Integer prePostAtr, String workTypeCD, String workTimeCD,
			List<CaculationTime> overtimeInputLst, Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests){
		String companyID = AppContexts.user().companyId();
		GeneralDate generalDate = GeneralDate.fromString(appDate, DATE_FORMAT); 
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, 
				ApplicationType.OVER_TIME_APPLICATION, generalDate);
		
		// 6.計算処理 : 
		List<OvertimeInputCaculation> overtimeInputCaculations = commonOvertimeHoliday.calculator(appCommonSettingOutput, appDate, workTimeCD, workTypeCD, startTime, endTime, startTimeRests, endTimeRests);
		List<OvertimeColorCheck> otTimeLst = overtimeInputLst.stream()
				.map(x -> OvertimeColorCheck.createApp(x.getAttendanceID(), x.getFrameNo(), x.getApplicationTime()))
				.collect(Collectors.toList());
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value).get();
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		// 07-01_事前申請状態チェック
		PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.OVER_TIME_APPLICATION);
		// 07-02_実績取得・状態チェック
		ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
				companyID, 
				employeeID, 
				GeneralDate.fromString(appDate, DATE_FORMAT), 
				ApplicationType.OVER_TIME_APPLICATION, 
				workTypeCD, 
				workTimeCD, 
				withdrawalAppSet.getOverrideSet(), 
				Optional.empty());
		// 07_事前申請・実績超過チェック
		PreActualColorResult preActualColorResult =	preActualColorCheck.preActualColorCheck(
				preExcessDisplaySetting, 
				performanceExcessAtr, 
				ApplicationType.OVER_TIME_APPLICATION, 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				withdrawalAppSet.getOverrideSet(), 
				Optional.empty(), 
				overtimeInputCaculations, 
				otTimeLst, 
				preAppCheckResult.opAppBefore, 
				preAppCheckResult.beforeAppStatus, 
				actualStatusCheckResult.actualLst, 
				actualStatusCheckResult.actualStatus);
		return preActualColorResult;
	}

	private List<OvertimeInputCaculation> convertMaptoList(Map<Integer,TimeWithCalculationImport> overTime,TimeWithCalculationImport flexTime,TimeWithCalculationImport midNightTime){
		List<OvertimeInputCaculation> result = new ArrayList<>();
		
		for(Map.Entry<Integer,TimeWithCalculationImport> entry : overTime.entrySet()){
			OvertimeInputCaculation overtimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, entry.getKey(), entry.getValue().getCalTime());
			result.add(overtimeCal);
		}
		OvertimeInputCaculation flexTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 12,(flexTime.getCalTime() == null || flexTime.getCalTime() < 0)? null : flexTime.getCalTime());
		OvertimeInputCaculation midNightTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 11, midNightTime.getCalTime());
		result.add(flexTimeCal);
		result.add(midNightTimeCal);
		return result;
	}
	
	/**
	 * @param appID
	 * @return
	 */
	public OverTimeDto findDetailByAppID(String appID){
		String companyID = AppContexts.user().companyId();
		// 07_勤務種類取得: lay loai di lam 
		// 08_就業時間帯取得(lay loai gio lam viec)
		// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
		// 01-04_加給時間を取得: chua xong
		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, appID);
		if(!opAppOverTime.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppOverTime appOverTime = opAppOverTime.get();
		Integer startTime = appOverTime.getWorkClockFrom1();
		Integer endTime = appOverTime.getWorkClockTo1();
		OverTimeDto overTimeDto = OverTimeDto.fromDomain(appOverTime);
		String employeeName = employeeAdapter.getEmployeeName(appOverTime.getApplication().getEmployeeID());
		overTimeDto.setEmployeeName(employeeName);
		overTimeDto.setEnteredPersonName(appOverTime.getApplication().getEnteredPersonID() == null ? null : employeeAdapter.getEmployeeName(appOverTime.getApplication().getEnteredPersonID()));
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				appOverTime.getApplication().getEmployeeID(),
				1, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), appOverTime.getApplication().getAppDate());
		//hoatt
		overTimeDto.setRequireAppReasonFlg(appCommonSettingOutput.getApplicationSetting().getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED) ? true : false);
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(approvalFunctionSetting != null){
			overTimeDto.setEnableOvertimeInput(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
			// 時刻計算利用チェック
			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
				overTimeDto.setDisplayCaculationTime(true);
				// 07_勤務種類取得: lay loai di lam 
				String workTypeCD = appOverTime.getWorkTypeCode() == null ? "" : appOverTime.getWorkTypeCode().v();
				WorkType workType = workTypeRepository.findNoAbolishByPK(companyID, workTypeCD).orElse(null);
				String workTypeName = "" ;
				if (workType != null) {
					workTypeName = workType.getName().v();
				}
				overTimeDto.setWorkType(new WorkTypeOvertime(workTypeCD, workTypeName));
				List<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
				List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, appOverTime.getApplication().getEmployeeID(),approvalFunctionSetting,appEmploymentWorkType);
				
				List<String> workTypeCodes = new ArrayList<>();
				for(WorkTypeOvertime workTypeOvertime : workTypeOvertimes){
					workTypeCodes.add(workTypeOvertime.getWorkTypeCode());
				}
				overTimeDto.setWorkTypes(workTypeCodes);
				
				// 08_就業時間帯取得(lay loai gio lam viec) 
				List<SiftType> siftTypes = overtimeService.getSiftType(companyID, appOverTime.getApplication().getEmployeeID(), approvalFunctionSetting,appOverTime.getApplication().getAppDate());
				List<String> siftCodes = new ArrayList<>();
				for(SiftType siftType : siftTypes){
					siftCodes.add(siftType.getSiftCode());
				}
				overTimeDto.setSiftTypes(siftCodes);
				
				String workTimeCD = appOverTime.getSiftCode() == null ? "" : appOverTime.getSiftCode().v();
				String workTimeName = null;
				WorkTimeSetting workTime = workTimeRepository.findByCode(companyID, workTimeCD).orElse(null);
				if (workTime != null) {
					workTimeName = workTime.getWorkTimeDisplayName().getWorkTimeName().v();
				}
				
				overTimeDto.setSiftType(new SiftType(workTimeCD, workTimeName));
				
				// 表示対象の就業時間帯を取得する
				Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyID, workTimeCD);
				if (optFindByCode.isPresent()) {
					overTimeDto.setWorktimeStart(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v());
					overTimeDto.setWorktimeEnd(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v());
				}
				
				// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
				boolean displayRestTime = commonOvertimeHoliday.getRestTime(
						companyID,
						approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
						approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
						ApplicationType.OVER_TIME_APPLICATION);
				overTimeDto.setDisplayRestTime(displayRestTime);
				if(displayRestTime) {
					// 休憩時間帯を取得する
					Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
					Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
					List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID,workTypeCD, workTimeCD, opStartTime, opEndTime);
					List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
						DeductionTimeDto dto = new DeductionTimeDto();
						domain.saveToMemento(dto);
						return dto;
					}).collect(Collectors.toList());
					overTimeDto.setTimezones(timeZones);
				}
			}else{
				overTimeDto.setDisplayCaculationTime(false);
			}
		}
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
		if(appTypeDiscreteSetting.isPresent()){
			// 01-05_申請定型理由を取得
			if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
				overTimeDto.setTypicalReasonDisplayFlg(true);
				List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
						companyID,
						appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg(),
						ApplicationType.OVER_TIME_APPLICATION);
				List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
				for (ApplicationReason applicationReason : applicationReasons) {
					ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
							applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
					applicationReasonDtos.add(applicationReasonDto);
				}
				overTimeDto.setApplicationReasonDtos(applicationReasonDtos);
			}else{
				overTimeDto.setTypicalReasonDisplayFlg(false);
			}
			//01-06_申請理由を取得
			overTimeDto.setDisplayAppReasonContentFlg(otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.get().getDisplayReasonFlg()));
		}
		if(overtimeRestAppCommonSet.isPresent()){
			//01-08_乖離定型理由を取得
			if(overTimeDto.getApplication().getPrePostAtr() != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				overTimeDto.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
						.getDivergenceReasonForm(
								companyID,
								EnumAdaptor.valueOf(overTimeDto.getApplication().getPrePostAtr(), PrePostAtr.class),
								overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(),
								ApplicationType.OVER_TIME_APPLICATION);
				convertToDivergenceReasonDto(divergenceReasons,overTimeDto);
			}else{
				overTimeDto.setDisplayDivergenceReasonForm(false);
			}
			//時間外表示区分
			overTimeDto.setExtratimeDisplayFlag(overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
			//01-07_乖離理由を取得
			overTimeDto.setDisplayDivergenceReasonInput(
					commonOvertimeHoliday.displayDivergenceReasonInput(
							EnumAdaptor.valueOf(overTimeDto.getApplication().getPrePostAtr(), PrePostAtr.class), 
							overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr()));
			AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
			overTimeDto.setPerformanceExcessAtr(performanceExcessAtr.value);
		}
		List<OvertimeInputDto> overTimeInputs = new ArrayList<>();
		// 01-03_残業枠を取得: chua xong
		overTimeDto.setAppOvertimeNightFlg(appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value);
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(appOverTime.getOverTimeAtr().value,companyID);
		
		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			overtimeInputDto.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue());
			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		OvertimeInputDto overtimeInputShiftNight = new OvertimeInputDto();
		overtimeInputShiftNight.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
		overtimeInputShiftNight.setFrameNo(11);
		overTimeInputs.add(overtimeInputShiftNight);
		
		OvertimeInputDto overtimeInputFlexExessTime = new OvertimeInputDto();
		overtimeInputFlexExessTime.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
		overtimeInputFlexExessTime.setFrameNo(12);
		overTimeInputs.add(overtimeInputFlexExessTime);
		
		// lay breakTime
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.BREAKTIME.value);
			overtimeInputDto.setFrameNo(breaktimeFrame.getWorkdayoffFrNo().v().intValueExact());
			overtimeInputDto.setFrameName(breaktimeFrame.getWorkdayoffFrName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		
		
		// 01-04_加給時間を取得: chua xong
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				overTimeDto.setDisplayBonusTime(true);
				List<BonusPayTimeItem> bonusPayTimeItems= this.commonOvertimeHoliday.getBonusTime(
						companyID,
						appOverTime.getApplication().getEmployeeID(),
						appOverTime.getApplication().getAppDate(),
						overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr());
				for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
					OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
					overtimeInputDto.setAttendanceID(AttendanceType.BONUSPAYTIME.value);
					overtimeInputDto.setFrameNo(bonusPayTimeItem.getId());
					overtimeInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
					overtimeInputDto.setTimeItemTypeAtr(bonusPayTimeItem.getTimeItemTypeAtr().value);
					overTimeInputs.add(overtimeInputDto);
				}
			}else{
				overTimeDto.setDisplayBonusTime(false);
			}
		}
		List<Integer> restStartTimes =  new ArrayList<Integer>();
		List<Integer> restEndTimes =  new ArrayList<Integer>();
		for(int i = 1; i < 11; i++){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.RESTTIME.value);
			overtimeInputDto.setFrameNo(i);
			overtimeInputDto.setFrameName(Integer.toString(i));
			overtimeInputDto.setTimeItemTypeAtr(0);
			overTimeInputs.add(overtimeInputDto);
		}
		overTimeDto.getOverTimeInputs().forEach(item -> {
			overTimeInputs.stream().filter(x -> 
				(x.getAttendanceID()==item.getAttendanceID())
				&& (x.getFrameNo()==item.getFrameNo())
				&& (x.getTimeItemTypeAtr()==item.getTimeItemTypeAtr())
					).findAny().ifPresent(value -> {
						value.setStartTime(item.getStartTime());
						value.setEndTime(item.getEndTime());
						value.setApplicationTime(item.getApplicationTime());
					});;
		});
		List<OvertimeInputDto> overtimeRestTimes = overTimeInputs.stream().filter(x -> x.getAttendanceID() == AttendanceType.RESTTIME.value).collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(overtimeRestTimes)){
			restStartTimes = overtimeRestTimes.stream().filter(x -> x.getStartTime() != null).map(x -> x.getStartTime())
					.collect(Collectors.toList());
			restEndTimes = overtimeRestTimes.stream().filter(x -> x.getEndTime() != null).map(x -> x.getEndTime())
					.collect(Collectors.toList());
		}
		overTimeDto.setOverTimeInputs(overTimeInputs);
		
		// xu li hien thi du lieu xin truoc
				if(overtimeRestAppCommonSet.isPresent()){
					// hien thi du lieu thuc te
					overTimeDto.setReferencePanelFlg(false);
					if(overTimeDto.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
						overTimeDto.setReferencePanelFlg(true);
					}
					// hien thi don xin truoc
					overTimeDto.setAllPreAppPanelFlg(false);
					if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value && overTimeDto.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
						overTimeDto.setAllPreAppPanelFlg(true);
					}
				}
		//01-09_事前申請を取得
				if(overTimeDto.isAllPreAppPanelFlg()){
					if(overTimeDto.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value ){
						AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
								appOverTime.getApplication().getEmployeeID(),
								appOverTime.getApplication().getPrePostAtr(),
								overtimeRestAppCommonSet.get().getPreDisplayAtr(), 
								appOverTime.getApplication().getAppDate(),
								ApplicationType.OVER_TIME_APPLICATION);
						if(appOvertime != null){
							overTimeDto.setPreAppPanelFlg(true);
							PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
							convertOverTimeDto(companyID,preAppOvertimeDto,overTimeDto,appOvertime);
						}else{
							overTimeDto.setPreAppPanelFlg(false);
						}
						
					}
				}
				
		//xu li tinh toan
		List<OvertimeInputDto> overtimeHours = overTimeInputs.stream().filter(x -> x.getAttendanceID() == AttendanceType.NORMALOVERTIME.value).collect(Collectors.toList());
		checkColorCaculationForUIB(overtimeHours
				,overTimeDto.getApplication().getPrePostAtr()
				,overTimeDto.getApplication().getApplicationDate()
				,overTimeDto.getApplication().getInputDate()
				,overTimeDto.getSiftType() == null ? "" : overTimeDto.getSiftType().getSiftCode()
				,overTimeDto.getWorkType() == null ? "" : overTimeDto.getWorkType().getWorkTypeCode()
				,overTimeDto.getWorkClockFrom1()
				,overTimeDto.getWorkClockTo1()
				,restStartTimes,restEndTimes);
		for(OvertimeInputDto overtime : overtimeHours){
			for(OvertimeInputDto overtimeHour : overTimeDto.getOverTimeInputs()){
				if(overtime.getAttendanceID() == overtimeHour.getAttendanceID()){
					if(overtime.getFrameNo() == overtimeHour.getFrameNo()){
						overtimeHour.setErrorCode(overtime.getErrorCode());
					}
				}
			}
		}
		
//		List<OvertimeWorkFrame> otFrames = iOvertimePreProcess.getOvertimeHours(appOverTime.getOverTimeAtr().value,companyID);
		// dùng cho xử lí tính toán
		List<CaculationTime> overTimeHours = new ArrayList<>(); 
		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			CaculationTime cal = new CaculationTime();
			cal.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			cal.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValueExact());
			cal.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
			overTimeHours.add(cal);
		}
		for(int i = 11; i<= 12;i++){
			CaculationTime caculationTime = new CaculationTime();
			caculationTime.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			caculationTime.setFrameNo(i);
			overTimeHours.add(caculationTime);
		}
		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
		if(appOverTime.getApplication().getPrePostAtr() == PrePostAtr.POSTERIOR) {
			appOvertimeReference.setAppDateRefer(appOverTime.getApplication().getAppDate().toString(DATE_FORMAT));
			List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
			WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
			// 07-02_実績取得・状態チェック
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
					companyID, 
					appOverTime.getApplication().getEmployeeID(), 
					appOverTime.getApplication().getAppDate(), 
					appOverTime.getApplication().getAppType(), 
					appOverTime.getWorkTypeCode() == null ? null : appOverTime.getWorkTypeCode().v(), 
					appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v(), 
					withdrawalAppSet.getOverrideSet(), 
					Optional.empty());
			for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
				overTimeInputsRefer.add(CaculationTime.builder()
						.attendanceID(1)
						.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
						.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
						.build());
			}
			if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
			} else {
				appOvertimeReference.setWorkTypeRefer(
						new WorkTypeOvertime(actualStatusCheckResult.workType, 
								workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
				appOvertimeReference.setSiftTypeRefer(
						new SiftType(actualStatusCheckResult.workTime, 
								workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
				appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
				for(CaculationTime caculationTime : overTimeInputsRefer) {
					caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
							.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
							.findAny().map(y -> y.actualTime).orElse(null));
				}
				appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
			}
		}
		overTimeDto.setAppOvertimeReference(appOvertimeReference);
		
		// display flex
		if(appOvertimeSettingRepository.getAppOver().isPresent()){
			if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.NOTDISPLAY)){
				overTimeDto.setFlexFLag(false);
			}else if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.DISPLAY)){
				GeneralDate baseDate = appCommonSettingOutput.generalDate;
				Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(appOverTime.getApplication().getEmployeeID(),baseDate);
				if(personalLablorCodition.isPresent()){
					if(personalLablorCodition.get().getLaborSystem().isFlexTimeWork()){
						overTimeDto.setFlexFLag(true);
					}else{
						overTimeDto.setFlexFLag(false);
					}
				}
			}else{
				overTimeDto.setFlexFLag(true);
			}
			//ドメインモデル「残業申請設定」.勤種変更可否フラグがtrueの場合
			overTimeDto.setWorkTypeChangeFlg(false);
			if(appOvertimeSettingRepository.getAppOver().get().getWorkTypeChangeFlag().equals(Changeable.CHANGEABLE)){
				overTimeDto.setWorkTypeChangeFlg(true);
			}
		}
		Optional<OvertimeRestAppCommonSetting> otRestAppCommonSet = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		if(otRestAppCommonSet.isPresent()&&(otRestAppCommonSet.get().getExtratimeDisplayAtr()==UseAtr.USE)){
			Map<String, AppOverTime> appOverTimeDetailMap = overtimeRepository.getListAppOvertimeFrame(companyID, Arrays.asList(appID));
			if(!appOverTimeDetailMap.isEmpty()){
				Optional<AppOvertimeDetail> appOvertimeDetail = appOverTimeDetailMap.entrySet().stream().findFirst().get().getValue().getAppOvertimeDetail();
				overTimeDto.setAppOvertimeDetailDto(AppOvertimeDetailDto.fromDomain(appOvertimeDetail));
				if(!appOvertimeDetail.isPresent()){
					overTimeDto.setAppOvertimeDetailStatus(null);
				} else {
					overTimeDto.setAppOvertimeDetailStatus(overtimeService.getTime36Detail(appOvertimeDetail.get()));
				}
			}
		}
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.get().getPerformanceExcessAtr();
		overTimeDto.setPreExcessDisplaySetting(preExcessDisplaySetting.value);
		overTimeDto.setPerformanceExcessAtr(performanceExcessAtr.value);
		return overTimeDto;
	} 
	public List<OvertimeInputDto> checkColorCaculationForUIB(List<OvertimeInputDto> overtimeHours,int prePostAtr,String appDate,String inputDate,String siftCD,
			String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests){
		
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// 6.計算処理 : 
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(employeeID,
				GeneralDate.fromString(appDate, DATE_FORMAT),
				workTypeCode, siftCD, startTime, endTime, startTimeRests , endTimeRests);
		Map<Integer,TimeWithCalculationImport> overTime = dailyAttendanceTimeCaculationImport.getOverTime();
		List<OvertimeInputCaculation> overtimeInputCaculations = convertMaptoList(overTime,dailyAttendanceTimeCaculationImport.getFlexTime(),dailyAttendanceTimeCaculationImport.getMidNightTime());
		List<CaculationTime> overTimeInputs = new ArrayList<>();
		for(OvertimeInputDto overtime : overtimeHours){
			CaculationTime calcu = new CaculationTime();
			calcu.setAttendanceID(overtime.getAttendanceID());
			calcu.setFrameNo(overtime.getFrameNo());
			calcu.setApplicationTime(overtime.getApplicationTime());
			overTimeInputs.add(calcu);
		}
		
		overTimeInputs = this.overtimeFourProcess.checkDisplayColor(overTimeInputs, overtimeInputCaculations, prePostAtr, inputDate == null ? null : GeneralDateTime.fromString(inputDate, DATE_TIME_FORMAT), appDate == null ? null :GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION.value, employeeID, companyID);
		for(OvertimeInputDto overtime : overtimeHours){
			for(CaculationTime calculation : overTimeInputs){
				if(overtime.getFrameNo() == calculation.getFrameNo()){
					overtime.setErrorCode(calculation.getErrorCode());
				}
			}
		}
		return overtimeHours;
	}
	
	/**
	 * @param appDate
	 * @param prePostAtr
	 * @return
	 */
	public OverTimeDto findByChangeAppDate(String appDate,int prePostAtr,String siftCD, List<CaculationTime> overtimeHours,String workTypeCode,Integer startTime,Integer endTime,List<Integer> startTimeRests,List<Integer> endTimeRests,int overtimeAtr, String changeEmployee){
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		OverTimeDto result = new OverTimeDto();
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		// 申請日を変更する : chưa xử lí(Hung)
		
		// 01-01_残業通知情報を取得
		int rootAtr = 1;
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class),appDate == null? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		result.setDisplayOvertimeInstructInforFlg(overtimeInstructInfomation.isDisplayOvertimeInstructInforFlg());
		result.setOvertimeInstructInformation(overtimeInstructInfomation.getOvertimeInstructInfomation());
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  == 1 ? false : true);
		result.setSendMailWhenApprovalFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true : false);
		result.setSendMailWhenRegisterFlg(appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true : false);
		applicationDto.setPrePostAtr(prePostAtr);
		result.setApplication(applicationDto);
		// 01-09_事前申請を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			// hien thi du lieu thuc te
			result.setReferencePanelFlg(false);
			if(result.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
				result.setReferencePanelFlg(true);
			}
			// hien thi don xin truoc
			result.setAllPreAppPanelFlg(false);
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value && result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
				result.setAllPreAppPanelFlg(true);
			}
		}
		//01-09_事前申請を取得
		if(result.isAllPreAppPanelFlg()){
			if(prePostAtr  == PrePostAtr.POSTERIOR.value ){
				AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
						employeeID,
						EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
						overtimeRestAppCommonSet.get().getPreDisplayAtr(), 
						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
						ApplicationType.OVER_TIME_APPLICATION);
				if(appOvertime != null){
					result.setPreAppPanelFlg(true);
					convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
				}else{
					result.setPreAppPanelFlg(false);
				}
				
			}
		}
		
		
		// ドメインモデル「申請表示設定」．事前事後区分表示をチェックする
		result.setDisplayPrePostFlg(AppDisplayAtr.DISPLAY.value);
		if(appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value){
			result.setDisplayPrePostFlg(AppDisplayAtr.NOTDISPLAY.value);
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT),overtimeAtr);
			if(prePostAtrJudgment != null){
				prePostAtr = prePostAtrJudgment.value;
				applicationDto.setPrePostAtr(prePostAtr);
			}
		}
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting". Check base date of approval route )
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
			if(approvalFunctionSetting != null){
				// 時刻計算利用チェック
				if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
					result.setDisplayCaculationTime(true);
					List<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
					// 07_勤務種類取得: lay loai di lam 
					List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, employeeID,approvalFunctionSetting,appEmploymentWorkType);
					/*if(!CollectionUtil.isEmpty(workTypeOvertimes)){
						result.setWorkType(workTypeOvertimes.get(0));
					}*/
					List<String> workTypeCodes = new ArrayList<>();
					for(WorkTypeOvertime workTypeOvertime : workTypeOvertimes){
						workTypeCodes.add(workTypeOvertime.getWorkTypeCode());
					}
					result.setWorkTypes(workTypeCodes);
					// 08_就業時間帯取得(lay loai gio lam viec) 
					List<SiftType> siftTypes = overtimeService.getSiftType(companyID, employeeID, approvalFunctionSetting,GeneralDate.fromString(appDate, "yyyy/MM/dd"));
					List<String> siftCodes = new ArrayList<>();
					for(SiftType siftType : siftTypes){
						siftCodes.add(siftType.getSiftCode());
					}
					result.setSiftTypes(siftCodes);
					/*if(!CollectionUtil.isEmpty(siftTypes)){
						result.setSiftType(siftTypes.get(0));
					}*/
					
					
					// 09_勤務種類就業時間帯の初期選択をセットする
					WorkTypeAndSiftType workTypeAndSiftType = overtimeService.getWorkTypeAndSiftTypeByPersonCon(companyID, employeeID, 
							Strings.isBlank(appDate) ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), 
							workTypeOvertimes, siftTypes);
					result.setWorkType(workTypeAndSiftType.getWorkType());
					result.setSiftType(workTypeAndSiftType.getSiftType());
					
					// 表示対象の就業時間帯を取得する
					if (null != workTypeAndSiftType.getSiftType()) {
						Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyID, workTypeAndSiftType.getSiftType().getSiftCode());
						if(optFindByCode.isPresent()){
							result.setWorktimeStart(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v());
							result.setWorktimeEnd(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v());
						}
					}
					// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
					boolean displayRestTime = commonOvertimeHoliday.getRestTime(
							companyID,
							approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
							approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
							ApplicationType.OVER_TIME_APPLICATION);
					result.setDisplayRestTime(displayRestTime);
					if(displayRestTime) {
						// 休憩時間帯を取得する
						Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
						Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
						List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID, result.getWorkType().getWorkTypeCode(), result.getSiftType().getSiftCode(), opStartTime, opEndTime);
						List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
							DeductionTimeDto dto = new DeductionTimeDto();
							domain.saveToMemento(dto);
							return dto;
						}).collect(Collectors.toList());
						result.setTimezones(timeZones);
					}
					// 01-18_実績の内容を表示し直す : chưa xử lí
					AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
					WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
					ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
							.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, 
									result.getWorkType() == null ? null : result.getWorkType().getWorkTypeCode(), 
									result.getSiftType() ==  null ? null : result.getSiftType().getSiftCode(), 
									withdrawalAppSet.getOverrideSet(), Optional.empty());
					appOvertimeReference.setAppDateRefer(appDate);
					List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
					List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
					for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
						overTimeInputsRefer.add(CaculationTime.builder()
								.attendanceID(1)
								.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
								.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
								.build());
					}
					if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
						appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
						result.setAppOvertimeReference(appOvertimeReference);
					} else {
						appOvertimeReference.setWorkTypeRefer(
								new WorkTypeOvertime(actualStatusCheckResult.workType, 
										workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
						appOvertimeReference.setSiftTypeRefer(
								new SiftType(actualStatusCheckResult.workTime, 
										workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
						appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
						for(CaculationTime caculationTime : overTimeInputsRefer) {
							caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
									.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
									.findAny().map(y -> y.actualTime).orElse(null));
						}
						appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
						appOvertimeReference.setOverTimeShiftNightRefer(actualStatusCheckResult.actualLst.stream()
									.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
									.findAny().map(y -> y.actualTime).orElse(null));
						appOvertimeReference.setFlexExessTimeRefer(actualStatusCheckResult.actualLst.stream()
									.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
									.findAny().map(y -> y.actualTime).orElse(null));
						result.setAppOvertimeReference(appOvertimeReference);
					}
				}else{
					result.setDisplayCaculationTime(false);
				}
			}
			if(approvalFunctionSetting != null){
				if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
					result.setDisplayCaculationTime(true);
					// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
					RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, employeeID, changeEmployee, appDate,
							approvalFunctionSetting,result.getSiftType() == null ? siftCD : result.getSiftType().getSiftCode(), true);
					result.setDisplayCaculationTime(BooleanUtils.toBoolean(recordWorkOutput.getRecordWorkDisplay().value));
					result.setWorkClockFrom1(recordWorkOutput.getStartTime1());
					result.setWorkClockFrom2(recordWorkOutput.getStartTime2());
					result.setWorkClockTo1(recordWorkOutput.getEndTime1());
					result.setWorkClockTo2(recordWorkOutput.getEndTime2());
				}else{
					result.setDisplayCaculationTime(false);
				}
			}
		String employeeName = "";
		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
		}
		result.setEmployeeName(employeeName);
		
		return result;
		
	}
	
	/**
	 * @param result
	 * @param uiType
	 * @param appDate
	 * @param companyID
	 * @param employeeID
	 * @param appCommonSettingOutput
	 * @param applicationDto
	 * @param overtimeAtr
	 * @param overTimeInputs
	 */
	private void getData(OverTimeDto result,int uiType,String appDate,String companyID,String employeeID,
			AppCommonSettingOutput appCommonSettingOutput,ApplicationDto_New applicationDto,int overtimeAtr,
			List<OvertimeInputDto> overTimeInputs,PreAppOvertimeDto preAppOvertimeDto,Integer startTime1,Integer endTime1,String reasonContent){
		//申請日付を取得 : lay thong tin lam them
		applicationDto.setApplicationDate(appDate);
		// 01-01_残業通知情報を取得
		OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		result.setDisplayOvertimeInstructInforFlg(overtimeInstructInfomation.isDisplayOvertimeInstructInforFlg());
		result.setOvertimeInstructInformation(overtimeInstructInfomation.getOvertimeInstructInfomation());
		//01-02_時間外労働を取得: lay lao dong ngoai thoi gian
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday
				.getAgreementTime(companyID, employeeID, ApplicationType.OVER_TIME_APPLICATION);
		if(opAgreeOverTimeOutput.isPresent()){
			result.setAgreementTimeDto(AgreeOverTimeDto.fromDomain(opAgreeOverTimeOutput.get()));
		}
		Optional<ApplicationSetting> opApplicationSetting = applicationSettingRepository.getApplicationSettingByComID(companyID);
		// 01-13_事前事後区分を取得
		DisplayPrePost displayPrePost =	commonOvertimeHoliday.getDisplayPrePost(
				companyID, 
				ApplicationType.OVER_TIME_APPLICATION,
				uiType,
				OverTimeAtr.ALL,
				appDate == null ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT),
				opApplicationSetting.get().getDisplayPrePostFlg());
		result.setDisplayPrePostFlg(displayPrePost.getDisplayPrePostFlg());
		applicationDto.setPrePostAtr(displayPrePost.getPrePostAtr());
		result.setApplication(applicationDto);
		result.setPrePostCanChangeFlg(displayPrePost.isPrePostCanChangeFlg());
				
		//String workplaceID = employeeAdapter.getWorkplaceId(companyID, employeeID, GeneralDate.today());
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if(approvalFunctionSetting != null){
			result.setEnableOvertimeInput(approvalFunctionSetting.getApplicationDetailSetting().get().getTimeInputUse().value==1?true:false);
			// 時刻計算利用チェック
			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
				result.setDisplayCaculationTime(true);
				
				List<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
				// 07_勤務種類取得: lay loai di lam 
				List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, employeeID,approvalFunctionSetting,appEmploymentWorkType);
				
				List<String> workTypeCodes = new ArrayList<>();
				for(WorkTypeOvertime workTypeOvertime : workTypeOvertimes){
					workTypeCodes.add(workTypeOvertime.getWorkTypeCode());
				}
				result.setWorkTypes(workTypeCodes);
				GeneralDate baseDate = appCommonSettingOutput.generalDate;
				// 08_就業時間帯取得(lay loai gio lam viec) 
				List<SiftType> siftTypes = overtimeService.getSiftType(companyID, employeeID, approvalFunctionSetting,baseDate);
				List<String> siftCodes = new ArrayList<>();
				for(SiftType siftType : siftTypes){
					siftCodes.add(siftType.getSiftCode());
				}
				result.setSiftTypes(siftCodes);
				
				// 09_勤務種類就業時間帯の初期選択をセットする
				WorkTypeAndSiftType workTypeAndSiftType = overtimeService.getWorkTypeAndSiftTypeByPersonCon(companyID, employeeID, 
						Strings.isBlank(appDate) ? null : GeneralDate.fromString(appDate, "yyyy/MM/dd"), workTypeOvertimes, siftTypes);
				result.setWorkType(workTypeAndSiftType.getWorkType());
				result.setSiftType(workTypeAndSiftType.getSiftType());
				
				// 表示対象の就業時間帯を取得する
				if (null != workTypeAndSiftType.getSiftType()) {
					Optional<PredetemineTimeSetting> optFindByCode = predetemineTimeRepo.findByWorkTimeCode(companyID, workTypeAndSiftType.getSiftType().getSiftCode());
					if(optFindByCode.isPresent()){
						result.setWorktimeStart(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().v());
						result.setWorktimeEnd(optFindByCode.get().getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().v());
					}
				}
				
				// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
				RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, employeeID, null, appDate,
						approvalFunctionSetting,result.getSiftType() == null? "" :result.getSiftType().getSiftCode(), true);
				result.setDisplayCaculationTime(BooleanUtils.toBoolean(recordWorkOutput.getRecordWorkDisplay().value));
				result.setWorkClockFrom1(recordWorkOutput.getStartTime1());
				result.setWorkClockFrom2(recordWorkOutput.getStartTime2());
				result.setWorkClockTo1(recordWorkOutput.getEndTime1());
				result.setWorkClockTo2(recordWorkOutput.getEndTime2());
				if(startTime1 != null){
					result.setWorkClockFrom1(startTime1);
				}
				if(endTime1 != null){
					result.setWorkClockTo1(endTime1);
				}
				
				// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
				boolean displayRestTime = commonOvertimeHoliday.getRestTime(
						companyID,
						approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
						approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
						ApplicationType.OVER_TIME_APPLICATION);
				result.setDisplayRestTime(displayRestTime);
				if(displayRestTime) {
					// 休憩時間帯を取得する
					Optional<TimeWithDayAttr> opStartTime = startTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime1)); 
					Optional<TimeWithDayAttr> opEndTime = endTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime1)); 
					List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID, result.getWorkType().getWorkTypeCode(), result.getSiftType().getSiftCode(), opStartTime, opEndTime);
					List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
						DeductionTimeDto dto = new DeductionTimeDto();
						domain.saveToMemento(dto);
						return dto;
					}).collect(Collectors.toList());
					result.setTimezones(timeZones);
				}
			}else{
				result.setDisplayCaculationTime(false);
			}
		}
		
		// 01-03_残業枠を取得: chua xong
		result.setAppOvertimeNightFlg(appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value);
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(overtimeAtr,companyID);
		// dùng cho xử lí tính toán
		List<CaculationTime> overTimeHours = new ArrayList<>(); 
		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			CaculationTime cal = new CaculationTime();
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			overtimeInputDto.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValueExact());
			overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
			cal.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			cal.setFrameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValueExact());
			cal.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
			overTimeHours.add(cal);
			overTimeInputs.add(overtimeInputDto);
		}
		for(int i = 11; i<= 12;i++){
			CaculationTime caculationTime = new CaculationTime();
			caculationTime.setAttendanceID(AttendanceType.NORMALOVERTIME.value);
			caculationTime.setFrameNo(i);
			overTimeHours.add(caculationTime);
		}
		
		// lay breakTime
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
			OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
			overtimeInputDto.setAttendanceID(AttendanceType.BREAKTIME.value);
			overtimeInputDto.setFrameNo(breaktimeFrame.getWorkdayoffFrNo().v().intValueExact());
			overtimeInputDto.setFrameName(breaktimeFrame.getWorkdayoffFrName().toString());
			overTimeInputs.add(overtimeInputDto);
		}
		
		
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			//時間外表示区分
			result.setExtratimeDisplayFlag(overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
		}
		// 01-04_加給時間を取得
		// dùng cho xử lí tính toán
		List<CaculationTime> bonusTimes = new ArrayList<>();
		if(overtimeRestAppCommonSet.isPresent()){
			result.setDisplayBonusTime(false);
			if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				result.setDisplayBonusTime(true);
				List<BonusPayTimeItem> bonusPayTimeItems= this.commonOvertimeHoliday
						.getBonusTime(companyID, employeeID, 
								appDate == null ? GeneralDate.today() : GeneralDate.fromString(appDate, DATE_FORMAT), 
								overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr());
				for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
					CaculationTime cal = new CaculationTime();
					OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
					overtimeInputDto.setAttendanceID(AttendanceType.BONUSPAYTIME.value);
					overtimeInputDto.setFrameNo(bonusPayTimeItem.getId());
					overtimeInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
					overtimeInputDto.setTimeItemTypeAtr(bonusPayTimeItem.getTimeItemTypeAtr().value);
					overTimeInputs.add(overtimeInputDto);
					cal.setAttendanceID(AttendanceType.BONUSPAYTIME.value);
					cal.setFrameNo(bonusPayTimeItem.getId());
					cal.setTimeItemTypeAtr(bonusPayTimeItem.getTimeItemTypeAtr().value);
					cal.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
					bonusTimes.add(cal);
				}
			}
		}
		result.setOverTimeInputs(overTimeInputs);
		// 01-05_申請定型理由を取得, 01-06_申請理由を取得
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
		if(appTypeDiscreteSetting.isPresent()){
			result.setTypicalReasonDisplayFlg(false);
			// 01-05_申請定型理由を取得
			if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setTypicalReasonDisplayFlg(true);
				List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
						companyID,
						appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg(),
						ApplicationType.OVER_TIME_APPLICATION);
				List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
				for (ApplicationReason applicationReason : applicationReasons) {
					ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
							applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
					applicationReasonDtos.add(applicationReasonDto);
				}
				result.setApplicationReasonDtos(applicationReasonDtos);
			}
			//01-06_申請理由を取得
			result.setDisplayAppReasonContentFlg(otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.get().getDisplayReasonFlg()));
			if(result.isDisplayAppReasonContentFlg()){
				applicationDto.setApplicationReason(reasonContent);
			}
		}
		if(overtimeRestAppCommonSet.isPresent()){
			result.setDisplayDivergenceReasonForm(false);
			//01-08_乖離定型理由を取得
			if(result.getApplication().getPrePostAtr() != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
						.getDivergenceReasonForm(
								companyID,
								EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class),
								overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(),
								ApplicationType.OVER_TIME_APPLICATION);
				convertToDivergenceReasonDto(divergenceReasons,result);
			}
			//01-07_乖離理由を取得
			result.setDisplayDivergenceReasonInput(
					commonOvertimeHoliday.displayDivergenceReasonInput(
							EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class), 
							overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr()));
		}
		
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
				//dung cho thay doi xin truoc xin sau
				result.setPerformanceDisplayAtr(true);
			}
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
				result.setPreDisplayAtr(true);
			}
			// hien thi du lieu thuc te
			if(result.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
				result.setReferencePanelFlg(true);
			}
			// hien thi don xin truoc
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value && result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
				result.setAllPreAppPanelFlg(true);
			}else{
				result.setAllPreAppPanelFlg(false);
			}
		}
		if(result.isAllPreAppPanelFlg()){
			//01-09_事前申請を取得
			if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value ){
				result.setPreAppPanelFlg(false);
				AppOverTime appOvertime = otherCommonAlgorithm.getPreApplication(
						employeeID,
						EnumAdaptor.valueOf(result.getApplication().getPrePostAtr(), PrePostAtr.class),
						overtimeRestAppCommonSet.get().getPreDisplayAtr(), 
						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
						ApplicationType.OVER_TIME_APPLICATION);
				if(appOvertime != null){
					result.setPreAppPanelFlg(true);
					convertOverTimeDto(companyID,preAppOvertimeDto,result,appOvertime);
				}			
			}
		}
		if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value && appDate != null){
			// 01-18_実績の内容を表示し直す :
			if (approvalFunctionSetting != null) {
				AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
				WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
				ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
						.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, 
								result.getWorkType() == null ? null : result.getWorkType().getWorkTypeCode(), 
								result.getSiftType() ==  null ? null : result.getSiftType().getSiftCode(), 
								withdrawalAppSet.getOverrideSet(), Optional.empty());
				appOvertimeReference.setAppDateRefer(appDate);
				List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
				for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
					overTimeInputsRefer.add(CaculationTime.builder()
							.attendanceID(1)
							.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
							.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
							.build());
				}
				if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
					result.setAppOvertimeReference(appOvertimeReference);
				} else {
					appOvertimeReference.setWorkTypeRefer(
							new WorkTypeOvertime(actualStatusCheckResult.workType, 
									workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
					appOvertimeReference.setSiftTypeRefer(
							new SiftType(actualStatusCheckResult.workTime, 
									workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
					appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
					
					for(CaculationTime caculationTime : overTimeInputsRefer) {
						caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
								.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
								.findAny().map(y -> y.actualTime).orElse(null));
					}
					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
					result.setAppOvertimeReference(appOvertimeReference);
				}
			}
		}
		if(appOvertimeSettingRepository.getAppOver().isPresent()){
			// display flex
			if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.NOTDISPLAY)){
				result.setFlexFLag(false);
			}else if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr().equals(FlexExcessUseSetAtr.DISPLAY)){
				GeneralDate baseDate = appCommonSettingOutput.generalDate;
				Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID,baseDate);
				if(personalLablorCodition.isPresent()){
					if(personalLablorCodition.get().getLaborSystem().isFlexTimeWork()){
						result.setFlexFLag(true);
					}else{
						result.setFlexFLag(false);
					}
				}
			}else{
				result.setFlexFLag(true);
			}
			//ドメインモデル「残業申請設定」.勤種変更可否フラグがtrueの場合
			result.setWorkTypeChangeFlg(false);
			if(appOvertimeSettingRepository.getAppOver().get().getWorkTypeChangeFlag().equals(Changeable.CHANGEABLE)){
				result.setWorkTypeChangeFlg(true);
			}
		}
		// xử lí tính toán
		if(result.getApplication().getApplicationDate() != null
				&& result.getWorkClockFrom1() != null
				&& result.getWorkClockTo1() != null
				&& result.getWorkType() != null
				&& !StringUtils.isEmpty(result.getWorkType().getWorkTypeCode()) 
				&& result.getSiftType() != null
				&& !StringUtils.isEmpty(result.getSiftType().getSiftCode())
				&& result.isDisplayCaculationTime()){
			result.setCaculationTimes(this.getCaculationValue(overTimeHours,
					bonusTimes,
					result.getApplication().getPrePostAtr(), 
					appDate,
					result.getSiftType().getSiftCode(),
					result.getWorkType().getWorkTypeCode(),
					result.getWorkClockFrom1(),
					result.getWorkClockTo1(),
					result.getTimezones().stream().map(x -> x.getStart()).collect(Collectors.toList()),
					result.getTimezones().stream().map(x -> x.getEnd()).collect(Collectors.toList())));
			result.setResultCaculationTimeFlg(true);
			
		}
		result.setApplication(applicationDto);
	}

	/**
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, OverTimeDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	
	/**
	 * @param companyID
	 * @param applicationDto
	 * @param result
	 * @param appOvertime
	 */
	private void convertOverTimeDto(String companyID,PreAppOvertimeDto preAppOvertimeDto, OverTimeDto result,AppOverTime appOvertime){
		if(appOvertime.getApplication() != null){
			if(appOvertime.getApplication().getAppDate() != null){
				preAppOvertimeDto.setAppDatePre(appOvertime.getApplication().getAppDate().toString(DATE_FORMAT));
			}
		}
		
		if (appOvertime.getWorkTypeCode() != null) {
			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
			workTypeOvertime.setWorkTypeCode(appOvertime.getWorkTypeCode().toString());
			Optional<WorkType> workType = workTypeRepository.findNoAbolishByPK(companyID,
					appOvertime.getWorkTypeCode().toString());
			if (workType.isPresent()) {
				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
			}
			preAppOvertimeDto.setWorkTypePre(workTypeOvertime);
		}
		if (appOvertime.getSiftCode() != null) {
			SiftType siftType = new SiftType();

			siftType.setSiftCode(appOvertime.getSiftCode().toString().equals("000")? "" : appOvertime.getSiftCode().toString());
			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
					appOvertime.getSiftCode().toString()); 
			if (workTime.isPresent()) {
                siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().v());
			}
			preAppOvertimeDto.setSiftTypePre(siftType);
		}

		preAppOvertimeDto.setWorkClockFromTo1Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom1(),appOvertime.getWorkClockTo1()));
		preAppOvertimeDto.setWorkClockFromTo2Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom2(),appOvertime.getWorkClockTo2()));

		
		List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
		List<OverTimeInput> overtimeInputs = appOvertime.getOverTimeInput();
		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
			List<Integer> frameNo = new ArrayList<>();
			for (OverTimeInput overTimeInput : overtimeInputs) {
				OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
				overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceType().value);
				overtimeInputDto.setFrameNo(overTimeInput.getFrameNo());
				overtimeInputDto.setStartTime(overTimeInput.getStartTime() == null ? null : overTimeInput.getStartTime().v());
				overtimeInputDto.setEndTime(overTimeInput.getEndTime() == null ? null : overTimeInput.getEndTime().v());
				overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime()== null ? null : overTimeInput.getApplicationTime().v());
				overtimeInputDtos.add(overtimeInputDto);
				frameNo.add(overTimeInput.getFrameNo());
			}
			List<OvertimeWorkFrame> overtimeFrames = this.overtimeFrameRepository.getOvertimeWorkFrameByFrameNos(companyID,frameNo);
			for (OvertimeInputDto overtimeInputDto : overtimeInputDtos) {
				for (OvertimeWorkFrame overtimeFrame : overtimeFrames) {
					if (overtimeInputDto.getFrameNo() == overtimeFrame.getOvertimeWorkFrNo().v().intValueExact()) {
						overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
						continue;
					}
				}
			}
			preAppOvertimeDto.setOverTimeInputsPre(overtimeInputDtos);
			preAppOvertimeDto.setOverTimeShiftNightPre(appOvertime.getOverTimeShiftNight());
			preAppOvertimeDto.setFlexExessTimePre(appOvertime.getFlexExessTime());
			
		}
		result.setPreAppOvertimeDto(preAppOvertimeDto);
	}
	private String convertWorkClockFromTo(Integer startTime, Integer endTime){
		String WorkClockFromTo = "";
		if(startTime == null && endTime != null){
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
			WorkClockFromTo = "　"
					+  "　~　"
					+ endTimeWithDay.getDayDivision().description
					+ convert(endTime);
		}
		if(startTime != null && endTime != null){
			TimeWithDayAttr startTimeWithDay = new TimeWithDayAttr(startTime);
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
		 WorkClockFromTo = startTimeWithDay.getDayDivision().description 
							+ convert(startTime) + "　~　"
							+ endTimeWithDay.getDayDivision().description
							+ convert(endTime);
		}
		return WorkClockFromTo;
	}
	private String convert(Integer minute) {
		if(minute == null){
			return null;
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(minute);
		return timeConvert.getInDayTimeWithFormat();
	}


	
	/**
	 * 01-14_勤務時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param siftCD
	 * @return
	 */
	public RecordWorkDto getRecordWork(String employeeID, String appDate, String siftCD,int prePortAtr,List<CaculationTime> overtimeHours,
			String workTypeCode,List<Integer> startRestTimes,List<Integer> endRestTimes, boolean restTimeDisFlg){
		
		String companyID = AppContexts.user().companyId();
		Integer startTime1 = null; 
		Integer endTime1 = null;
		Integer startTime2 = null;
		Integer endTime2 = null;
		GeneralDate inputDate=  appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT);
		AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				1, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), inputDate);
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		// 01-14_勤務時間取得(lay thoi gian): Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」)
		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, employeeID, null, appDate,
				approvalFunctionSetting,siftCD, true);
		startTime1 = recordWorkOutput.getStartTime1();
		endTime1 = recordWorkOutput.getEndTime1();
		startTime2 = recordWorkOutput.getStartTime2();
		endTime2 = recordWorkOutput.getEndTime2();
		List<DeductionTimeDto> timeZones = new ArrayList<>();
		if(restTimeDisFlg){
			Optional<TimeWithDayAttr> opStartTime = startTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime1)); 
			Optional<TimeWithDayAttr> opEndTime = endTime1==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime1));
			//休憩時間帯を取得する
			List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID,workTypeCode, siftCD, opStartTime, opEndTime);
			timeZones = breakTimes.stream().map(domain->{
				DeductionTimeDto dto = new DeductionTimeDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
			// 01-18_実績の内容を表示し直す
			if(appDate!=null) {
				WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
				ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
						.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, workTypeCode, siftCD, withdrawalAppSet.getOverrideSet(), Optional.empty());
				appOvertimeReference.setAppDateRefer(appDate);
				List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
				List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
				for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
					overTimeInputsRefer.add(CaculationTime.builder()
							.attendanceID(1)
							.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
							.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
							.build());
				}
				if(actualStatusCheckResult.actualStatus==ActualStatus.NO_ACTUAL) {
					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
				} else {
					appOvertimeReference.setWorkTypeRefer(
							new WorkTypeOvertime(actualStatusCheckResult.workType, 
									workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
					appOvertimeReference.setSiftTypeRefer(
							new SiftType(actualStatusCheckResult.workTime, 
									workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
					appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
					for(CaculationTime caculationTime : overTimeInputsRefer) {
						caculationTime.setApplicationTime(actualStatusCheckResult.actualLst.stream()
								.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
								.findAny().map(y -> y.actualTime).orElse(null));
					}
					appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
					appOvertimeReference.setOverTimeShiftNightRefer(actualStatusCheckResult.actualLst.stream()
							.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
							.findAny().map(y -> y.actualTime).orElse(null));
					appOvertimeReference.setFlexExessTimeRefer(actualStatusCheckResult.actualLst.stream()
							.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
							.findAny().map(y -> y.actualTime).orElse(null));
				}
			}
		}
		return new RecordWorkDto(startTime1, endTime1, startTime2, endTime2, appOvertimeReference, timeZones);
	} 
	
	public AppOverTimeMobDto getDetailMob(String appID, AppCommonSettingOutput appCommonSettingOutput) {
		AppOverTimeMobDto appOverTimeMobDto = new AppOverTimeMobDto();
		String companyID = AppContexts.user().companyId();
		Optional<AppOverTime> opAppOverTime = overtimeRepository.getFullAppOvertime(companyID, appID);
		if(!opAppOverTime.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppOverTime appOverTime = opAppOverTime.get();
		Application_New application = appOverTime.getApplication();
		appOverTimeMobDto.applicant = employeeAdapter.getEmployeeName(application.getEmployeeID());
		if(!application.getEmployeeID().equals(application.getEnteredPersonID())){
			appOverTimeMobDto.representer = employeeAdapter.getEmployeeName(application.getEnteredPersonID());
		}
		appOverTimeMobDto.appDate = application.getAppDate().toString(DATE_FORMAT);
		appOverTimeMobDto.inputDate = application.getInputDate().toString(DATE_TIME_FORMAT);
		appOverTimeMobDto.appType = application.getAppType().value;
		appOverTimeMobDto.prePostAtr = application.getPrePostAtr().value;
		appOverTimeMobDto.workTypeCD = appOverTime.getWorkTypeCode()==null ? null : appOverTime.getWorkTypeCode().v();
		appOverTimeMobDto.workTimeCD = appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v() ;
		appOverTimeMobDto.startTime = appOverTime.getWorkClockFrom1();
		appOverTimeMobDto.endTime = appOverTime.getWorkClockTo1();
		appOverTimeMobDto.appReason = application.getAppReason().v();
		appOverTimeMobDto.divergenceReasonContent = appOverTime.getDivergenceReason();
		appOverTimeMobDto.frameLst = new ArrayList<>();
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value).get();
		if(overtimeRestAppCommonSet.getBonusTimeDisplayAtr().value == UseAtr.USE.value){
			appOverTimeMobDto.displayBonusTime = true;
		} 
		List<OvertimeColorCheck> otTimeLst = new ArrayList<>();
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			appOverTimeMobDto.frameLst.add(new OvertimeFrameDto(
					AttendanceType.NORMALOVERTIME.value, 
					overtimeFrame.getOvertimeWorkFrNo().v().intValue(), 
					overtimeFrame.getOvertimeWorkFrName().toString()));
			otTimeLst.add(OvertimeColorCheck.createApp(
					AttendanceType.NORMALOVERTIME.value, 
					overtimeFrame.getOvertimeWorkFrNo().v().intValue(), 
					null));
		}
		appOverTimeMobDto.appOvertimeNightFlg = appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value == 1 ? true : false;
		if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr() == FlexExcessUseSetAtr.ALWAYSDISPLAY){
			appOverTimeMobDto.flexFLag = true;
		} else if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr() == FlexExcessUseSetAtr.DISPLAY){
			Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository
					.getBySidAndStandardDate(application.getEmployeeID(), application.getAppDate());
			if(personalLablorCodition.isPresent()){
				if(personalLablorCodition.get().getLaborSystem() == WorkingSystem.FLEX_TIME_WORK){
					appOverTimeMobDto.flexFLag = true;
				}
			}
		}
		if(appOverTimeMobDto.appOvertimeNightFlg) {
			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 11, null));
		}
		if(appOverTimeMobDto.flexFLag) {
			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 12, null));
		}
		if(appOverTimeMobDto.displayBonusTime){
			List<BonusPayTimeItem> bonusPayTimeItems= this.commonOvertimeHoliday.getBonusTime(
					companyID,
					appOverTime.getApplication().getEmployeeID(),
					appOverTime.getApplication().getAppDate(),
					overtimeRestAppCommonSet.getBonusTimeDisplayAtr());
			for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
				appOverTimeMobDto.frameLst.add(new OvertimeFrameDto(
						AttendanceType.BONUSPAYTIME.value, 
						bonusPayTimeItem.getId(), 
						bonusPayTimeItem.getTimeItemName().toString()));
				otTimeLst.add(OvertimeColorCheck.createApp(
						AttendanceType.BONUSPAYTIME.value, 
						bonusPayTimeItem.getId(), 
						null));
			}
		}
		List<OvertimeBreakDto> breakTimeLst = appOverTime.getOverTimeInput().stream()
				.filter(x -> x.getAttendanceType().value==0)
				.map(x -> new OvertimeBreakDto(x.getFrameNo(), x.getStartTime().v(), x.getEndTime().v()))
				.collect(Collectors.toList());
		appOverTimeMobDto.breakTimeLst = breakTimeLst;
		otTimeLst = otTimeLst.stream().map(x -> {
			Integer value = appOverTime.getOverTimeInput().stream()
			.filter(y -> y.getAttendanceType().value==x.attendanceID && y.getFrameNo()==x.frameNo)
			.findAny().map(z -> z.getApplicationTime().v()).orElse(null);
			return OvertimeColorCheck.createApp(x.attendanceID, x.frameNo, value);
		}).collect(Collectors.toList());
		if(appOverTime.getWorkTypeCode()!=null && appOverTime.getSiftCode()!=null) {
			// // アルゴリズム「残業申請設定を取得する」を実行する
			UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
			AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
			WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
			// 07-01_事前申請状態チェック
			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
					companyID, 
					application.getEmployeeID(), 
					application.getAppDate(), 
					application.getAppType());
			// 07-02_実績取得・状態チェック
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
					companyID, 
					application.getEmployeeID(), 
					application.getAppDate(), 
					application.getAppType(), 
					appOverTime.getWorkTypeCode() == null ? null : appOverTime.getWorkTypeCode().v(), 
					appOverTime.getSiftCode() == null ? null : appOverTime.getSiftCode().v(), 
					withdrawalAppSet.getOverrideSet(), 
					Optional.empty());
			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
			PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(
					preExcessDisplaySetting, 
					performanceExcessAtr, 
					application.getAppType(), 
					application.getPrePostAtr(), 
					withdrawalAppSet.getOverrideSet(), 
					Optional.empty(), 
					Collections.emptyList(), 
					otTimeLst,
					preAppCheckResult.opAppBefore,
					preAppCheckResult.beforeAppStatus,
					actualStatusCheckResult.actualLst,
					actualStatusCheckResult.actualStatus);
			appOverTimeMobDto.timeLst = preActualColorResult.resultLst;
		} else {
			appOverTimeMobDto.timeLst = otTimeLst;
		}
		// appOverTimeMobDto.timeLst = Collections.emptyList();
		// アルゴリズム「勤務種類名称を取得する」を実行する
		if(appOverTime.getWorkTypeCode()!=null){
			appOverTimeMobDto.workTypeName = workTypeRepository.findByPK(companyID, appOverTimeMobDto.workTypeCD)
				.map(x -> x.getName().v()).orElse(null);
		}
		// アルゴリズム「就業時間帯名称を取得する」を実行する
		if(appOverTime.getSiftCode()!=null){
			appOverTimeMobDto.workTimeName = workTimeRepository.findByCode(companyID, appOverTimeMobDto.workTimeCD)
				.map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null);
		}
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
			appOverTimeMobDto.displayCaculationTime = true;
		}
		appOverTimeMobDto.displayRestTime = commonOvertimeHoliday.getRestTime(
				companyID,
				approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse(),
				approvalFunctionSetting.getApplicationDetailSetting().get().getBreakInputFieldDisp(),
				ApplicationType.OVER_TIME_APPLICATION);
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
		if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
			appOverTimeMobDto.typicalReasonDisplayFlg = true;
		}
		appOverTimeMobDto.displayAppReasonContentFlg = otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.get().getDisplayReasonFlg());
		if(appOverTimeMobDto.prePostAtr != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value){
			appOverTimeMobDto.displayDivergenceReasonForm = true;
		}
		appOverTimeMobDto.displayDivergenceReasonInput = commonOvertimeHoliday.displayDivergenceReasonInput(
						EnumAdaptor.valueOf(appOverTimeMobDto.prePostAtr, PrePostAtr.class), overtimeRestAppCommonSet.getDivergenceReasonInputAtr());
		return appOverTimeMobDto;
	}
}
