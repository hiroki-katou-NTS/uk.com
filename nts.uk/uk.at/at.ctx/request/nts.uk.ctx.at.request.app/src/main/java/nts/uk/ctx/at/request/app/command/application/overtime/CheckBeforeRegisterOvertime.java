package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.find.overtime.dto.OvertimeCheckResultDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.IErrorCheckBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.IFactoryOvertime;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceAtr;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceID;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckBeforeRegisterOvertime {

	@Inject
	private NewBeforeRegister newBeforeRegister;
	@Inject
	private IErrorCheckBeforeRegister beforeCheck;
	@Inject
	private IFactoryOvertime factoryOvertime;

	public OvertimeCheckResultDto CheckBeforeRegister(CreateOvertimeCommand command) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();

		// Phase list
		List<AppApprovalPhase> pharseList = getAppApprovalPhaseList(command, companyId, appID);
		// Create Application
		Application appRoot = factoryOvertime.buildApplication(appID, command.getApplicationDate(),
				command.getPrePostAtr(), command.getApplicationReason(), command.getApplicationReason(), pharseList);

		AppOverTime overTimeDomain = factoryOvertime.buildAppOverTime(companyId, appID, command.getOvertimeAtr(),
				command.getWorkTypeCode(), command.getSiftTypeCode(), command.getWorkClockFrom1(),
				command.getWorkClockTo1(), command.getWorkClockFrom2(), command.getWorkClockTo2(),
				command.getDivergenceReasonContent(), command.getFlexExessTime(), command.getOverTimeShiftNight(),
				getOverTimeInput(command, companyId, appID));

		return CheckBeforeRegister(appRoot, overTimeDomain);
	}

	public OvertimeCheckResultDto CheckBeforeRegister(Application app, AppOverTime overtime) {
		// 社員ID
		String employeeId = AppContexts.user().employeeId();
		OvertimeCheckResultDto result = new OvertimeCheckResultDto(0, 0, 0, false);
		OvertimeCheckResult res = new OvertimeCheckResult();
		// 2-1.新規画面登録前の処理を実行する
		newBeforeRegister.processBeforeRegister(app);
		// 登録前エラーチェック
		// 計算ボタン未クリックチェック
		beforeCheck.calculateButtonCheck(0, app.getCompanyID(), employeeId, 1, ApplicationType.OVER_TIME_APPLICATION,
				app.getApplicationDate());
		// 事前申請超過チェック
		Map<AttendanceID, List<OverTimeInput>> findMap = overtime.getOverTimeInput().stream()
				.collect(groupingBy(OverTimeInput::getAttendanceID));
		// Only check for [残業時間]
		//時間①～フレ超過時間　まで　背景色をピンク
		List<OverTimeInput> overtimeInputs = findMap.get(AttendanceID.NORMALOVERTIME);
		if (!overtimeInputs.isEmpty()) {
			res = beforeCheck.preApplicationExceededCheck(app.getCompanyID(), app.getApplicationDate(),
					app.getInputDate(), app.getPrePostAtr(), AttendanceID.NORMALOVERTIME.value, overtimeInputs);
			if (res.getErrorCode() != 0) {
				result.setErrorCode(res.getErrorCode());
				result.setFrameNo(res.getFrameNo());
				result.setAttendanceId(AttendanceID.NORMALOVERTIME.value);
				return result;
			}
		}
		// TODO: 実績超過チェック
		beforeCheck.OvercountCheck(app.getCompanyID(), app.getApplicationDate(), app.getPrePostAtr());
		// TODO: ３６協定時間上限チェック（月間）
		beforeCheck.TimeUpperLimitMonthCheck();
		// TODO: ３６協定時間上限チェック（年間）
		beforeCheck.TimeUpperLimitYearCheck();
		// 事前否認チェック
		res = beforeCheck.preliminaryDenialCheck(app.getCompanyID(), app.getApplicationDate(), app.getInputDate(),
				app.getPrePostAtr());
		result.setConfirm(res.isConfirm());

		return result;
	}

	/**
	 * Convert Phase command list to Approve Phase list
	 * 
	 * @param command
	 *            : create command
	 * @param companyId:
	 *            会社ID
	 * @param appID:
	 *            申請ID
	 * @return
	 */
	private List<AppApprovalPhase> getAppApprovalPhaseList(CreateOvertimeCommand command, String companyId,
			String appID) {
		return command.getAppApprovalPhaseCmds().stream()
				.map(appApprovalPhaseCmd -> new AppApprovalPhase(companyId, appID, IdentifierUtil.randomUniqueId(),
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class),
						appApprovalPhaseCmd.dispOrder,
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalATR, ApprovalAtr.class),
						appApprovalPhaseCmd.getListFrame().stream()
								.map(approvalFrame -> new ApprovalFrame(companyId,
										IdentifierUtil.randomUniqueId(), approvalFrame.dispOrder,
										approvalFrame.listApproveAccepted
												.stream()
												.map(approveAccepted -> ApproveAccepted.createFromJavaType(companyId,
														IdentifierUtil.randomUniqueId(), approveAccepted.approverSID,
														ApprovalAtr.UNAPPROVED.value, approveAccepted.confirmATR, null,
														approveAccepted.reason, approveAccepted.representerSID))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

	private List<OverTimeInput> getOverTimeInput(CreateOvertimeCommand command, String Cid, String appId) {
		List<OverTimeInput> overTimeInputs = new ArrayList<OverTimeInput>();
		/**
		 * 休出時間 ATTENDANCE_ID = 0
		 */
		if (null != command.getBreakTimes()) {
			overTimeInputs.addAll(getOverTimeInput(command.getBreakTimes(), Cid, appId, AttendanceAtr.Time.value));
		}
		/**
		 * 残業時間 ATTENDANCE_ID = 1
		 */
		if (null != command.getOvertimeHours()) {
			overTimeInputs
					.addAll(getOverTimeInput(command.getOvertimeHours(), Cid, appId, AttendanceAtr.TimeOfDay.value));
		}
		/**
		 * 加給時間 ATTENDANCE_ID = 2
		 */
		if (null != command.getRestTime()) {
			overTimeInputs
					.addAll(getOverTimeInput(command.getRestTime(), Cid, appId, AttendanceAtr.NumberOfTime.value));
		}
		/**
		 * 加給時間 ATTENDANCE_ID = 3
		 */
		if (null != command.getBonusTimes()) {
			overTimeInputs.addAll(getOverTimeInput(command.getBonusTimes(), Cid, appId, AttendanceAtr.Attribute.value));
		}
		return overTimeInputs;
	}

	private List<OverTimeInput> getOverTimeInput(List<OvertimeInputCommand> inputCommand, String Cid, String appId,
			int attendanceId) {
		return inputCommand.stream()
				.filter(item -> item.getStartTime() != 0 || item.getEndTime() != 0 || item.getApplicationTime() != 0)
				.map(item -> OverTimeInput.createSimpleFromJavaType(Cid, appId, attendanceId, item.getFrameNo(),
						item.getStartTime(), item.getEndTime(), item.getApplicationTime()))
				.collect(Collectors.toList());
	}
}
