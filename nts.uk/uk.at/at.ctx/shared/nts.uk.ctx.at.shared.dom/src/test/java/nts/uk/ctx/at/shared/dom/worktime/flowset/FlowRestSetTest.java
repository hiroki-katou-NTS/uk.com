package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;

public class FlowRestSetTest {

	@Test
	public void getters() {
		FlowRestSet target = new FlowRestSet(true, FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE, RestClockManageAtr.IS_CLOCK_MANAGE);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void isConvertGoOutToBreak_true() {
		FlowRestSet target = new FlowRestSet(
				true, //打刻を併用する(外出を休憩として扱う）
				FlowRestClockCalcMethod.USE_REST_TIME_TO_CALCULATE,//休憩として扱う外出を休憩時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isTrue();
	}

	@Test
	public void isConvertGoOutToBreak_false() {
		FlowRestSet target = new FlowRestSet(
				false, //打刻を併用しない(外出を休憩として扱わない）
				FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE,//休憩として扱う外出を外出時間として計算する
				RestClockManageAtr.IS_CLOCK_MANAGE);
		
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PUBLIC)).isFalse();
	}
}
