package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHAppReflectionSetting;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.gobackdirectly.ReflectGoBackDirectly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.lateleaveearly.ReflectArrivedLateLeaveEarly;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.ReflectAppStamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.workchange.ReflectWorkChangeApplication;

/**
 * @author thanh_nx
 *
 *         各申請反映のドメインモデルを取得する
 */
public class GetDomainReflectModelApp {

	public static ApplicationReflect process(Require require, String companyId, ApplicationTypeShare appType,
			Optional<Object> typeDaikyu) {

		// 反映する申請の申請種類をもとに、反映条件のドメインモデルを取得する
		switch (appType) {
		case OVER_TIME_APPLICATION:
			// TODO: 0：残業申請の反映
			return null;
		case ABSENCE_APPLICATION:
			// TODO: 1：休暇申請の反映
			return null;
		case WORK_CHANGE_APPLICATION:
			// 2：勤務変更申請の反映
			return require.findReflectWorkCg(companyId).orElse(null);
		case BUSINESS_TRIP_APPLICATION:
			// 3：出張申請の反映
			return require.findReflectBusinessTripApp(companyId).orElse(null);
		case GO_RETURN_DIRECTLY_APPLICATION:
			// 4：直行直帰申請の反映
			return require.findReflectGoBack(companyId).orElse(null);
		case HOLIDAY_WORK_APPLICATION:
			// TODO: 6：休日出勤申請の反映
			return null;
		case STAMP_APPLICATION:
			// 7：打刻申請の反映
			return require.findReflectAppStamp(companyId).orElse(null);
		case ANNUAL_HOLIDAY_APPLICATION:
			// TODO: 8：時間休暇申請の反映
			return null;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			// 9: 遅刻早退取消申請の反映
			return require.findReflectArrivedLateLeaveEarly(companyId).orElse(null);
		case COMPLEMENT_LEAVE_APPLICATION:
			// TODO: 申請.振休振出申請
			return null;

		case OPTIONAL_ITEM_APPLICATION:
			// TODO: 15：任意項目の反映
			return null;

		default:
			return null;
		}
	}

	public static interface Require {
		/**
		 * 
		 * require{ 申請反映設定を取得する(会社ID、申請種類） }
		 * 
		 * SHRequestSettingAdapter
		 */
		public Optional<SHAppReflectionSetting> getAppReflectionSetting(String companyId, ApplicationTypeShare appType);

		public Optional<ReflectWorkChangeApplication> findReflectWorkCg(String companyId);

		public Optional<ReflectGoBackDirectly> findReflectGoBack(String companyId);

		public Optional<ReflectAppStamp> findReflectAppStamp(String companyId);

		public Optional<ReflectArrivedLateLeaveEarly> findReflectArrivedLateLeaveEarly(String companyId);

		public Optional<ReflectBusinessTripApp> findReflectBusinessTripApp(String companyId);
	}
}
