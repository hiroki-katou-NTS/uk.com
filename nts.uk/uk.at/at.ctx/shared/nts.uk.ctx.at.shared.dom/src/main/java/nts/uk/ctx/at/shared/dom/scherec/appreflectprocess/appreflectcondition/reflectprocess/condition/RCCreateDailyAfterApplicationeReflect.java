package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
<<<<<<< HEAD:nts.uk/uk.at/at.ctx/shared/nts.uk.ctx.at.shared.dom/src/main/java/nts/uk/ctx/at/shared/dom/application/reflectprocess/condition/RCCreateDailyAfterApplicationeReflect.java
import nts.uk.ctx.at.shared.dom.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.record.RCReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.groupappabsence.appabsence.schedule.RCReflectApplyForLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.record.RCReflectArrivedLateLeaveEarlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.RCReflectAppHolidayWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.RCReflectOvertimeApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.record.RCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.SCRCReflectTimeLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.workrecord.RCReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
=======
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
<<<<<<< HEAD
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.lateleaveearly.record.RCReflectArrivedLateLeaveEarlyApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.stamp.record.RCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.timeleaveapplication.SCRCReflectTimeLeaveApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.workchange.workrecord.RCReflectWorkChangeApp;
>>>>>>> e8afbd56623... move package(Reflect application):nts.uk/uk.at/at.ctx/shared/nts.uk.ctx.at.shared.dom/src/main/java/nts/uk/ctx/at/shared/dom/scherec/appreflectprocess/appreflectcondition/reflectprocess/condition/RCCreateDailyAfterApplicationeReflect.java
=======
>>>>>>> 855fbfd81e4... move reflect process v2
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

/**
 * @author thanh_nx
 *
 *         [RQ667]申請反映後の日別勤怠(work）を作成する（勤務実績）
 */
public class RCCreateDailyAfterApplicationeReflect {

	public static DailyAfterAppReflectResult process(Require require, ApplicationShare application,
			DailyRecordOfApplication dailyApp, GeneralDate date) {
		String companyId = require.getCId();
		// TODO: typeDaikyu chua co domain
		Object domainSetReflect = GetDomainReflectModelApp.process(require, companyId, application.getAppType(),
				Optional.empty());
		List<Integer> itemIds = new ArrayList<Integer>();
		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:
<<<<<<< HEAD
			//0：残業申請を反映する（勤務予定）
			itemIds.addAll(RCReflectOvertimeApp.process(require, companyId, (AppOverTimeShare) application, dailyApp,
					(AppReflectOtHdWork) domainSetReflect));
			break;
		case ABSENCE_APPLICATION:
			// 1：休暇申請を反映する(勤務予定）
			return RCReflectApplyForLeaveApp.process(require, (ApplyForLeaveShare) application, dailyApp,
					(VacationApplicationReflect) domainSetReflect);
=======
			// TODO: 0：残業申請を反映する（勤務実績）
			break;
		case ABSENCE_APPLICATION:
			// TODO: 1：休暇申請を反映する(勤務実績）
			break;
>>>>>>> 855fbfd81e4... move reflect process v2
		case WORK_CHANGE_APPLICATION:
			// 2：勤務変更申請を反映する(勤務実績）
			itemIds.addAll(((ReflectWorkChangeApp) domainSetReflect).reflectRecord(require, (AppWorkChangeShare) application, dailyApp));
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請の反映（勤務実績）
			itemIds.addAll(((ReflectBusinessTripApp) domainSetReflect).reflectRecord(require,
					(BusinessTripShare) application, dailyApp, date));
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請を反映する(勤務実績）
			itemIds.addAll(((GoBackReflect) domainSetReflect).reflect(require, companyId, (GoBackDirectlyShare) application,
					dailyApp));
			break;
		case HOLIDAY_WORK_APPLICATION:
<<<<<<< HEAD
			// 6：休日出勤申請を反映する（勤務予定）
			itemIds.addAll(RCReflectAppHolidayWork.process(require, companyId, (AppHolidayWorkShare) application,
					dailyApp, (AppReflectOtHdWork) domainSetReflect).getLstItemId());
=======
			// TODO: 6：休日出勤申請を反映する（勤務実績）
>>>>>>> 855fbfd81e4... move reflect process v2
			break;
		case STAMP_APPLICATION:
			// 7：打刻申請を反映する（勤務実績）
			if (!application.getOpStampRequestMode().isPresent()
					|| application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ADDITIONAL) {
				itemIds.addAll(
						((StampAppReflect) domainSetReflect).reflectRecord(require, (AppStampShare) application, dailyApp));
			}
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// 8：時間休暇申請を反映する
			itemIds.addAll(((TimeLeaveApplicationReflect) domainSetReflect)
					.reflect((TimeLeaveApplicationShare) application, dailyApp).getLstItemId());
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: 遅刻早退取消申請
			itemIds.addAll(((LateEarlyCancelReflect) domainSetReflect).reflect((ArrivedLateLeaveEarlyShare) application,
					dailyApp));
			break;
		case COMPLEMENT_LEAVE_APPLICATION:
			// TODO: [input. 申請.振休振出申請種類]をチェック
			break;

		case OPTIONAL_ITEM_APPLICATION:
			// TODO: 15：任意項目を反映する
			break;

		default:
			break;
		}

		return new DailyAfterAppReflectResult(dailyApp, itemIds);
	}

<<<<<<< HEAD
	public static interface Require extends GetDomainReflectModelApp.Require, RCReflectWorkChangeApp.Require,
			SCRCReflectGoBackDirectlyApp.Require, RCReflectWorkStampApp.Require, RCReflectBusinessTripApp.Require, 
			RCReflectOvertimeApp.Require, RCReflectApplyForLeaveApp.Require, RCReflectAppHolidayWork.Require {
=======
	public static interface Require extends GetDomainReflectModelApp.Require, ReflectWorkChangeApp.Require,
	GoBackReflect.Require, StampAppReflect.Require, ReflectBusinessTripApp.Require {
>>>>>>> 855fbfd81e4... move reflect process v2

	}
}
