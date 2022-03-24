package nts.uk.ctx.at.record.dom.anyperiod;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceName;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.monthlyattditem.DisplayMonthResultsMethod;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodActualResultCorrectionDetail;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionLogRegisterServiceTest {
    @Injectable
    private AnyPeriodCorrectionLogRegisterService.Require require;

    @Mocked
    private AnyPeriodRecordToAttendanceItemConverter converter;

    @Test
    public void testCreateLogContent() {
        List<ItemValue> original = Arrays.asList(
                new ItemValue("30", ValueType.TIME, null, 1),
                new ItemValue("3.0", ValueType.COUNT_WITH_DECIMAL, null, 2),
                new ItemValue("200", ValueType.AMOUNT, null, 3)
        );
        List<ItemValue> corrected = Arrays.asList(
                new ItemValue("90", ValueType.TIME, null, 1),
                new ItemValue("3.0", ValueType.COUNT_WITH_DECIMAL, null, 2),
                new ItemValue("300", ValueType.AMOUNT, null, 3)
        );
        List<ItemValue> calculated = Arrays.asList(
                new ItemValue("90", ValueType.TIME, null, 1),
                new ItemValue("3.0", ValueType.COUNT_WITH_DECIMAL, null, 2),
                new ItemValue("400", ValueType.AMOUNT, null, 3)
        );

        new Expectations() {
            {
                converter.withBase(anyString).withAttendanceTime((AttendanceTimeOfAnyPeriod) any).convert((Collection<Integer>) any);
                result = original;
                result = corrected;
                result = calculated;
            }
            {
                require.findByAttendanceItemId(anyString, (List<Integer>) any);
                result = Arrays.asList(
                        createAttItem(1, "name 1", 1),
                        createAttItem(2, "name 2", 2),
                        createAttItem(3, "name 3", 2)
                );
            }
        };

        AnyPeriodLogContent result = NtsAssert.Invoke.privateMethod(
                new AnyPeriodCorrectionLogRegisterService(),
                "createLogContent",
                require,
                "companyId",
                new AnyPeriodActualResultCorrectionDetail(
                        new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode")),
                        new AttendanceTimeOfAnyPeriod("employeeId", new AnyAggrFrameCode("frameCode"))
                )
        );

        assertThat(result.getEmployeeId()).isEqualTo("employeeId");
        assertThat(result.getCorrectedList().size()).isEqualTo(2);
        assertThat(result.getCalculatedList().size()).isEqualTo(1);
    }

    private MonthlyAttendanceItem createAttItem(int itemId, String itemName, int attendanceAtr) {
        MonthlyAttendanceItem obj = new MonthlyAttendanceItem(new MonthlyAttendanceItemGetMemento() {
            @Override
            public UseSetting getUserCanUpdateAtr() {
                return UseSetting.UseAtr_Use;
            }
            @Override
            public DisplayMonthResultsMethod getTwoMonthlyDisplay() {
                return DisplayMonthResultsMethod.DISPLAY_FIRST_ITEM;
            }
            @Override
            public Optional<PrimitiveValueOfAttendanceItem> getPrimitiveValue() {
                return Optional.empty();
            }
            @Override
            public int getNameLineFeedPosition() {
                return 0;
            }
            @Override
            public MonthlyAttendanceItemAtr getMonthlyAttendanceAtr() {
                return MonthlyAttendanceItemAtr.valueOf(attendanceAtr);
            }
            @Override
            public int getDisplayNumber() {
                return 1;
            }
            @Override
            public Optional<AttendanceName> getDisplayName() {
                return Optional.of(new AttendanceName(itemName));
            }
            @Override
            public String getCompanyId() {
                return "";
            }
            @Override
            public AttendanceName getAttendanceName() {
                return new AttendanceName(itemName);
            }
            @Override
            public int getAttendanceItemId() {
                return itemId;
            }
        });
        return obj;
    }
}
