package nts.uk.ctx.at.request.app.command.application.overtime;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOvertimeDetailDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeCheckResultDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingDataDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.IErrorCheckBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckBeforeRegisterOvertime {

	@Inject
	private NewBeforeRegister newBeforeRegister;
	@Inject
	private IErrorCheckBeforeRegister beforeCheck;
	@Inject
	private IFactoryOvertime factoryOvertime;
	@Inject 
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	public ColorConfirmResult checkBeforeRegisterColor(CreateOvertimeCommand command) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
//		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
//				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());
		Application appRoot = null;
		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, null, null, 
				"", command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());
		
		// Optional<Application_New> opAppBefore = command.getOpAppBefore() == null ? null : Optional.ofNullable(ApplicationDto_New.toEntity(command.getOpAppBefore()));
		boolean beforeAppStatus = command.isBeforeAppStatus();
		ActualStatus actualStatus = EnumAdaptor.valueOf(command.getActualStatus(), ActualStatus.class);
		List<OvertimeColorCheck> actualLst = command.getActualLst();

//		return beforeRegisterColorConfirm(command.getCalculateFlag(), appRoot, overTimeDomain, command.isCheckOver1Year(),command.isCheckAppDate(), command.getAppID(),
//				opAppBefore, beforeAppStatus, actualStatus, actualLst, command.getOvertimeSettingDataDto());
		return null;
	}

	public OvertimeCheckResultDto CheckBeforeRegister(CreateOvertimeCommand command) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
