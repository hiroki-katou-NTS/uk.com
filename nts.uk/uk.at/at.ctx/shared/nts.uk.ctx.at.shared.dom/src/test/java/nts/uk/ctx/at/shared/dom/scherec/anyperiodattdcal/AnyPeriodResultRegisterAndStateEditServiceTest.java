package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodResultRegisterAndStateEditServiceTest {
    @Injectable
    private AnyPeriodResultRegisterAndStateEditService.Require require;

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

        List<ItemValue> itemValues = new ArrayList<>();
        itemValues.add(new ItemValue(ValueType.TIME_WITH_DAY, "", 31, "600"));
        itemValues.add(new ItemValue(ValueType.TIME_WITH_DAY, "", 34, "600"));

        AnyPeriodResultRegistrationDetail result = AnyPeriodResultRegisterAndStateEditService.register(
                require,
                "frameCode",
                "employeeId",
                "employeeId",
                itemValues
        );

        assertThat(result.getProcesses().size()).isEqualTo(3);
        assertThat(result.getCorrectionDetail().getAfterCorrection().getEmployeeId()).isEqualTo("employeeId");
        assertThat(result.getCorrectionDetail().getAfterCalculation().getEmployeeId()).isEqualTo("employeeId");
    }
}
