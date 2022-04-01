package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class ConditionAtrTest {

	@Test
	public void toGoingOutReasonTest_break() {
		assertThat(ConditionAtr.BREAK.toGoingOutReason()).isNotPresent();
	}

	@Test
	public void toGoingOutReasonTest_private() {
		assertThat(ConditionAtr.PrivateGoOut.toGoingOutReason().get()).isEqualTo(GoingOutReason.PRIVATE);
	}

	@Test
	public void toGoingOutReasonTest_public() {
		assertThat(ConditionAtr.PublicGoOut.toGoingOutReason().get()).isEqualTo(GoingOutReason.PUBLIC);
	}

	@Test
	public void toGoingOutReasonTest_compensation() {
		assertThat(ConditionAtr.CompesationGoOut.toGoingOutReason().get()).isEqualTo(GoingOutReason.COMPENSATION);
	}

	@Test
	public void toGoingOutReasonTest_union() {
		assertThat(ConditionAtr.UnionGoOut.toGoingOutReason().get()).isEqualTo(GoingOutReason.UNION);
	}

	@Test
	public void toGoingOutReasonTest_child() {
		assertThat(ConditionAtr.Child.toGoingOutReason()).isNotPresent();
	}

	@Test
	public void toGoingOutReasonTest_care() {
		assertThat(ConditionAtr.Care.toGoingOutReason()).isNotPresent();
	}
}