//		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
//				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());
		
		Application appRoot = null;
		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, null, null, 
				"", command.getFlexExessTime(), command.getOverTimeShiftNight(),
				CheckBeforeRegisterOvertime.getOverTimeInput(command, companyId, appID),
				Optional.empty());


		return checkBeforeRegister(command.getCalculateFlag(), appRoot, overTimeDomain, command.isCheckOver1Year(),command.isCheckAppDate());
	}

	public ColorConfirmResult beforeRegisterColorConfirm(int calculateFlg, Application app, AppOverTime overtime, boolean checkOver1Year, boolean checkAppDate, String appID,
			Optional<Application> opAppBefore, boolean beforeAppStatus, ActualStatus actualStatus, List<OvertimeColorCheck> actualLst, OvertimeSettingDataDto overtimeSettingDataDto) {
//		String companyID = AppContexts.user().companyId();
//		OvertimeSettingData overtimeSettingData = overtimeSettingDataDto.toDomain();
//		// 社員ID
//		String employeeId = app.getEmployeeID();
//		// 2-1.新規画面登録前の処理を実行する
//		if (null == appID) {
//			// newBeforeRegister.processBeforeRegister(app, overtime.getOverTimeAtr(), checkOver1Year, Collections.emptyList());
//		}
//		// 登録前エラーチェック
//		// 計算ボタン未クリックチェック
//		// Get setting info
//		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet
//				.prelaunchAppCommonSetService(companyID, employeeId, 1, ApplicationType.OVER_TIME_APPLICATION, app.getAppDate().getApplicationDate());
//		// 時刻計算利用する場合にチェックしたい
//		ApprovalFunctionSetting requestSetting = appCommonSettingOutput.approvalFunctionSetting;
//		if (null != requestSetting) {
//			commonOvertimeHoliday.calculateButtonCheck(calculateFlg, requestSetting.getApplicationDetailSetting().get().getTimeCalUse());
//		}
//		
//		// 事前申請超過チェック
//		Map<AttendanceType, List<OverTimeInput>> findMap = overtime.getOverTimeInput().stream()
//				.collect(groupingBy(OverTimeInput::getAttendanceType));
//		// Only check for [残業時間]
//		// 時間①～フレ超過時間 まで 背景色をピンク
//		List<OverTimeInput> overtimeInputs = new ArrayList<>(); 
//		overtimeInputs.addAll(CollectionUtil.isEmpty(findMap.get(AttendanceType.NORMALOVERTIME)) ? Collections.emptyList() : findMap.get(AttendanceType.NORMALOVERTIME));
//		OvertimeRestAppCommonSetting overtimeRestAppCommonSetting = overtimeSettingData.overtimeRestAppCommonSet;
//		List<OvertimeColorCheck> otTimeLst = overtimeInputs.stream()
//				.map(x -> new OvertimeColorCheck(
//						x.getAttendanceType().value, 
//						x.getFrameNo(), 
//						x.getApplicationTimeValue(), 
//						0, 
//						null, 
//						0, 
//						null, 
//						0))
//				.collect(Collectors.toList());
//		PreActualColorResult preActualColorResult = null;
//		if(app.getPrePostAtr()==PrePostAtr.POSTERIOR) {
//			UseAtr preExcessDisplaySetting = overtimeRestAppCommonSetting.getPreExcessDisplaySetting();
//			AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSetting.getPerformanceExcessAtr();
//			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
//			preActualColorResult = preActualColorCheck.preActualColorCheck(
//					preExcessDisplaySetting, 
//					performanceExcessAtr, 
//					ApplicationType.OVER_TIME_APPLICATION, 
//					app.getPrePostAtr(), 
//					Collections.emptyList(), 
//					otTimeLst,
//					opAppBefore,
//					beforeAppStatus,
//					actualLst,
//					actualStatus);
//		}
		//return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, preActualColorResult);
		return null;
	}
	
	public OvertimeCheckResultDto checkBeforeRegister(int calculateFlg, Application app, AppOverTime overTimeDomain, boolean checkOver1Year, boolean checkAppDate) {
		String companyID = AppContexts.user().companyId();
		OvertimeCheckResultDto result = new OvertimeCheckResultDto(0, 0, 0, false, null);
		// TODO: 実績超過チェック
		beforeCheck.OvercountCheck(companyID, app.getAppDate().getApplicationDate(), app.getPrePostAtr());
		// ３６協定時間上限チェック（月間）
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = commonOvertimeHoliday.registerOvertimeCheck36TimeLimit(
				companyID, app.getEmployeeID(), app.getAppDate().getApplicationDate(), overTimeDomain.getOverTimeInput());
		result.setAppOvertimeDetail(AppOvertimeDetailDto.fromDomain(appOvertimeDetailOtp));
		beforeCheck.TimeUpperLimitYearCheck();
		
		return result;
	}
	
	public OvertimeCheckResultDto checkBeforeUpdate(CreateOvertimeCommand command) {

		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Create Application
//		Application_New appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
//				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(),command.getApplicantSID());
		Application appRoot = null;

		Integer workClockFrom1 = command.getWorkClockFrom1() == null ? null : command.getWorkClockFrom1().intValue();
		Integer workClockTo1 = command.getWorkClockTo1() == null ? null : command.getWorkClockTo1().intValue();

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), workClockFrom1, workClockTo1, null, null, 
				"", command.getFlexExessTime(), command.getOverTimeShiftNight(),
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
		beforeCheck.OvercountCheck(companyId, appRoot.getAppDate().getApplicationDate(), appRoot.getPrePostAtr());
		// ３６上限チェック(詳細)
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = commonOvertimeHoliday.updateOvertimeCheck36TimeLimit(
				companyId, command.getAppID(), appRoot.getEnteredPersonID(), appRoot.getEmployeeID(),
				appRoot.getAppDate().getApplicationDate(), overtimeInputs);
		result.setAppOvertimeDetail(AppOvertimeDetailDto.fromDomain(appOvertimeDetailOtp));
		// TODO: ３６協定時間上限チェック（年間）
		beforeCheck.TimeUpperLimitYearCheck();

		return result;
	}

	public static List<OverTimeInput> getOverTimeInput(CreateOvertimeCommand command, String Cid, String appId) {
		List<OverTimeInput> overTimeInputs = new ArrayList<OverTimeInput>();
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
