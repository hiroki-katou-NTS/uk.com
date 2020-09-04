package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.stamp.AppStampCombinationAtrShare;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.workinfo.timereflectfromworkinfo.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         出退勤の打刻を反映する
 */
public class ReflectTimeStamp {

	/**
	 * @param require
	 * @param 日別勤怠(work）
	 * @param 打刻反映範囲
	 * @param 申請時刻
	 * @param 反映する時刻
	 * @param 打刻区分
	 */
	public static ReflectTimeStampResult reflect(Require require, DailyRecordOfApplication dailyRecordApp,
			OutputTimeReflectForWorkinfo timeReflectWork, AttendanceTime attendanceTime, TimeWithDayAttr attr,
			AppStampCombinationAtrShare appStampComAtr, Optional<Stamp> stamp) {

		if (!stamp.isPresent())
			return new ReflectTimeStampResult();
		// 打刻区分チェック
		OutputCheckRangeReflectAttd stampOut = null;
		if (groupAtt(appStampComAtr)) {
			stampOut = require.checkRangeReflectAttd(stamp.get(), timeReflectWork.getStampReflectRangeOutput(),
					dailyRecordApp.getDomain());
		} else {
			stampOut = require.checkRangeReflectOut(stamp.get(), timeReflectWork.getStampReflectRangeOutput(),
					dailyRecordApp.getDomain());
		}

		return createWorkTypeNo(stampOut, dailyRecordApp.getDomain());
	}

	private static ReflectTimeStampResult createWorkTypeNo(OutputCheckRangeReflectAttd attd, IntegrationOfDaily daily) {

		if (attd == OutputCheckRangeReflectAttd.OUT_OF_RANGE) {
			// 範囲外
			return new ReflectTimeStampResult();
		} else if (attd == OutputCheckRangeReflectAttd.FIRST_TIME) {
			// 1回目勤務の出勤打刻反映範囲内
			return new ReflectTimeStampResult(daily, true, new WorkNo(1));
		} else {
			// 2回目勤務の出勤打刻反映範囲内
			return new ReflectTimeStampResult(daily, true, new WorkNo(2));
		}
	}

	public static interface Require {

		// CheckRangeReflectAttd
		public OutputCheckRangeReflectAttd checkRangeReflectAttd(Stamp stamp,
				StampReflectRangeOutput stampReflectRangeOutput, IntegrationOfDaily integrationOfDaily);

		// CheckRangeReflectLeavingWork
		public OutputCheckRangeReflectAttd checkRangeReflectOut(Stamp stamp, StampReflectRangeOutput s,
				IntegrationOfDaily integrationOfDaily);
	}

	public static boolean groupAtt(AppStampCombinationAtrShare atr) {
		if (atr == AppStampCombinationAtrShare.ATTENDANCE || atr == AppStampCombinationAtrShare.EARLY
				|| atr == AppStampCombinationAtrShare.HOLIDAY) {
			return true;
		}
		return false;
	}

	@AllArgsConstructor
	@Getter
	@Setter
	public static class ReflectTimeStampResult {

		private IntegrationOfDaily daily;

		private boolean reflect;

		private WorkNo workNoReflect;

		public ReflectTimeStampResult() {
			this.reflect = false;
		}
	}
}
