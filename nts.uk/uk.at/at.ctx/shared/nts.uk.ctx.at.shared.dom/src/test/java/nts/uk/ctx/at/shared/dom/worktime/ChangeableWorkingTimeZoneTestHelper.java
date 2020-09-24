package nts.uk.ctx.at.shared.dom.worktime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class ChangeableWorkingTimeZoneTestHelper {

	public static class TimeWizDayAtr {

		/**
		 * 時分から時刻(日区分付き)に変換する
		 * @param hour 時
		 * @param minute 分
		 * @return 時刻(日区分付き)
		 */
		public static TimeWithDayAttr from( int hour, int minute ) {
			return new TimeWithDayAttr( hour * 60 + minute );
		}

	}

	public static class TimeSpan {

		/**
		 * 開始時分と終了時分から計算用時間帯に変換する
		 * @param startH 開始 時
		 * @param startM 開始 分
		 * @param endH 終了 時
		 * @param endM 終了 分
		 * @return 計算用時間帯
		 */
		public static TimeSpanForCalc from( int startH, int startM, int endH, int endM ) {
			return TimeSpan.from( TimeWizDayAtr.from( startH, startM ), TimeWizDayAtr.from( endH, endM ) );
		}

		/**
		 * 開始時刻と終了時刻から計算用時間帯に変換する
		 * @param start 開始時刻(日区分付き)
		 * @param end 終了時刻(日区分付き)
		 * @return 計算用時間帯
		 */
		public static TimeSpanForCalc from( TimeWithDayAttr start, TimeWithDayAttr end ) {
			return new TimeSpanForCalc( start, end );
		}

	}

	public static class PerNo {

		/**
		 * 勤務NOごとの変更可能な勤務時間帯を作る(DUMMY)
		 * @param workNo 勤務NO
		 * @return 勤務NOごとの変更可能な勤務時間帯(DUMMY)
		 */
		public static ChangeableWorkingTimeZonePerNo createDummy(int workNo) {
			return ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					new WorkNo(workNo), TimeSpan.from( 8, ( 0 + workNo ), 20, ( 30 + workNo ) )
				);
		}

		/**
		 * 勤務NOごとの変更可能な勤務時間帯リストを作る(DUMMY)
		 * @param size リストのサイズ
		 * @return 勤務NOごとの変更可能な勤務時間帯リスト(DUMMY)
		 */
		public static List<ChangeableWorkingTimeZonePerNo> createDummyList(int size) {

			if (size < 0) {
				return Collections.emptyList();
			}

			return IntStream.rangeClosed( 1, size ).boxed()
					.map( e -> PerNo.createDummy(e) )
					.collect(Collectors.toList());
		}

	}


}
