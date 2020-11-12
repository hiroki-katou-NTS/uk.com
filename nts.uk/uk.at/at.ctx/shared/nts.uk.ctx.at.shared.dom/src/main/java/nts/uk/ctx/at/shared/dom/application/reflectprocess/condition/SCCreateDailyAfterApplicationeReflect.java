package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.application.bussinesstrip.BusinessTripShare;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.gobackdirectly.GoBackDirectlyShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.schedule.SCReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ReflectGoBackDirectly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.schedulerecord.SCRCReflectGoBackDirectlyApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.schedule.SCReflectWorkStampApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectWorkChangeApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.schedule.SCReflectWorkChangeApp;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampShare;
import nts.uk.ctx.at.shared.dom.application.workchange.AppWorkChangeShare;

/**
 * @author thanh_nx
 *
 *         [RQ668]申請反映後の日別勤怠(work）を作成する（勤務予定）
 */
public class SCCreateDailyAfterApplicationeReflect {

	public static DailyAfterAppReflectResult process(Require require, String companyId, ApplicationShare application,
			DailyRecordOfApplication dailyApp, GeneralDate date) {
		
		// TODO: typeDaikyu chua co domain
		ApplicationReflect domainSetReflect = GetDomainReflectModelApp.process(require, companyId,
				application.getAppType(), Optional.empty());
		List<Integer> itemIds = new ArrayList<Integer>();
		switch (application.getAppType()) {
		case OVER_TIME_APPLICATION:
			// TODO: 0：残業申請を反映する（勤務予定）
			break;
		case ABSENCE_APPLICATION:
			// TODO: 1：休暇申請を反映する(勤務予定）
			break;
		case WORK_CHANGE_APPLICATION:
			// 2：勤務変更申請を反映する(勤務予定）inprocess xu ly set stampsource
			itemIds.addAll(SCReflectWorkChangeApp.reflect(require, (AppWorkChangeShare) application, dailyApp,
					(ReflectWorkChangeApplication) domainSetReflect));
			break;
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請の反映（勤務予定）
			itemIds.addAll(SCReflectBusinessTripApp.reflect(require, (BusinessTripShare) application, dailyApp,
					(ReflectBusinessTripApp) domainSetReflect, date));
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請を反映する(勤務予定）
			itemIds.addAll(SCRCReflectGoBackDirectlyApp.reflect(require, companyId, (GoBackDirectlyShare) application,
					dailyApp, (ReflectGoBackDirectly) domainSetReflect));
			break;
		case HOLIDAY_WORK_APPLICATION:
			// TODO: 6：休日出勤申請を反映する（勤務予定）
			break;
		case STAMP_APPLICATION:
			// 7：打刻申請を反映する（勤務予定）
			itemIds.addAll(SCReflectWorkStampApp.reflect(require, (AppStampShare) application, dailyApp,
					(ReflectAppStamp) domainSetReflect));
			break;
		case ANNUAL_HOLIDAY_APPLICATION:
			// TODO: 8：時間休暇申請を反映する
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

	public static interface Require extends GetDomainReflectModelApp.Require, SCReflectWorkChangeApp.Require,
			SCRCReflectGoBackDirectlyApp.Require, SCReflectWorkStampApp.Require, SCReflectBusinessTripApp.Require {

	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class DailyAfterAppReflectResult {

		private DailyRecordOfApplication domainDaily;

		private List<Integer> lstItemId;

	}
}
