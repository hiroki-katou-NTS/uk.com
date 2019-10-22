package nts.uk.ctx.at.request.app.command.application.overtime;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeCheckResultDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.IErrorCheckBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.FlexExcessUseSetAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckBeforeRegisterOvertime {

	@Inject
	private NewBeforeRegister_New newBeforeRegister;
	@Inject
	private IErrorCheckBeforeRegister beforeCheck;
	@Inject
	private IFactoryOvertime factoryOvertime;
	@Inject
	private OvertimeRestAppCommonSetRepository overTimeSetRepo;
	@Inject 
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private AppOvertimeSettingRepository appOvertimeSettingRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	public ColorConfirmResult checkBeforeRegisterColor(CreateOvertimeCommand command) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();
		Integer workClockFrom2 = command.getWorkClockFrom2() == null ? null : command.getWorkClockFrom2().intValue();
		Integer workClockTo2 = command.getWorkClockTo2() == null ? null : command.getWorkClockTo2().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, workClockFrom2,
				workClockTo2, command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());


		return beforeRegisterColorConfirm(command.getCalculateFlag(), appRoot, overTimeDomain, command.isCheckOver1Year(),command.isCheckAppDate(), command.getAppID());
	}

	public OvertimeCheckResultDto CheckBeforeRegister(CreateOvertimeCommand command) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();
		Integer workClockFrom2 = command.getWorkClockFrom2() == null ? null : command.getWorkClockFrom2().intValue();
		Integer workClockTo2 = command.getWorkClockTo2() == null ? null : command.getWorkClockTo2().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, workClockFrom2,
				workClockTo2, command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());


		return checkBeforeRegister(command.getCalculateFlag(), appRoot, overTimeDomain, command.isCheckOver1Year(),command.isCheckAppDate());
	}

	public ColorConfirmResult beforeRegisterColorConfirm(int calculateFlg, Application_New app, AppOverTime overtime, boolean checkOver1Year, boolean checkAppDate, String appID) {
		// 社員ID
		String employeeId = app.getEmployeeID();
		String companyID =  app.getCompanyID();
		// 2-1.新規画面登録前の処理を実行する
		if (null == appID) {
			newBeforeRegister.processBeforeRegister(app,overtime.getOverTimeAtr().value, checkOver1Year, Collections.emptyList());
		}
		// 登録前エラーチェック
		// 計算ボタン未クリックチェック
		commonOvertimeHoliday.calculateButtonCheck(calculateFlg, app.getCompanyID(), employeeId, 1,
				ApplicationType.OVER_TIME_APPLICATION, app.getAppDate());
		// 事前申請超過チェック
		Map<AttendanceType, List<OverTimeInput>> findMap = overtime.getOverTimeInput().stream()
				.collect(groupingBy(OverTimeInput::getAttendanceType));
		// Only check for [残業時間]
		// 時間①～フレ超過時間 まで 背景色をピンク
		List<OverTimeInput> overtimeInputs = new ArrayList<>(); 
		overtimeInputs.addAll(CollectionUtil.isEmpty(findMap.get(AttendanceType.NORMALOVERTIME)) ? Collections.emptyList() : findMap.get(AttendanceType.NORMALOVERTIME));
		overtimeInputs.addAll(CollectionUtil.isEmpty(findMap.get(AttendanceType.BONUSPAYTIME)) ? Collections.emptyList() : findMap.get(AttendanceType.BONUSPAYTIME));
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, app.getEmployeeID(), 1, 
				EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), app.getAppDate());
		Optional<OvertimeRestAppCommonSetting>  overTimeSettingOpt = this.overTimeSetRepo.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		List<OvertimeColorCheck> otTimeLst = new ArrayList<>();
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
		for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
			otTimeLst.add(OvertimeColorCheck.createApp(
					AttendanceType.NORMALOVERTIME.value, 
					overtimeFrame.getOvertimeWorkFrNo().v().intValue(), 
					null));
		}
		boolean appOvertimeNightFlg = appCommonSettingOutput.applicationSetting.getAppOvertimeNightFlg().value == 1 ? true : false;
		boolean flexFLag = false;
		if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr() == FlexExcessUseSetAtr.ALWAYSDISPLAY){
			flexFLag = true;
		} else if(appOvertimeSettingRepository.getAppOver().get().getFlexJExcessUseSetAtr() == FlexExcessUseSetAtr.DISPLAY){
			Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository
					.getBySidAndStandardDate(app.getEmployeeID(), app.getAppDate());
			if(personalLablorCodition.isPresent()){
				if(personalLablorCodition.get().getLaborSystem() == WorkingSystem.FLEX_TIME_WORK){
					flexFLag = true;
				}
			}
		}
		if(appOvertimeNightFlg) {
			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 11, null));
		}
		if(flexFLag) {
			otTimeLst.add(OvertimeColorCheck.createApp(AttendanceType.NORMALOVERTIME.value, 12, null));
		}
		boolean displayBonusTime = false;
		if(overTimeSettingOpt.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
			displayBonusTime = true;
		} 
		if(displayBonusTime){
			List<BonusPayTimeItem> bonusPayTimeItems= this.commonOvertimeHoliday.getBonusTime(
					companyID,
					app.getEmployeeID(),
					app.getAppDate(),
					overTimeSettingOpt.get().getBonusTimeDisplayAtr());
			for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItems){
				otTimeLst.add(OvertimeColorCheck.createApp(
						AttendanceType.BONUSPAYTIME.value, 
						bonusPayTimeItem.getId(), 
						null));
			}
		}
		PreActualColorResult preActualColorResult = null;
		if(app.getPrePostAtr()==PrePostAtr.POSTERIOR) {
			UseAtr preExcessDisplaySetting = overTimeSettingOpt.get().getPreExcessDisplaySetting();
			AppDateContradictionAtr performanceExcessAtr = overTimeSettingOpt.get().getPerformanceExcessAtr();
			AppOvertimeSetting appOvertimeSetting = appOvertimeSettingRepository.getAppOver().get();
			otTimeLst = otTimeLst.stream().map(x -> {
				Integer value = overtimeInputs.stream()
				.filter(y -> y.getAttendanceType().value==x.attendanceID && y.getFrameNo()==x.frameNo)
				.findAny().map(z -> z.getApplicationTime().v()).orElse(null);
				return OvertimeColorCheck.createApp(x.attendanceID, x.frameNo, value);
			}).collect(Collectors.toList());
			// 07-01_事前申請状態チェック
			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
					companyID, 
					employeeId, 
					app.getAppDate(), 
					ApplicationType.OVER_TIME_APPLICATION);
			// 07-02_実績取得・状態チェック
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
					companyID, 
					employeeId, 
					app.getAppDate(), 
					ApplicationType.OVER_TIME_APPLICATION, 
					overtime.getWorkTypeCode() == null ? null : overtime.getWorkTypeCode().v(), 
					overtime.getSiftCode() == null ? null : overtime.getSiftCode().v(), 
							appOvertimeSetting.getPriorityStampSetAtr(), 
					Optional.empty());
			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
			preActualColorResult = preActualColorCheck.preActualColorCheck(
					preExcessDisplaySetting, 
					performanceExcessAtr, 
					ApplicationType.OVER_TIME_APPLICATION, 
					app.getPrePostAtr(), 
					Collections.emptyList(), 
					otTimeLst,
					preAppCheckResult.opAppBefore,
					preAppCheckResult.beforeAppStatus,
					actualStatusCheckResult.actualLst,
					actualStatusCheckResult.actualStatus);
		}
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, preActualColorResult);
	}
	
	public OvertimeCheckResultDto checkBeforeRegister(int calculateFlg, Application_New app, AppOverTime overTimeDomain, boolean checkOver1Year, boolean checkAppDate) {
		OvertimeCheckResultDto result = new OvertimeCheckResultDto(0, 0, 0, false, null);
		// TODO: 実績超過チェック
		beforeCheck.OvercountCheck(app.getCompanyID(), app.getAppDate(), app.getPrePostAtr());
		// ３６協定時間上限チェック（月間）
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = commonOvertimeHoliday.registerOvertimeCheck36TimeLimit(
				app.getCompanyID(), app.getEmployeeID(), app.getAppDate(), overTimeDomain.getOverTimeInput());
		result.setAppOvertimeDetail(AppOvertimeDetailDto.fromDomain(appOvertimeDetailOtp));
		beforeCheck.TimeUpperLimitYearCheck();
		
		return result;
	}
	
	public ColorConfirmResult checkBeforeUpdateColor(CreateOvertimeCommand command){
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();
		Integer workClockFrom2 = command.getWorkClockFrom2() == null ? null : command.getWorkClockFrom2().intValue();
		Integer workClockTo2 = command.getWorkClockTo2() == null ? null : command.getWorkClockTo2().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, workClockFrom2,
				workClockTo2, command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());
		// 社員ID
		String employeeId = AppContexts.user().employeeId();
		int calculateFlg = command.getCalculateFlag();
		// 登録前エラーチェック
		// 計算ボタン未クリックチェック
		commonOvertimeHoliday.calculateButtonCheck(calculateFlg, appRoot.getCompanyID(), employeeId, 1,
				ApplicationType.OVER_TIME_APPLICATION, appRoot.getAppDate());
		// 事前申請超過チェック
		Map<AttendanceType, List<OverTimeInput>> findMap = overTimeDomain.getOverTimeInput().stream()
				.collect(groupingBy(OverTimeInput::getAttendanceType));
		// Only check for [残業時間]
		// 時間①～フレ超過時間 まで 背景色をピンク
		List<OverTimeInput> overtimeInputs = findMap.get(AttendanceType.NORMALOVERTIME);
		
		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
			ColorConfirmResult colorConfirmResult = commonOvertimeHoliday.preApplicationExceededCheck(appRoot.getCompanyID(), appRoot.getAppDate(),
					appRoot.getInputDate(), appRoot.getPrePostAtr(), AttendanceType.NORMALOVERTIME.value, overtimeInputs, employeeId);
			if (colorConfirmResult.isConfirm()) {
				return colorConfirmResult;
			}
		}
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
	}
	
	public OvertimeCheckResultDto checkBeforeUpdate(CreateOvertimeCommand command) {

		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();
		Integer workClockFrom2 = command.getWorkClockFrom2() == null ? null : command.getWorkClockFrom2().intValue();
		Integer workClockTo2 = command.getWorkClockTo2() == null ? null : command.getWorkClockTo2().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, workClockFrom2,
				workClockTo2, command.getDivergenceReasonContent().replaceFirst(":", System.lineSeparator()),
				command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());
		// 社員ID
		OvertimeCheckResultDto result = new OvertimeCheckResultDto(0, 0, 0, false, null);
		// 事前申請超過チェック
		Map<AttendanceType, List<OverTimeInput>> findMap = overTimeDomain.getOverTimeInput().stream()
				.collect(groupingBy(OverTimeInput::getAttendanceType));
		// Only check for [残業時間]
		// 時間①～フレ超過時間 まで 背景色をピンク
		List<OverTimeInput> overtimeInputs = findMap.get(AttendanceType.NORMALOVERTIME);
		
		// TODO: 実績超過チェック
		beforeCheck.OvercountCheck(appRoot.getCompanyID(), appRoot.getAppDate(), appRoot.getPrePostAtr());
		// ３６上限チェック(詳細)
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = commonOvertimeHoliday.updateOvertimeCheck36TimeLimit(
				appRoot.getCompanyID(), command.getAppID(), appRoot.getEnteredPersonID(), appRoot.getEmployeeID(),
				appRoot.getAppDate(), overtimeInputs);
		result.setAppOvertimeDetail(AppOvertimeDetailDto.fromDomain(appOvertimeDetailOtp));
		// TODO: ３６協定時間上限チェック（年間）
		beforeCheck.TimeUpperLimitYearCheck();

		return result;
	}

	public static List<OverTimeInput> getOverTimeInput(CreateOvertimeCommand command, String Cid, String appId) {
		List<OverTimeInput> overTimeInputs = new ArrayList<OverTimeInput>();
		/**
		 * 休憩時間  ATTENDANCE_ID = 2
		 */
		if (null != command.getBreakTimes()) {
			overTimeInputs.addAll(getOverTimeInput(command.getBreakTimes(), Cid, appId, AttendanceType.BREAKTIME.value));
		}
		/**
		 * 残業時間 ATTENDANCE_ID = 1
		 */
		if (null != command.getOvertimeHours()) {
			overTimeInputs
					.addAll(getOverTimeInput(command.getOvertimeHours(), Cid, appId, AttendanceType.NORMALOVERTIME.value));
		}
		/**
		 * 休出時間 ATTENDANCE_ID = 0
		 */
		if (null != command.getRestTime()) {
			overTimeInputs
					.addAll(getOverTimeInput(command.getRestTime(), Cid, appId, AttendanceType.RESTTIME.value));
		}
		/**
		 * 加給時間 ATTENDANCE_ID = 3
		 */
		if (null != command.getBonusTimes()) {
			overTimeInputs.addAll(getOverTimeInput(command.getBonusTimes(), Cid, appId, AttendanceType.BONUSPAYTIME.value));
		}
		return overTimeInputs;
	}

	private static List<OverTimeInput> getOverTimeInput(List<OvertimeInputCommand> inputCommand, String Cid, String appId,
			int attendanceId) {
		return inputCommand.stream().filter(item -> {
			Integer startTime = item.getStartTime() == null ? null : item.getStartTime().intValue();
			Integer endTime = item.getEndTime() == null ? null : item.getEndTime().intValue();
			Integer appTime = item.getApplicationTime() == null ? null : item.getApplicationTime().intValue();
			return startTime != null || endTime != null || appTime != null;
		}).map(item -> {
			Integer startTime = item.getStartTime() == null ? null : item.getStartTime().intValue();
			Integer endTime = item.getEndTime() == null ? null : item.getEndTime().intValue();
			Integer appTime = item.getApplicationTime() == null ? null : item.getApplicationTime().intValue();
			return OverTimeInput.createSimpleFromJavaType(Cid, appId, attendanceId, item.getFrameNo(), startTime,
					endTime, appTime, item.getTimeItemTypeAtr());
		}).collect(Collectors.toList());
	}
}
