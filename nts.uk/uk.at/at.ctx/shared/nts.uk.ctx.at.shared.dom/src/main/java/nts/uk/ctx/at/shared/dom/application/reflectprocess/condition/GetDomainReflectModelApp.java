package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.adapter.application.reflect.SHAppReflectionSetting;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.businesstrip.ReflectBusinessTripApp;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication.TimeLeaveApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.workchangeapp.ReflectWorkChangeApp;

/**
 * @author thanh_nx
 *
 *         各申請反映のドメインモデルを取得する
 */
public class GetDomainReflectModelApp {

	public static Object process(Require require, String companyId, ApplicationTypeShare appType,
			Optional<Object> typeDaikyu) {

		// 反映する申請の申請種類をもとに、反映条件のドメインモデルを取得する
		switch (appType) {
		case OVER_TIME_APPLICATION:
			// 0：残業申請の反映
			return require.findOvertime(companyId).orElse(null);
		case ABSENCE_APPLICATION:
			// 1：休暇申請の反映
			return require.findVacationApp(companyId).orElse(null);
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
			//  8：時間休暇申請の反映
			return require.findReflectTimeLeav(companyId).orElse(null);
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

		public Optional<ReflectWorkChangeApp> findReflectWorkCg(String companyId);

		public Optional<GoBackReflect> findReflectGoBack(String companyId);

		public Optional<StampAppReflect> findReflectAppStamp(String companyId);

		public Optional<LateEarlyCancelReflect> findReflectArrivedLateLeaveEarly(String companyId);

		public Optional<ReflectBusinessTripApp> findReflectBusinessTripApp(String companyId);
		
		public Optional<TimeLeaveApplicationReflect> findReflectTimeLeav(String companyId);
		
		public Optional<AppReflectOtHdWork> findOvertime(String companyId);
		
		public Optional<VacationApplicationReflect> findVacationApp(String companyId);
	}
}
