package nts.uk.ctx.at.record.dom.anyperiod;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

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
                require.getNameOfAttendanceItem(itemIds, (TypeOfItemImport) any);
                result = Arrays.asList(
                        createAttItemName(1, "name 1", 1),
                        createAttItemName(2, "name 2", 2),
                        createAttItemName(3, "name 3", 2)
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
        List<ItemInfo> itemInfos = CorrectionLogInfoItemCreateService.create(require, oldValues, newValues);

        assertThat(itemInfos.size()).isEqualTo(2);

        assertThat(itemInfos.get(0).getValueBefore().getViewValue()).isEqualTo("0:30");
        assertThat(itemInfos.get(0).getValueAfter().getViewValue()).isEqualTo("1:30");

        assertThat(itemInfos.get(1).getValueBefore().getViewValue()).isEqualTo("300");
        assertThat(itemInfos.get(1).getValueAfter().getViewValue()).isEqualTo("200");
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
