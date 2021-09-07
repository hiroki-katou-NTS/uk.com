package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionParam;

/**
 * @author thanh_nx
 *
 *         申請を登録する
 */
public class RegisterApplicationFromNR {

	// 申請を登録する
	// [R-８] 申請を登録する
	public static void register(Require require, String companyId, String contractCode, Application application) {

		// 登録前の処理チェック
		if (!checkBeforeRegisterData(require, companyId, application)) {
			return;
		}

		ApprovalRootContentImport_New appRoot = require.getApprovalRoot(companyId, application.getEmployeeID(),
				EmploymentRootAtr.APPLICATION, application.getAppType(), application.getAppDate().getApplicationDate());

		if (appRoot.getErrorFlag() != ErrorFlagImport.NO_ERROR) {
			return;
		}

		require.insertApp(application, appRoot.getApprovalRootState().getListApprovalPhaseState());

		switch (application.getAppType()) {
		case STAMP_APPLICATION:
			require.insert((AppRecordImage) application);
			break;

		case OVER_TIME_APPLICATION:
			require.insert((AppOverTime) application);
			break;

		case ABSENCE_APPLICATION:
			require.insert(companyId, contractCode, (ApplyForLeave) application);
			break;

		case WORK_CHANGE_APPLICATION:
			require.insert((AppWorkChange) application);
			break;

		case HOLIDAY_WORK_APPLICATION:
			require.insert((AppHolidayWork) application);
			break;

		case EARLY_LEAVE_CANCEL_APPLICATION:
			require.insert(companyId, (ArrivedLateLeaveEarly) application);
			break;

		case ANNUAL_HOLIDAY_APPLICATION:
			require.insert((TimeLeaveApplication) application);
			break;

		default:
			break;
		}

		DatePeriod period = createPeriod(application);

		// アルゴリズム.暫定データの登録
		require.registerDateChange(companyId, application.getEmployeeID(), period.datesBetween());

	}

