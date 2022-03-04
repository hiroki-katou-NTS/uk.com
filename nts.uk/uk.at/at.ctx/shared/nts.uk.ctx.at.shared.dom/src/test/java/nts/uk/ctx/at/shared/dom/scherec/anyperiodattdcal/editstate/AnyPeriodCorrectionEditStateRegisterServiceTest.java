package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionEditStateRegisterServiceTest {
    @Injectable
    private AnyPeriodCorrectionEditStateRegisterService.Require require;

    @Test
    public void testRegister() {
        List<AnyPeriodCorrectionEditingState> states = AnyPeriodCorrectionEditStateCreateService.create(
                "frameCode",
                "employee1",
                "employee2",
                Arrays.asList(1, 2, 3)
        );

        List<AtomTask> tasks = AnyPeriodCorrectionEditStateRegisterService.register(require, states);

        assertThat(tasks.size()).isEqualTo(3);
    }
}
