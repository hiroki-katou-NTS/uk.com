package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import java.lang.reflect.Field;
import java.util.Collections;

import lombok.SneakyThrows;
import lombok.val;
import mockit.Injectable;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.StayingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;

/**
 * 日別勤怠の勤怠時間に関するHelper
 * ※集計処理に特化しています
 * @author kumiko_otake
 */
public class AttendanceTimeOfDailyAttendanceHelper {

	/* 共通 */
	@Injectable private static AttendanceTime atdTime;
	/* 日別勤怠の勤怠時間 */
	@Injectable private static WorkScheduleTimeOfDaily schedule;
	@Injectable private static StayingTimeOfDaily stay;
	@Injectable private static AttendanceTimeOfExistMinus budget;
	@Injectable private static AttendanceTimeOfExistMinus unEmploy;
	/* 日別勤怠の勤務実績時間 */
	@Injectable private static ConstraintTime constTime;
	@Injectable private static DivergenceTimeOfDaily divTime;
	/* 日別勤怠の総労働時間 */
	@Injectable private static ExcessOfStatutoryTimeOfDaily excess;
	@Injectable private static BreakTimeOfDaily breakTime;
	@Injectable private static RaiseSalaryTimeOfDailyPerfor raise;
	@Injectable private static WorkTimes workTimes;
	@Injectable private static TemporaryTimeOfDaily temporaly;
	@Injectable private static ShortWorkTimeOfDaily shorttime;
	@Injectable private static HolidayOfDaily holiday;
	@Injectable private static IntervalTimeOfDaily interval;
	/* 日別勤怠の所定内時間 */
	@Injectable private static WithinStatutoryMidNightTime withinMidnite;



	/**
	 * 日別勤怠の勤怠時間を作成する
	 * @param withinTime 就業時間
	 * @param withinAmount 就業時間金額
	 * @param premiumTime 割増労働時間合計
	 * @param premiumAmount 割増金額合計
	 * @return 日別勤怠の勤怠時間
	 */
	public static AttendanceTimeOfDailyAttendance createWithTimeAndAmount(
			int withinTime, int withinAmount
		,	int premiumTime, int premiumAmount
	) {
		return new AttendanceTimeOfDailyAttendance(
						schedule
					,	new ActualWorkingTimeOfDaily(
								atdTime, constTime, atdTime
							,	new TotalWorkingTime(
											atdTime, atdTime, atdTime, AttendanceTimeOfDailyAttendanceHelper.createWithinOfDaily(withinTime, withinAmount), excess
										,	Collections.emptyList(), Collections.emptyList(), breakTime, Collections.emptyList()
										,	raise, workTimes, temporaly, shorttime, holiday, interval
									)
							, divTime, AttendanceTimeOfDailyAttendanceHelper.createPremiumOfDaily(premiumTime, premiumAmount) )
					,	stay, budget, unEmploy
				);
	}

	/**
	 * 日別勤怠の勤怠時間を作成する
	 * @param within 就業時間
	 * @param premium 割増労働時間合計
	 * @return 日別勤怠の勤怠時間
	 */
	public static AttendanceTimeOfDailyAttendance createWithTime(int within, int premium) {

		return AttendanceTimeOfDailyAttendanceHelper.createWithTimeAndAmount( within, 0, premium, 0 );

	}

	/**
	 * 日別勤怠の勤怠時間を作成する
	 * @param within 就業時間
	 * @param premium 割増労働時間合計
	 * @return 日別勤怠の勤怠時間
	 */
	public static AttendanceTimeOfDailyAttendance createWithAmount(int within, int premium) {

		return AttendanceTimeOfDailyAttendanceHelper.createWithTimeAndAmount( 480, within, 0, premium );

	}

	/**
	 * 日別勤怠の所定内時間を作成する
	 * @param time 就業時間
	 * @param amount 就業時間金額
	 * @return 日別勤怠の所定内時間
	 */
	protected static WithinStatutoryTimeOfDaily createWithinOfDaily(int time, int amount) {

		val within = new WithinStatutoryTimeOfDaily(atdTime, atdTime, atdTime, withinMidnite);

		// 就業時間
		AttendanceTimeOfDailyAttendanceHelper.setValue( within , "workTime", new AttendanceTime( time ) );
		// 就業時間金額
		AttendanceTimeOfDailyAttendanceHelper.setValue( within , "withinWorkTimeAmount", new AttendanceAmountDaily( amount ) );

		return within;

	}


	/**
	 * 日別勤怠の割増時間を作成する
	 * @param time 割増労働時間合計
	 * @param amount 割増金額合計
	 * @return 日別勤怠の割増時間
	 */
	protected static PremiumTimeOfDailyPerformance createPremiumOfDaily(int time, int amount) {

		val premium = new PremiumTimeOfDailyPerformance(Collections.emptyList());

		// 割増労働時間合計
		AttendanceTimeOfDailyAttendanceHelper.setValue( premium , "totalWorkingTime", new AttendanceTime( time ) );
		// 割増金額合計
		AttendanceTimeOfDailyAttendanceHelper.setValue( premium , "totalAmount", new AttendanceAmountDaily( amount ) );


		return premium;

	}


	/**
	 * フィールドに値を設定する
	 * @param instance
	 * @param name
	 * @param value
	 */
	@SneakyThrows
	protected static <T> void setValue(Object instance, String name, T value) {
		Field field = instance.getClass().getDeclaredField( name );
		field.setAccessible( true );
		field.set( instance, value );
	}
}
