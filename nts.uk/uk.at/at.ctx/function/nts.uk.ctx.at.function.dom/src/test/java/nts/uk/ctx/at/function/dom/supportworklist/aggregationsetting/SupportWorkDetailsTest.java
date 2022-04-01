package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class SupportWorkDetailsTest {

    @Test
    public void testGetter() {
        SupportWorkDetails detail = SupportWorkDetails.create(
                "employee-id-00001",
                GeneralDate.today(),
                "",
                "",
                Arrays.asList(1, 2, 3),
                new OuenWorkTimeSheetOfDailyAttendance(
                        new SupportFrameNo(1),
                        SupportType.TIMEZONE,
                        WorkContent.create(
                                WorkplaceOfWorkEachOuen.create(
                                        new WorkplaceId("workplace-id-00001"),
                                        new WorkLocationCD("0001")
                                ),
                                Optional.empty(),
                                Optional.empty()
                        ),
                        null,
                        Optional.empty()
                ),
                null
        );

        NtsAssert.invokeGetters(detail);
    }

    @Test
    public void testSetSupportWork() {
        OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDaily = new OuenWorkTimeSheetOfDailyAttendance(
                new SupportFrameNo(1),
                SupportType.TIMEZONE,
                WorkContent.create(
                        WorkplaceOfWorkEachOuen.create(
                                new WorkplaceId("workplace-id-00001"),
                                new WorkLocationCD("0001")
                        ),
                        Optional.empty(),
                        Optional.empty()
                ),
                null,
                Optional.empty()
        );
        SupportWorkDetails detail1 = SupportWorkDetails.create(
                "employee-id-00001",
                GeneralDate.today(),
                "",
                "",
                Arrays.asList(1, 2, 3),
                ouenWorkTimeSheetOfDaily,
                null
        );
        SupportWorkDetails detail2 = SupportWorkDetails.create(
                "employee-id-00001",
                GeneralDate.today(),
                "",
                "",
                Arrays.asList(1, 2, 3),
                ouenWorkTimeSheetOfDaily,
                null
        );
        detail2.setSupportWork(true);

        assertThat(detail1.isSupportWork()).isNotEqualTo(detail2.isSupportWork());
    }

    @Test
    public void testConvertItemValue() {
        SupportWorkDetails detail = SupportWorkDetails.create(
                "employee-id-00001",
                GeneralDate.today(),
                "",
                "",
                Arrays.asList(924, 925, 926, 927, 928, 929, 930, 1305, 1306, 1309, 1336, 2191),
                new OuenWorkTimeSheetOfDailyAttendance(
                        new SupportFrameNo(1),
                        SupportType.TIMEZONE,
                        WorkContent.create(
                                WorkplaceOfWorkEachOuen.create(
                                        new WorkplaceId("workplace-id-00001"),
                                        new WorkLocationCD("0001")
                                ),
                                Optional.of(WorkGroup.create(
                                        "WKCD1",
                                        "WKCD2",
                                        "WKCD3",
                                        "WKCD4",
                                        "WKCD5"
                                )),
                                Optional.empty()
                        ),
                        TimeSheetOfAttendanceEachOuenSheet.create(
                                new WorkNo(1),
                                Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(100))),
                                Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(200)))
                        ),
                        Optional.empty()
                ),
                OuenWorkTimeOfDailyAttendance.create(
                        new SupportFrameNo(1),
                        OuenAttendanceTimeEachTimeSheet.create(
                                new AttendanceTime(300),
                                new AttendanceTime(400),
                                new AttendanceTime(500),
                                null,
                                null,
                                PremiumTimeOfDailyPerformance.createEmpty()
                        ),
                        OuenMovementTimeEachTimeSheet.create(
                                new AttendanceTime(600),
                                new AttendanceTime(700),
                                new AttendanceTime(800),
                                null
                        ),
                        new AttendanceAmountDaily(900)
                )
        );

        assertThat(detail.getItemList().size()).isEqualTo(12);
        assertThat((String)detail.getItemList().get(0).value()).isEqualTo("WKCD1");
        assertThat((String)detail.getItemList().get(1).value()).isEqualTo("WKCD2");
        assertThat((String)detail.getItemList().get(2).value()).isEqualTo("WKCD3");
        assertThat((String)detail.getItemList().get(3).value()).isEqualTo("WKCD4");
        assertThat((String)detail.getItemList().get(4).value()).isEqualTo("WKCD5");
        assertThat((Integer) detail.getItemList().get(5).value()).isEqualTo(100);
        assertThat((Integer)detail.getItemList().get(6).value()).isEqualTo(200);
        assertThat((Integer)detail.getItemList().get(7).value()).isEqualTo(300);
        assertThat((Integer)detail.getItemList().get(8).value()).isEqualTo(500);
        assertThat((Integer)detail.getItemList().get(9).value()).isEqualTo(900);
        assertThat((Integer)detail.getItemList().get(10).value()).isEqualTo(600);
        assertThat((Integer)detail.getItemList().get(11).value()).isEqualTo(0);
    }
}
