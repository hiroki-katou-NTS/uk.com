package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
<<<<<<< HEAD:nts.uk/uk.at/at.ctx/shared/nts.uk.ctx.at.shared.dom/src/main/java/nts/uk/ctx/at/shared/dom/application/reflectprocess/condition/SCCreateDailyAfterApplicationeReflect.java
import nts.uk.ctx.at.shared.dom.application.appabsence.ApplyForLeaveShare;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.holidayworktime.AppHolidayWorkShare;
import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.schedule.SCReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.groupappabsence.appabsence.schedule.SCReflectApplyForLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.SCReflectAppHolidayWork;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.SCReflectOvertimeApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.schedule.SCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.SCRCReflectTimeLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
=======
import nts.uk.ctx.at.shared.dom.scherec.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
<<<<<<< HEAD
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.stamp.schedule.SCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.timeleaveapplication.SCRCReflectTimeLeaveApp;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp;
>>>>>>> e8afbd56623... move package(Reflect application):nts.uk/uk.at/at.ctx/shared/nts.uk.ctx.at.shared.dom/src/main/java/nts/uk/ctx/at/shared/dom/scherec/appreflectprocess/appreflectcondition/reflectprocess/condition/SCCreateDailyAfterApplicationeReflect.java
=======
>>>>>>> 855fbfd81e4... move reflect process v2
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

/**
 * @author thanh_nx
 *
 *         [RQ668]申請反映後の日別勤怠(work）を作成する（勤務予定）
 */
public class SCCreateDailyAfterApplicationeReflect {

	public static DailyAfterAppReflectResult process(Require require, String companyId, ApplicationShare application,
			DailyRecordOfApplication dailyApp, GeneralDate date) {

		// TODO: typeDaikyu chua co domain
		Object domainSetReflect = GetDomainReflectModelApp.process(require, companyId, application.getAppType(),
				Optional.empty());
		List<Integer> itemIds = new ArrayList<Integer>();
		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:
			// 0：残業申請を反映する（勤務予定）
			itemIds.addAll(SCReflectOvertimeApp.process(require, (AppOverTimeShare) application, dailyApp,
					(AppReflectOtHdWork) domainSetReflect));
			break;
		case ABSENCE_APPLICATION:
			// 1：休暇申請を反映する(勤務予定）
			return SCReflectApplyForLeaveApp.process(require, (ApplyForLeaveShare) application, dailyApp,
					(VacationApplicationReflect) domainSetReflect);
	
		case WORK_CHANGE_APPLICATION:
<<<<<<< HEAD
			// 2：勤務変更申請を反映する(勤務予定）
			itemIds.addAll(SCReflectWorkChangeApp.reflect(require, (AppWorkChangeShare) application, dailyApp,
					(ReflectWorkChangeApp) domainSetReflect));
=======
			// 2：勤務変更申請を反映する(勤務予定）inprocess xu ly set stampsource
			itemIds.addAll(((ReflectWorkChangeApp) domainSetReflect).reflectSchedule(require,
					(AppWorkChangeShare) application, dailyApp));
>>>>>>> 855fbfd81e4... move reflect process v2
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請の反映（勤務予定）
			itemIds.addAll(((ReflectBusinessTripApp) domainSetReflect).reflectSchedule(require,
					(BusinessTripShare) application, dailyApp, date));
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請を反映する(勤務予定）
			itemIds.addAll(((GoBackReflect) domainSetReflect).reflect(require, companyId,
					(GoBackDirectlyShare) application, dailyApp));
			break;
		case HOLIDAY_WORK_APPLICATION:
			// 6：休日出勤申請を反映する（勤務予定）
			itemIds.addAll(SCReflectAppHolidayWork.process(require, (AppHolidayWorkShare) application,
					dailyApp, (AppReflectOtHdWork) domainSetReflect).getLstItemId());
			break;
		case STAMP_APPLICATION:
			// 7：打刻申請を反映する（勤務予定）
			itemIds.addAll(((StampAppReflect) domainSetReflect).reflectSchedule((AppStampShare) application, dailyApp
					));
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// 8：時間休暇申請を反映する
			itemIds.addAll(((TimeLeaveApplicationReflect) domainSetReflect)
					.reflect((TimeLeaveApplicationShare) application, dailyApp).getLstItemId());
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: 遅刻早退取消申請
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
	public static interface Require extends GetDomainReflectModelApp.Require, SCReflectWorkChangeApp.Require,
			SCRCReflectGoBackDirectlyApp.Require, SCReflectWorkStampApp.Require, SCReflectBusinessTripApp.Require,
			SCReflectOvertimeApp.Require, SCReflectApplyForLeaveApp.Require, SCReflectAppHolidayWork.Require {
=======
	public static interface Require extends GetDomainReflectModelApp.Require, ReflectWorkChangeApp.Require,
			GoBackReflect.Require, ReflectBusinessTripApp.Require {
>>>>>>> 855fbfd81e4... move reflect process v2

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class DailyAfterAppReflectResult {

		private DailyRecordOfApplication domainDaily;

		private List<Integer> lstItemId;

	}
}
