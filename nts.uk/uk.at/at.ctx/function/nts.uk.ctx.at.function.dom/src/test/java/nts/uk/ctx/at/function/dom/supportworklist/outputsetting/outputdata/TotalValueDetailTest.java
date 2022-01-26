package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.supportworklist.SupportWorkDetailsHelper;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class TotalValueDetailTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        TotalValueDetail detail = TotalValueDetail.create(
                require,
                "000000000003-0001",
                Arrays.asList(SupportWorkDetailsHelper.createDetailData("", GeneralDate.today(), Arrays.asList(930)))
        );

        NtsAssert.invokeGetters(detail);
    }

    @Test
    public void testValue() {
        String companyId = "000000000000-0001";
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        SupportWorkDetails supportWorkDetail11 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        SupportWorkDetails supportWorkDetail12 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today().addDays(1), attendanceItemIds);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        new Expectations() {{
            require.getDailyAttendanceItems(companyId, attendanceItemIds);
            result = Arrays.asList(
                    new DailyAttendanceItemAdapterDto(companyId, 929, "name929", 1, 1, 0, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 930, "name930", 1, 1, 1, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 1305, "name1305", 1, 1, 2, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 1306, "name1306", 1, 1, 3, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 1309, "name1309", 1, 1, 4, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 1336, "name1336", 1, 1, 5, 1, ""),
                    new DailyAttendanceItemAdapterDto(companyId, 2191, "name2191", 1, 1, 6, 1, "")
            );
        }};

        TotalValueDetail totalValueDetail = TotalValueDetail.create(
                require,
                companyId,
                Arrays.asList(supportWorkDetail1, supportWorkDetail2, supportWorkDetail11, supportWorkDetail12)
        );

        assertThat(totalValueDetail.getPeopleCount()).isEqualTo(3);
        assertThat((Integer) totalValueDetail.getItemValues().get(0).value()).isEqualTo(2000);
        assertThat((Integer) totalValueDetail.getItemValues().get(1).value()).isEqualTo(2400);
    }
}
