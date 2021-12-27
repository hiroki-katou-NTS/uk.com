package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

/**
 * @author daiki_ichioka
 *
 */
public class GoOutTimeRoundingSettingTest {

	@Test
	public void getters() {
		GoOutTimeRoundingSetting target = new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING, TimeRoundingSetting.oneMinDown());
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void getRoundingSetTest_individual() {
		GoOutTimeRoundingSetting target = new GoOutTimeRoundingSetting(
				RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING, //個別の丸め
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN)); //逆丸め用：5分切り捨て
		
		//result == 15分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_15MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
	
	@Test
	public void getRoundingSetTest_reverse() {
		GoOutTimeRoundingSetting target = new GoOutTimeRoundingSetting(
				RoundingGoOutTimeSheet.REVERSE_ROUNDING_EACH_TIMEZONE, // 各時間帯の逆丸め
				new TimeRoundingSetting(Unit.ROUNDING_TIME_15MIN, Rounding.ROUNDING_UP)); //15分切り上げ
		
		TimeRoundingSetting result = target.getRoundingSet(new TimeRoundingSetting(Unit.ROUNDING_TIME_5MIN, Rounding.ROUNDING_DOWN)); //逆丸め用：5分切り捨て
		
		//result == 5分切り上げ
		assertThat(result.getRoundingTime()).isEqualTo(Unit.ROUNDING_TIME_5MIN);
		assertThat(result.getRounding()).isEqualTo(Rounding.ROUNDING_UP);
	}
}
