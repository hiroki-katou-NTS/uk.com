package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 人件費・時間の集計単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.人件費・時間の集計単位
 * @author dan_pv
 *
 */
@RequiredArgsConstructor
public enum AggregationUnitOfLaborCosts {

	/** 合計 **/
	TOTAL( 0, AttendanceTimesForAggregation.WORKING_TOTAL ),
	/** 就業時間 **/
	WITHIN( 1, AttendanceTimesForAggregation.WORKING_WITHIN ),
	/** 時間外時間 **/
	EXTRA( 2, AttendanceTimesForAggregation.WORKING_EXTRA ),
	;


	public final int value;

	/** 対応する集計対象の勤怠時間 **/
	private final AttendanceTimesForAggregation attendanceTime;


	public static AggregationUnitOfLaborCosts of(int value) {
		return EnumAdaptor.valueOf(value, AggregationUnitOfLaborCosts.class);
	}


	/**
	 * 金額を取得する
	 * @param dailyAttendance 日別勤怠の勤怠時間
	 * @return 金額
	 */
	public BigDecimal getAmount(AttendanceTimeOfDailyAttendance dailyAttendance) {

		// 総労働時間
		if ( this == TOTAL ) {
			// 就業時間＋時間外時間
			return Stream.of( WITHIN, EXTRA )
							.map( e -> e.getAmount( dailyAttendance ) )
							.reduce( BigDecimal.ZERO, BigDecimal::add );
		}

		AttendanceAmountDaily amount = null;
		switch( this ) {
			case WITHIN:	// 就業時間
				amount = dailyAttendance.getActualWorkingTimeOfDaily()	// 日別勤怠の勤務時間
								.getTotalWorkingTime()					// 日別勤怠の総労働時間
								.getWithinStatutoryTimeOfDaily()		// 日別勤怠の所定内時間
								.getWithinWorkTimeAmount();				// 就業時間金額
				break;
			case EXTRA:		// 時間外時間
				amount = dailyAttendance.getActualWorkingTimeOfDaily()	// 日別勤怠の勤務時間
								.getPremiumTimeOfDailyPerformance()		// 日別勤怠の割増時間
								.getTotalAmount();						// 割増金額合計
				break;
			default:
				throw new RuntimeException("Value is out of range.");
		}

		return BigDecimal.valueOf( amount.v() );

	}


	/**
	 * 時間を取得する
	 * @param dailyAttendance 日別勤怠の勤怠時間
	 * @return 時間(分)
	 */
	public BigDecimal getTime(AttendanceTimeOfDailyAttendance dailyAttendance) {
		return this.attendanceTime.getTime(dailyAttendance);
	}

}
