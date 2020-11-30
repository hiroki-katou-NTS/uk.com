package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork.OutputCheckRangeReflectAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.stamp.EngraveShareAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.shr.com.time.AttendanceClock;
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
			OutputTimeReflectForWorkinfo timeReflectWork, AttendanceClock attendanceTime, TimeWithDayAttr attr,
			EngraveShareAtr appStampComAtr, Optional<Stamp> stamp) {

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

	public static boolean groupAtt(EngraveShareAtr atr) {
		if (atr == EngraveShareAtr.ATTENDANCE  || atr == EngraveShareAtr.EARLY
				|| atr == EngraveShareAtr.HOLIDAY) {
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
