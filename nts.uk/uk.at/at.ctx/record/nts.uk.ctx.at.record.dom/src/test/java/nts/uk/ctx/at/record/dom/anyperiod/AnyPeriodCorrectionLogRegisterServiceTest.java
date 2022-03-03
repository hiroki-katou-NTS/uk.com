package nts.uk.ctx.at.record.dom.anyperiod;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodActualResultCorrectionDetail;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AnyPeriodCorrectionLogRegisterServiceTest {
    @Injectable
    private AnyPeriodCorrectionLogRegisterService.Require require;

    @Test
    public void testCreateLogContent(@Mocked AnyPeriodRecordToAttendanceItemConverter converter) {
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
                require.getNameOfAttendanceItem((List<Integer>) any, (TypeOfItemImport) any);
                result = Arrays.asList(
                        createAttItemName(1, "name 1", 1),
                        createAttItemName(2, "name 2", 2),
                        createAttItemName(3, "name 3", 2)
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

    private AttItemName createAttItemName(int itemId, String itemName, int attendanceAtr) {
        AttItemName obj = new AttItemName();
        obj.setAttendanceItemId(itemId);
        obj.setAttendanceItemName(itemName);
        obj.setOldName(itemName);
        obj.setAttendanceAtr(attendanceAtr);
        return obj;
    }
}
