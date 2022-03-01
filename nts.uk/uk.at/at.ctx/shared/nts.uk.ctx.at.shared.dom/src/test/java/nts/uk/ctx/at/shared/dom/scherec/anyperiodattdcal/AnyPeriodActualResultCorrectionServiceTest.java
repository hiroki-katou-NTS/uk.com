package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodActualResultCorrectionServiceTest {
    @Injectable
    AnyPeriodActualResultCorrectionService.Require require;

    @Test
    public void test(@Mocked AnyPeriodActualResultCorrectionService service) {
        AnyPeriodActualResultCorrectionDetail result = service.create(
                require,
                "frameCode",
                "employeeId",
                new ArrayList<>()
        );
        assertThat(result.getAfterCorrection()).isNotNull();
        assertThat(result.getAfterCalculation()).isNotNull();
        assertThat(result.getAfterCorrection().getEmployeeId()).isEqualTo(result.getAfterCalculation().getEmployeeId());
    }
}
