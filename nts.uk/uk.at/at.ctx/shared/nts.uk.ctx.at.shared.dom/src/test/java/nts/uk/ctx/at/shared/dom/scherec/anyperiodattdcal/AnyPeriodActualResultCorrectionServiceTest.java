package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.*;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalCount;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
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
                result = Optional.of(AttendanceTimeOfAnyPeriod.of(
                        "employeeId",
                        new AnyAggrFrameCode("frameCode"),
                        new MonthlyCalculationByPeriod(),
                        new ExcessOutsideByPeriod(),
                        new AgreementTimeByPeriod(),
                        new VerticalTotalOfMonthly(),
                        TotalCountByPeriod.of(Arrays.asList(TotalCount.of(1, new AttendanceDaysMonth(0.0), new AttendanceTimeMonth(0)))),
                        new AnyItemByPeriod()
                ));
            }
            {
                converter.toAttendanceTime();
                result = Optional.of(AttendanceTimeOfAnyPeriod.of(
                        "employeeId",
                        new AnyAggrFrameCode("frameCode"),
                        new MonthlyCalculationByPeriod(),
                        new ExcessOutsideByPeriod(),
                        new AgreementTimeByPeriod(),
                        new VerticalTotalOfMonthly(),
                        TotalCountByPeriod.of(Arrays.asList(TotalCount.of(1, new AttendanceDaysMonth(1.0), new AttendanceTimeMonth(0)))),
                        new AnyItemByPeriod()
                ));
            }
        };

        AnyPeriodActualResultCorrectionDetail result = AnyPeriodActualResultCorrectionService.create(
                require,
                "frameCode",
                "employeeId",
                Arrays.asList(new ItemValue(1.0, ValueType.COUNT_WITH_DECIMAL, "", 476))
        );
        assertThat(result.getAfterCorrection()).isNotNull();
        assertThat(result.getAfterCalculation()).isNotNull();
        assertThat(result.getAfterCorrection().getEmployeeId()).isEqualTo(result.getAfterCalculation().getEmployeeId());
        assertThat(result.getAfterCorrection().getTotalCount().getTotalCountList().get(1).getCount().v()).isEqualTo(1.0);
    }
}
