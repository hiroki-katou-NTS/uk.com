package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodActualResultCorrectionServiceTest {
    @Mocked
    private AnyPeriodActualResultCorrectionService.Require require;

    @Test
    public void test(@Mocked AnyPeriodRecordToAttendanceItemConverter converter) {
        new Expectations() {
            {
                require.find("employeeId", "frameCode");
                result = Optional.of(new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode")));
            }
            {
                converter.toAttendanceTime();
                result = Optional.of(new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode")));
            }
        };

        AnyPeriodActualResultCorrectionDetail result = AnyPeriodActualResultCorrectionService.create(
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
