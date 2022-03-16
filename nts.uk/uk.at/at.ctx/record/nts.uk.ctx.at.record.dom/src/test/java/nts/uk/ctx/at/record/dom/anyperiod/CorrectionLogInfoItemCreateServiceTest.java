package nts.uk.ctx.at.record.dom.anyperiod;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.attendance.AttendanceName;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.monthlyattditem.DisplayMonthResultsMethod;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class CorrectionLogInfoItemCreateServiceTest {
    @Injectable
    private CorrectionLogInfoItemCreateService.Require require;

    @Test
    public void test() {
        List<Integer> itemIds = Arrays.asList(1, 2, 3);
        new Expectations() {
            {
                require.findByAttendanceItemId(anyString, itemIds);
                result = Arrays.asList(
                        createAttItem(1, "name 1", 1),
                        createAttItem(2, "name 2", 2),
                        createAttItem(3, "name 3", 2)
                );
            }
        };

        List<ItemValue> oldValues = Arrays.asList(
                new ItemValue("30", ValueType.TIME, null, 1),
                new ItemValue("3.0", ValueType.COUNT_WITH_DECIMAL, null, 2),
                new ItemValue("300", ValueType.AMOUNT, null, 3)
        );
        List<ItemValue> newValues = Arrays.asList(
                new ItemValue("90", ValueType.TIME, null, 1),
                new ItemValue("3.0", ValueType.COUNT_WITH_DECIMAL, null, 2),
                new ItemValue("200", ValueType.AMOUNT, null, 3)
        );
        List<ItemInfo> itemInfos = CorrectionLogInfoItemCreateService.create(require, "", oldValues, newValues);

        assertThat(itemInfos.size()).isEqualTo(2);

        assertThat(itemInfos.get(0).getValueBefore().getViewValue()).isEqualTo("0:30");
        assertThat(itemInfos.get(0).getValueAfter().getViewValue()).isEqualTo("1:30");

        assertThat(itemInfos.get(1).getValueBefore().getViewValue()).isEqualTo("300.0");
        assertThat(itemInfos.get(1).getValueAfter().getViewValue()).isEqualTo("200.0");
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
