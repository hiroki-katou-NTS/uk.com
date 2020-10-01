package nts.uk.ctx.at.request.dom.applicationreflect.service.getapp;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

public class GetApplicationForReflect {

	public static Application getAppData(Require require, String companyId, ApplicationType appType, 
			String appID, Application app) {

		switch (appType) {
		case OVER_TIME_APPLICATION:
			// TODO: 0：残業申請
			return null;
		case ABSENCE_APPLICATION:
			// TODO: 1：休暇申請の反映
			return null;
		case WORK_CHANGE_APPLICATION:
			// 2：勤務変更申請
			return require.findAppWorkCg(companyId, appID).orElse(null);
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請
			return require.findBusinessTripApp(companyId, appID).orElse(null);
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請
			return require.findGoBack(companyId, appID).orElse(null);
		case HOLIDAY_WORK_APPLICATION:
			// TODO: 6：休日出勤申請
			return null;
		case STAMP_APPLICATION:
			// 7：打刻申請
			return require.findAppStamp(companyId, appID).orElse(null);
		case ANNUAL_HOLIDAY_APPLICATION:
			// TODO: 8：時間休暇申請
			return null;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: 遅刻早退取消申請
			return require.findArrivedLateLeaveEarly(companyId, appID, app).orElse(null);
		case COMPLEMENT_LEAVE_APPLICATION:
			// TODO: 申請.振休振出申請
			return null;

		case OPTIONAL_ITEM_APPLICATION:
			// TODO: 15：任意項目
			return null;

		default:
			return null;
		}
	}

	public static interface Require {

		public Optional<AppWorkChange> findAppWorkCg(String companyId, String appID);

		public Optional<GoBackDirectly> findGoBack(String companyId, String appID);

		public Optional<AppStamp> findAppStamp(String companyId, String appID);

		public Optional<ArrivedLateLeaveEarly> findArrivedLateLeaveEarly(String companyId, String appID, Application application);

		public Optional<BusinessTrip> findBusinessTripApp(String companyId, String appID);
	}
}
