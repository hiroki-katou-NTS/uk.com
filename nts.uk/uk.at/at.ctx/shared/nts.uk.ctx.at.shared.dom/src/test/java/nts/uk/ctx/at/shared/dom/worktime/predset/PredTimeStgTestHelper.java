package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.Arrays;
import java.util.List;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class PredTimeStgTestHelper {

	public static class Time {
		/**
		 * 勤怠時刻を作成する
		 * @param hour 時
		 * @param minute 分
		 * @return 勤怠時刻
		 */
		public static AttendanceTime toAtdTime(int hour, int minute) {
			return new AttendanceTime( hour * 60 + minute );
		}
	}

	public static class Timezone {

		public static TimezoneUse from(int workNo, boolean isUse, TimeSpanForCalc timeSpan) {
			return new TimezoneUse(
								timeSpan.getStart(), timeSpan.getEnd()
							,	isUse ? UseSetting.USE : UseSetting.NOT_USE
							,	workNo
						);
		}

		public static TimezoneUse createUsed(int workNo, TimeSpanForCalc timeSpan) {
			return Timezone.from(workNo, true, timeSpan);
		}

		public static TimezoneUse createUnused(int workNo) {
			return Timezone.from(workNo, false, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(0, 0), TimeWithDayAttr.hourMinute(0, 0)));
		}

		public static List<TimezoneUse> createDummyList() {
			return Arrays.asList(
							Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 23, 30 )))
						,	Timezone.createUnused(2)
					);
		}
	}

	public static class PredTimeStg {

		/**
		 * 所定時間(DUMMY)を作成する
		 * @return 所定時間(DUMMY)
		 */
		public static PredetermineTime createDummyPredTime() {
			val predTime = new BreakDownTimeDay(Time.toAtdTime(8, 0), Time.toAtdTime(4, 0), Time.toAtdTime(4, 0));
			return new PredetermineTime(predTime, predTime);
		}

		/**
		 * 所定時間帯設定(DUMMY)を作成する
		 * @return 所定時間帯設定(DUMMY)
		 */
		public static PrescribedTimezoneSetting createDummyPrscTzStg() {
			return new PrescribedTimezoneSetting(
						TimeWithDayAttr.hourMinute( 12,  0 )
					,	TimeWithDayAttr.hourMinute( 13,  0 )
					,	Timezone.createDummyList()
				);
		}

		/**
		 * 所定時間設定を作成する
		 * @param code 就業時間帯コード
		 * @param startClock 1日の開始時刻
		 * @param rangeOfDay 1日の範囲(時間)
		 * @param prscTzStg 所定時間帯設定
		 * @return 所定時間設定
		 */
		public static PredetemineTimeSetting create(String code, TimeWithDayAttr startClock, AttendanceTime rangeOfDay, PrescribedTimezoneSetting prscTzStg) {
			return new PredetemineTimeSetting("CID", rangeOfDay, new WorkTimeCode(code)
						, PredTimeStg.createDummyPredTime(), false, prscTzStg, startClock, false
					);
		}

	}
}