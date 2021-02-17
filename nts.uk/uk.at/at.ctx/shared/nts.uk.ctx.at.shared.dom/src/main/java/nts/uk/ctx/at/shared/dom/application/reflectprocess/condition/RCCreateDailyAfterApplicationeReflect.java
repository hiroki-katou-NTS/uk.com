package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.lateleaveearly.ArrivedLateLeaveEarlyShare;
import nts.uk.ctx.at.shared.dom.application.overtime.AppOverTimeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.SCCreateDailyAfterApplicationeReflect.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.record.RCReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.record.RCReflectArrivedLateLeaveEarlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.RCReflectOvertimeApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.record.RCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.timeleaveapplication.SCRCReflectTimeLeaveApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.workrecord.RCReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.timeleaveapplication.TimeLeaveApplicationShare;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

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
			//0：残業申請を反映する（勤務予定）
			itemIds.addAll(RCReflectOvertimeApp.process(require, companyId, (AppOverTimeShare) application, dailyApp,
					(AppReflectOtHdWork) domainSetReflect));
			break;
		case ABSENCE_APPLICATION:
			// TODO: 1：休暇申請を反映する(勤務予定）
			break;
		case WORK_CHANGE_APPLICATION:
			// 2：勤務変更申請を反映する(勤務予定）
			itemIds.addAll(RCReflectWorkChangeApp.reflect(require, (AppWorkChangeShare) application, dailyApp,
					(ReflectWorkChangeApp) domainSetReflect));
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請の反映（勤務予定）
			itemIds.addAll(RCReflectBusinessTripApp.reflect(require, (BusinessTripShare) application, dailyApp,
					(ReflectBusinessTripApp) domainSetReflect, date));
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請を反映する(勤務予定）
			itemIds.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, companyId, (GoBackDirectlyShare) application,
					dailyApp, (GoBackReflect) domainSetReflect));
			break;
		case HOLIDAY_WORK_APPLICATION:
			// TODO: 6：休日出勤申請を反映する（勤務予定）
			break;
		case STAMP_APPLICATION:
			// 7：打刻申請を反映する（勤務予定）
			if (!application.getOpStampRequestMode().isPresent()
					|| application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ADDITIONAL) {
				itemIds.addAll(RCReflectWorkStampApp.reflect(require, (AppStampShare) application, dailyApp,
						(StampAppReflect) domainSetReflect));
			}
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// 8：時間休暇申請を反映する
			itemIds.addAll(SCRCReflectTimeLeaveApp.reflect((TimeLeaveApplicationShare) application, dailyApp,
					(TimeLeaveApplicationReflect) domainSetReflect));
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: 遅刻早退取消申請
			itemIds.addAll(RCReflectArrivedLateLeaveEarlyApp.reflect((ArrivedLateLeaveEarlyShare) application, dailyApp,
					(LateEarlyCancelReflect) domainSetReflect));
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

	public static interface Require extends GetDomainReflectModelApp.Require, RCReflectWorkChangeApp.Require,
			SCRCReflectGoBackDirectlyApp.Require, RCReflectWorkStampApp.Require, RCReflectBusinessTripApp.Require, RCReflectOvertimeApp.Require {

	}
}
