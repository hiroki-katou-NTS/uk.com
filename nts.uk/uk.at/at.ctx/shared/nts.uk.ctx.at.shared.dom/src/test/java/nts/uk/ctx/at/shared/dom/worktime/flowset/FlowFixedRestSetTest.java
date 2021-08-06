package nts.uk.ctx.at.shared.dom.worktime.flowset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class FlowFixedRestSetTest {

	@Test
	public void getters() {
		FlowFixedRestSet target = new FlowFixedRestSet(true, true, FlowFixedRestCalcMethod.REFER_MASTER);
		NtsAssert.invokeGetters(target);
	}

	@Test
	public void isConvertGoOutToBreak_true() {
		FlowFixedRestSet target = new FlowFixedRestSet(true, true, FlowFixedRestCalcMethod.STAMP_WHITOUT_REFER); //参照せずに打刻する
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isTrue();
	}

	@Test
	public void isConvertGoOutToBreak_false() {
		FlowFixedRestSet target = new FlowFixedRestSet(true, true, FlowFixedRestCalcMethod.REFER_MASTER); //マスタを参照する
		assertThat(target.isConvertGoOutToBreak(GoingOutReason.PRIVATE)).isFalse();
	}
}
