package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

/**
 * @author thanh_nx
 *
 *
 */
public class RegisterApplicationFromNR {

	//申請を登録する
	//[R-８] 申請を登録する
	public static void register(Require require, String companyId, Application application) {

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
			//TODO:
			break;

		case ABSENCE_APPLICATION:
			//TODO:
			break;

		case WORK_CHANGE_APPLICATION:
			 require.insert((AppWorkChange) application);
			break;

		case HOLIDAY_WORK_APPLICATION:
			//TODO:
			break;

		case EARLY_LEAVE_CANCEL_APPLICATION:
			 require.insert(companyId, (ArrivedLateLeaveEarly) application);
			break;

		case ANNUAL_HOLIDAY_APPLICATION:
			//TODO:
			break;

		default:
			break;
		}
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
		public ApprovalRootContentImport_New getApprovalRoot(String companyId, String employeeID, EmploymentRootAtr rootAtr,
				ApplicationType appType, GeneralDate appDate);

		// ApplicationApprovalService
		public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState);

		// [R-8] 打刻申請を作る
		public void insert(AppRecordImage appStamp);

		// [R-9] 残業申請を作る
		public void insert(AppOverTime appOverTime);

		// [R-10] 休暇申請を作る
		public void insert(AppAbsence appAbsence);

		// [R-11] 勤務変更申請を作る
		public void insert(AppWorkChange appWorkChange);

		// [R-12] 休日出勤時間申請を作る
		public void insert(AppHolidayWork appHolidayWork);

		// [R-13] 遅刻早退取消申請を作る
		public void insert(String cid, ArrivedLateLeaveEarly lateOrLeaveEarly);

		// [R-14] 時間年休申請を作る
		public void insert();
	}
}