	// [pvt-1] 登録前の処理チェック
	private static boolean checkBeforeRegisterData(Require require, String companyId, Application application) {

		// $期間
		DatePeriod period = createPeriod(application);
		List<GeneralDate> lstDateHdCheck = new ArrayList<>();
		if (application.getOpAppStartDate().isPresent() && application.getOpAppEndDate().isPresent()) {
			// $表示する実績内容
			List<ActualContentDisplay> lstContent = require.getAchievementContents(companyId,
					application.getEmployeeID(), period.datesBetween(), application.getAppType());
			// $休日リスト
			lstDateHdCheck.addAll(require.lstDateIsHoliday(application.getEmployeeID(), period, lstContent));
		}

		// $休暇申請の種類
		Optional<HolidayAppType> holidayAppType = Optional.empty();
		if (application.getAppType() == ApplicationType.ABSENCE_APPLICATION) {
			holidayAppType = Optional.of(((ApplyForLeave) application).getVacationInfo().getHolidayApplicationType());
		}

		// $残業申請区分
		Optional<OvertimeAppAtr> overtimeAppAtr = application.getAppType() == ApplicationType.OVER_TIME_APPLICATION
				? Optional.of(((AppOverTime) application).getOverTimeClf())
				: Optional.empty();

		// $申請表示情報
		AppDispInfoStartupOutput appDispInfo = require.getAppDispInfoStart(companyId, application.getAppType(),
				Arrays.asList(application.getEmployeeID()), period.datesBetween(), true, holidayAppType,
				overtimeAppAtr);

		Optional<String> workTypeCodes = Optional.empty();
		Optional<String> workTimeCodes = Optional.empty();
		if (application.getAppType() == ApplicationType.ABSENCE_APPLICATION) {
			workTypeCodes = Optional
					.of(((ApplyForLeave) application).getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode().v());
			workTimeCodes = ((ApplyForLeave) application).getReflectFreeTimeApp().getWorkInfo().getWorkTimeCodeNotNull()
					.map(x -> x.v());
		} else if (application.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
			workTypeCodes = ((AppWorkChange) application).getOpWorkTypeCD().map(x -> x.v());
			workTimeCodes = ((AppWorkChange) application).getOpWorkTimeCD().map(x -> x.v());
		} else if (application.getAppType() == ApplicationType.HOLIDAY_WORK_APPLICATION) {
			workTypeCodes = Optional.of(((AppHolidayWork) application).getWorkInformation().getWorkTypeCode().v());
			workTimeCodes = ((AppHolidayWork) application).getWorkInformation().getWorkTimeCodeNotNull()
					.map(x -> x.v());
		}

		try {
			val errorList = require.processBeforeRegister_New(companyId, EmploymentRootAtr.APPLICATION, false,
					application, overtimeAppAtr.orElse(null), new ArrayList<>(), lstDateHdCheck, appDispInfo,
					workTypeCodes.map(x -> Arrays.asList(x)).orElse(new ArrayList<>()), Optional.empty(), workTimeCodes,
					false);
			if (errorList.isEmpty())
				return true;
			return false;
		} catch (BusinessException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	// [pvt-2] 期間を作成
	private static DatePeriod createPeriod(Application app) {
		if (app.getOpAppEndDate().isPresent() && app.getOpAppStartDate().isPresent()) {
			return new DatePeriod(app.getOpAppStartDate().get().getApplicationDate(),
					app.getOpAppEndDate().get().getApplicationDate());
		}
		return new DatePeriod(app.getAppDate().getApplicationDate(), app.getAppDate().getApplicationDate());
	}

	public static interface Require {

		/**
		 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係あり)を取得する.12_承認ルートを取得.12_承認ルートを取得
		 * CommonAlgorithm
		 * 
		 * @param companyID  会社ID
		 * @param employeeID 申請者ID
		 * @param rootAtr    就業ルート区分
		 * @param appType    申請種類
		 * @param appDate    基準日
		 * @return
		 */
		public ApprovalRootContentImport_New getApprovalRoot(String companyId, String employeeID,
				EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate);

		// ApplicationApprovalService
		public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState);

		// [R-3] 打刻申請を作る
		public void insert(AppRecordImage appStamp);

		// [R-4] 残業申請を作る
		public void insert(AppOverTime appOverTime);

		// [R-5] 休暇申請を作る
		public void insert(String companyId, String contractCode, ApplyForLeave appAbsence);

		// [R-6] 勤務変更申請を作る
		public void insert(AppWorkChange appWorkChange);

		// [R-7] 休日出勤時間申請を作る
		public void insert(AppHolidayWork appHolidayWork);

		// [R-8] 遅刻早退取消申請を作る
		public void insert(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly);

		// [R-9] 時間年休申請を作る
		public void insert(TimeLeaveApplication timeLeav);

		// 暫定データの登録
		// InterimRemainDataMngRegisterDateChange
		public void registerDateChange(String cid, String sid, List<GeneralDate> lstDate);

		// 実績内容の取得
		// CollectAchievement.getAchievementContents
		public List<ActualContentDisplay> getAchievementContents(String companyID, String employeeID,
				List<GeneralDate> dateLst, ApplicationType appType);

		// UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請期間から休日の申請日を取得する.申請期間から休日の申請日を取得する
		// OtherCommonAlgorithm
		public List<GeneralDate> lstDateIsHoliday(String sid, DatePeriod dates,
				List<ActualContentDisplay> actualContentDisplayLst);

		// UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-1.新規画面登録前の処理(beforeRegister).2-1.新規画面登録前の処理
		// NewBeforeRegister
		public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr,
				boolean agentAtr, Application application, OvertimeAppAtr overtimeAppAtr,
				List<MsgErrorOutput> msgErrorLst, List<GeneralDate> lstDateHd,
				AppDispInfoStartupOutput appDispInfoStartupOutput, List<String> workTypeCds,
				Optional<TimeDigestionParam> timeDigestionUsageInfor, Optional<String> workTimeCode, boolean flag);

		// UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.起動時の申請表示情報を取得する.起動時の申請表示情報を取得する
		// CommonAlgorithm
		public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType,
				List<String> applicantLst, List<GeneralDate> dateLst, boolean mode,
				Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr);

	}
}
