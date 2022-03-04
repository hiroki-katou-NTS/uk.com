package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.*;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodActualResultCorrectionDetailTest {
    @Test
    public void testGetters() {
        AnyPeriodActualResultCorrectionDetail detail = new AnyPeriodActualResultCorrectionDetail(
                new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode")),
                new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode"))
        );
        NtsAssert.invokeGetters(detail);
    }

    @Test
    public void testGetCalculatedItems(@Mocked AnyPeriodRecordToAttendanceItemConverter converter) {
        AttendanceTimeOfAnyPeriod correctedDomain = new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode"));
        AttendanceTimeOfAnyPeriod calculatedDomain = new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode"));

        AnyPeriodActualResultCorrectionDetail detail = new AnyPeriodActualResultCorrectionDetail(
                correctedDomain,
                calculatedDomain
        );

        new Expectations() {
            {
                converter.withAttendanceTime(correctedDomain).convert((Collection<Integer>) any);
                result = Arrays.asList(
                        new ItemValue(ValueType.TIME_WITH_DAY, "", 31, "600"),
                        new ItemValue(ValueType.TIME_WITH_DAY, "", 34, "600")
                );
                result = Arrays.asList(
                        new ItemValue(ValueType.TIME_WITH_DAY, "", 31, "500"),
                        new ItemValue(ValueType.TIME_WITH_DAY, "", 34, "600")
                );
            }
        };

        CalculatedItemDetail result = detail.getCalculatedItems(converter);

        assertThat(result.getCalculatedItemIds().size()).isEqualTo(1);
        assertThat(result.getCalculatedItemIds().get(0)).isEqualTo(31);
    }
}
