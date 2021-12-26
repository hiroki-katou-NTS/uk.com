package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class GoOutTimezoneRoundingSetTest {

	@Test
	public void getters() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				TimeRoundingSetting.oneMinDown(),
				TimeRoundingSetting.oneMinDown(),
				TimeRoundingSetting.oneMinDown());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getRoundingSetTest_holiday() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //休日出勤：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP), //就業時間内：15分切り上げ
				new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN)); //残業：30分切り捨て
		
		TimeRoundingSetting result = target.getRoundingSet(ActualWorkTimeSheetAtr.HolidayWork, // 休日出勤
				GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown());
		
		//result == 5分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getRoundingSetTest_witnin() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //休日出勤：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP), //就業時間内：15分切り上げ
				new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN)); //残業：30分切り捨て
		
		TimeRoundingSetting result = target.getRoundingSet(ActualWorkTimeSheetAtr.WithinWorkTime, // 就業時間内
				GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown());
		
		//result == 15分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_15MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}

	@Test
	public void getRoundingSetTest_overTime() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //休日出勤：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP), //就業時間内：15分切り上げ
				new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN)); //残業：30分切り捨て
		
		TimeRoundingSetting result = target.getRoundingSet(ActualWorkTimeSheetAtr.OverTimeWork, // 残業
				GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown());
		
		//result == 30分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_30MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getRoundingSetTest_earlyWork() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //休日出勤：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP), //就業時間内：15分切り上げ
				new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN)); //残業：30分切り捨て
		
		TimeRoundingSetting result = target.getRoundingSet(ActualWorkTimeSheetAtr.EarlyWork, // 早出残業
				GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown());
		
		//result == 30分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_30MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}

	@Test
	public void getRoundingSetTest_statutoryOverTimeWork() {
		GoOutTimezoneRoundingSet target = GoOutSetHelper.createGoOutTimezoneRoundingSet(
				new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN), //休日出勤：5分切り捨て
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP), //就業時間内：15分切り上げ
				new TimeRoundingSetting(Unit.ROUNDING_TIME_30MIN, Rounding.ROUNDING_DOWN)); //残業：30分切り捨て
		
		TimeRoundingSetting result = target.getRoundingSet(ActualWorkTimeSheetAtr.StatutoryOverTimeWork, // 法定内残業
				GoingOutReason.PRIVATE, DeductionAtr.Appropriate, TimeRoundingSetting.oneMinDown());
		
		//result == 30分切り捨て
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_30MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_DOWN);
	}
}
