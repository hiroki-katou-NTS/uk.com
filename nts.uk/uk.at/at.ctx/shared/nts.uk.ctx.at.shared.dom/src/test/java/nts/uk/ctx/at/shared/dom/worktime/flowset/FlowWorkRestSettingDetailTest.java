package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;

public class FlowWorkRestSettingDetailTest {

	@Test
	public void getters() {
		FlowWorkRestSettingDetail target = this.helper_FixTrue_FlowFalse();
		NtsAssert.invokeGetters(target);
	}

	private FlowWorkRestSettingDetail helper_FixTrue_FlowFalse() {
		return new FlowWorkRestSettingDetail(
				new FlowRestSet(
						false, //打刻を併用しない(外出を休憩として扱わない）
						FlowRestClockCalcMethod.USE_GOOUT_TIME_TO_CALCULATE,//休憩として扱う外出を外出時間として計算する
						RestClockManageAtr.IS_CLOCK_MANAGE),
				new FlowFixedRestSet(true, true, FlowFixedRestCalcMethod.STAMP_WHITOUT_REFER),//参照せずに打刻する
				false,
				new TimeRoundingSetting(0,0));
	}

	@Test
	public void isConvertGoOutToBreak_Fix() {
		FlowWorkRestSettingDetail target = this.helper_FixTrue_FlowFalse();
		assertThat(target.isConvertGoOutToBreak(true, GoingOutReason.PRIVATE)).isTrue();//固定休憩
	}

	@Test
	public void isConvertGoOutToBreak_Flow() {
		FlowWorkRestSettingDetail target = this.helper_FixTrue_FlowFalse();
		assertThat(target.isConvertGoOutToBreak(false, GoingOutReason.PRIVATE)).isFalse();//流動休憩
	}
}
