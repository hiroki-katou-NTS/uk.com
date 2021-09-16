package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.List;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootServiceImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

/**
 * @author thanh_nx
 *
 *         申請を登録する
 */
public class RegisterApplicationFromNR {

	// 申請を登録する
	// [R-８] 申請を登録する
	public static void register(Require require, String companyId, String contractCode, Application application) {

		//  承認ルートを取得する
		val appRootRegister = require.createDefaultApprovalRootApp(companyId, application.getEmployeeID(),
				String.valueOf(application.getAppType().value), application.getAppDate().getApplicationDate(), application.getAppID(),
				 application.getAppDate().getApplicationDate());
		if (appRootRegister.getErrorFlagExport() != ErrorFlagImport.NO_ERROR) {
			return;
		}
		
		// 申請を登録する
		require.insert(application);
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
		
		// 承認ルートを登録する
		appRootRegister.getTask().ifPresent(x -> {
			x.run();
		});

		// アルゴリズム.暫定データの登録
		require.registerDateChange(companyId, application.getEmployeeID(), period.datesBetween());

	}

	// [pvt-1] 期間を作成
	private static DatePeriod createPeriod(Application app) {
		if (app.getOpAppEndDate().isPresent() && app.getOpAppStartDate().isPresent()) {
			return new DatePeriod(app.getOpAppStartDate().get().getApplicationDate(),
					app.getOpAppEndDate().get().getApplicationDate());
		}
		return new DatePeriod(app.getAppDate().getApplicationDate(), app.getAppDate().getApplicationDate());
	}

	public static interface Require {

		// [R-0] 新規画面登録時承認反映情報の整理
		//RegisterAtApproveReflectionInfoService
		public String newScreenRegisterAtApproveInfoReflect(String empID, Application application);

		// [R-1] 打刻申請を作る
		public void insert(AppRecordImage appStamp);

		// [R-2] 残業申請を作る
		public void insert(AppOverTime appOverTime);

		// [R-3] 休暇申請を作る
		public void insert(String companyId, String contractCode, ApplyForLeave appAbsence);

		// [R-4] 勤務変更申請を作る
		public void insert(AppWorkChange appWorkChange);

		// [R-5] 休日出勤時間申請を作る
		public void insert(AppHolidayWork appHolidayWork);

		// [R-6] 遅刻早退取消申請を作る
		public void insert(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly);

		// [R-7] 時間年休申請を作る
		public void insert(TimeLeaveApplication timeLeav);
		
		// [R-8] 申請を作る
		public void insert(Application application);

		//[R-9] 暫定データの登録
		// InterimRemainDataMngRegisterDateChange
		public void registerDateChange(String cid, String sid, List<GeneralDate> lstDate);

		// [R-10] 承認ルートを登録する
		// CollectApprovalRootServiceAdapter
		public ApprovalRootServiceImport createDefaultApprovalRootApp(String companyID, String employeeID,
				String targetType, GeneralDate standardDate, String appId, GeneralDate appDate);
		
	}
}
