package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.StateOfEditMonthly;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionEditingStateTest {
    @Test
    public void testGetters() {
        AnyPeriodCorrectionEditingState state = AnyPeriodCorrectionEditingState.create(
                "employee1",
                "employee2",
                "frameCode",
                33
        );
        NtsAssert.invokeGetters(state);
    }

    @Test
    public void testCorrectMyself() {
        AnyPeriodCorrectionEditingState state = AnyPeriodCorrectionEditingState.create(
                "employee1",
                "employee1",
                "frameCode",
                33
        );
        assertThat(state.getState()).isEqualTo(StateOfEditMonthly.HAND_CORRECTION_MYSELF);
    }

    @Test
    public void testCorrectByOther() {
        AnyPeriodCorrectionEditingState state = AnyPeriodCorrectionEditingState.create(
                "employee1",
                "employee2",
                "frameCode",
                33
        );
        assertThat(state.getState()).isEqualTo(StateOfEditMonthly.HAND_CORRECTION_OTHER);
    }
}
