package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionEditStateCreateServiceTest {
    @Test
    public void testCreateStates() {
        List<AnyPeriodCorrectionEditingState> states = AnyPeriodCorrectionEditStateCreateService.create(
                "frameCode",
                "employee1",
                "employee2",
                Arrays.asList(1, 2, 3)
        );
        assertThat(states.size()).isEqualTo(3);
    }
}
