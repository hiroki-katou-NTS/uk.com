package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.LeaveSetAdded;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 日別実績の時間休暇使用時間のテスト
 */
public class TimevacationUseTimeOfDailyTest {
	
	/**
	 * 加算使用時間の計算
	 * 加算区分全ての場合のテスト
	 * 
	 */
	@Test
	public void testCalcTotalVacation() {

		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													new AttendanceTime(120), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));

		int actualResult = timeDaily.calcTotalVacationAddTime(Optional.empty(), AdditionAtr.All);

		assertThat(actualResult).as("A1").isEqualTo(1260);

	}

	/**
	 * 加算使用時間の計算
	 * 加算区分　＝　就業加算時間のみの場合　かつ　年休加算しない、特別休暇加算しない
	 */
	@Test
	public void testCalcTotalVacation2() {
		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													new AttendanceTime(120), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));
		int actualResult = timeDaily
				.calcTotalVacationAddTime(
						createHASet(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),AdditionAtr.WorkingHoursOnly);

		assertThat(actualResult).as("A2").isEqualTo(1080);

	}
	
	/**
	 * 加算使用時間の計算
	 * 加算区分　＝　就業加算時間のみの場合　かつ　年休加算する、特別休暇加算しない
	 */
	@Test
	public void testCalcTotalVacation3() {
		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													new AttendanceTime(120), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));

		int actualResult = timeDaily
				.calcTotalVacationAddTime(
						createHASet(NotUseAtr.USE, NotUseAtr.NOT_USE),AdditionAtr.WorkingHoursOnly);

		assertThat(actualResult).as("A3").isEqualTo(1140);

	}
	
	/**
	 * 加算使用時間の計算
	 * 加算区分　＝　就業加算時間のみの場合　かつ　年休加算しない、特別休暇加算する
	 */
	@Test
	public void testCalcTotalVacation4() {
		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													new AttendanceTime(120), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));

		int actualResult = timeDaily
				.calcTotalVacationAddTime(
						createHASet(NotUseAtr.NOT_USE, NotUseAtr.USE),AdditionAtr.WorkingHoursOnly);

		assertThat(actualResult).as("A4").isEqualTo(1200);

	}
	
	/**
	 * 加算使用時間の計算
	 * 加算区分　＝　就業加算時間のみの場合　かつ　年休加算する、特別休暇加算する
	 */
	@Test
	public void testCalcTotalVacation5() {
		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													new AttendanceTime(120), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));

		int actualResult = timeDaily
				.calcTotalVacationAddTime(
						createHASet(NotUseAtr.USE, NotUseAtr.USE),AdditionAtr.WorkingHoursOnly);

		assertThat(actualResult).as("A5").isEqualTo(1260);

	}
	
	/**
	 * 
	 * 合計使用時間の計算
	 * 合計時間を求めるテスト
	 * 
	 */
	@Test
	public void testTotalVacation() {

		TimevacationUseTimeOfDaily timeDaily = new TimevacationUseTimeOfDaily(
													new AttendanceTime(60),
													new AttendanceTime(120), 
													new AttendanceTime(180), 
													new AttendanceTime(240), 
													Optional.empty(),
													new AttendanceTime(300), 
													new AttendanceTime(360));

		int actualResult = timeDaily.totalVacationAddTime();

		assertThat(actualResult).as("B").isEqualTo(1260);

	}
	
	
	private Optional<HolidayAddtionSet> createHASet(NotUseAtr annualHoliday,NotUseAtr specialHoliday){
		return Optional.of(new HolidayAddtionSet(
				null, null, NotUseAtr.NOT_USE, null, 
				new LeaveSetAdded(annualHoliday, null, specialHoliday),
				null));
	}
	
}
