package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.math.BigDecimal;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;

/**
 * 集計対象の勤怠時間項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.集計対象の勤怠時間
 * @author kumiko_otake
 */
@Getter
@RequiredArgsConstructor
public enum AttendanceTimesForAggregation {

	/** 総労働時間 **/
	WORKING_TOTAL( 0 ),
	/** 就業時間 **/
	WORKING_WITHIN( 1 ),
	/** 時間外時間 **/
	WORKING_EXTRA( 2 ),
	/** 夜勤時間 **/
	NIGHTSHIFT( 3 ),
	;


	/** 内部値 **/
	private final int value;


	/**
	 * 時間を取得する
	 * @param dailyAttendance 日別勤怠の勤怠時間
	 * @return 時間(分)
	 */
	public BigDecimal getTime(AttendanceTimeOfDailyAttendance dailyAttendance) {

		// 総労働時間
		if ( this == WORKING_TOTAL ) {
			// 就業時間＋時間外時間
			return Stream.of( WORKING_WITHIN, WORKING_EXTRA )
							.map( e -> e.getTime( dailyAttendance ) )
							.reduce( BigDecimal.ZERO, BigDecimal::add );
		}

		AttendanceTime time = null;
		switch( this ) {
			case WORKING_WITHIN:	// 就業時間
				time = dailyAttendance.getActualWorkingTimeOfDaily()	// 日別勤怠の勤務時間
								.getTotalWorkingTime()					// 日別勤怠の総労働時間
								.getWithinStatutoryTimeOfDaily()		// 日別勤怠の所定内時間
								.getWorkTime();							// 就業時間
				break;
			case WORKING_EXTRA:		// 時間外時間
				time = dailyAttendance.getActualWorkingTimeOfDaily()	// 日別勤怠の勤務時間
								.getPremiumTimeOfDailyPerformance()		// 日別勤怠の割増時間
								.getTotalWorkingTime();					// 割増労働時間合計
				break;
			case NIGHTSHIFT:		// 夜勤時間
				// TODO 日別勤怠の医療項目決定待ち
				time = dailyAttendance.getMedicalCareTime()				// 日別勤怠の医療時間
								.getWorkTime();							// 勤務時間
				break;
			default:
				throw new RuntimeException("Value is out of range.");

		}

		return BigDecimal.valueOf( time.valueAsMinutes() );

	}

}
