package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.enums.StampSourceInfo;

/**
 * @author ThanhNX
 *
 *         勤務情報と労働条件元に直行直帰区分を判断する
 */
public class DetermineClassifiByWorkInfoCond {

	// 勤務情報と労働条件元に直行直帰区分を判断する
	public static AutoStampSetClassifi determine(WorkingConditionItem workCondItem,
			WorkInfoOfDailyAttendance workInformation) {
		// 「自動打刻セット区分」をクリア
		AutoStampSetClassifi autoStamp = new AutoStampSetClassifi(NotUseAtr.NOTUSE, null, NotUseAtr.NOTUSE, null);
		if (workCondItem.getAutoStampSetAtr() == NotUseAtr.USE) {
			// 「自動打刻セット区分」をセットする
			autoStamp = new AutoStampSetClassifi(NotUseAtr.USE, StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO,
					NotUseAtr.USE, StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO);
		}

		// INPUT．「日別実績の勤務情報」を確認する
		if (workInformation.getGoStraightAtr() == NotUseAttribute.Use) {
			autoStamp.setAttendanceReflect(NotUseAtr.USE);
			autoStamp.setAttendanceStamp(StampSourceInfo.GO_STRAIGHT);
		}

		if (workInformation.getBackStraightAtr() == NotUseAttribute.Use) {
			autoStamp.setLeaveWorkReflect(NotUseAtr.USE);
			autoStamp.setLeaveStamp(StampSourceInfo.GO_STRAIGHT);
		}
		return autoStamp;
	}

	/**
	 * 自動打刻セット区分
	 */
	@AllArgsConstructor
	@Data
	public static class AutoStampSetClassifi {

		/**
		 * 出勤反映
		 */
		private NotUseAtr attendanceReflect;

		/**
		 * 出勤打刻元
		 */
		private StampSourceInfo attendanceStamp;

		/**
		 * 退勤反映
		 */
		private NotUseAtr leaveWorkReflect;

		/**
		 * 退勤打刻元
		 */
		private StampSourceInfo leaveStamp;

		public boolean isReflect() {
			return attendanceReflect == NotUseAtr.USE || leaveWorkReflect == NotUseAtr.USE;
		}

	}
}
