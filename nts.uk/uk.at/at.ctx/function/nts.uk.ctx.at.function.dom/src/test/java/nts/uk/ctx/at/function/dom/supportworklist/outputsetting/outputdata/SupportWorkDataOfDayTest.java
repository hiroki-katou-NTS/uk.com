package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class SupportWorkDataOfDayTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        SupportWorkDataOfDay detail = SupportWorkDataOfDay.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                Arrays.asList(SupportWorkDetails.create(
                        "000000000003-0001",
                        GeneralDate.today(),
                        "workplace-id-0001",
                        "0001",
                        Arrays.asList(1, 2, 3),
                        new OuenWorkTimeSheetOfDailyAttendance(new SupportFrameNo(1), null, null,
                                Optional.empty()),
                        OuenWorkTimeOfDailyAttendance.create(
                                SupportFrameNo.of(1),
                                null,
                                null,
                                null)
                )),
                NotUseAtr.NOT_USE
        );

        NtsAssert.invokeGetters(detail);
    }

    @Test
    public void testDisplayTotal() {
        SupportWorkDataOfDay detail = SupportWorkDataOfDay.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                Arrays.asList(SupportWorkDetails.create(
                        "000000000003-0001",
                        GeneralDate.today(),
                        "workplace-id-0001",
                        "0001",
                        Arrays.asList(1, 2, 3),
                        new OuenWorkTimeSheetOfDailyAttendance(new SupportFrameNo(1), null, null,
                                Optional.empty()),
                        OuenWorkTimeOfDailyAttendance.create(
                                SupportFrameNo.of(1),
                                null,
                                null,
                               null)
                )),
                NotUseAtr.USE
        );

        assertThat(detail.getTotalDetailOfDay().isPresent()).isEqualTo(true);
    }

    @Test
    public void testNotDisplayTotal() {
        SupportWorkDataOfDay detail = SupportWorkDataOfDay.create(
                require,
                "000000000003-0001",
                GeneralDate.today(),
                Arrays.asList(SupportWorkDetails.create(
                        "000000000003-0001",
                        GeneralDate.today(),
                        "workplace-id-0001",
                        "0001",
                        Arrays.asList(1, 2, 3),
                        new OuenWorkTimeSheetOfDailyAttendance(new SupportFrameNo(1), null, null,
                                Optional.empty()),
                        OuenWorkTimeOfDailyAttendance.create(
                                SupportFrameNo.of(1),
                                null,
                                null,
                                null)
                )),
                NotUseAtr.NOT_USE
        );

        assertThat(detail.getTotalDetailOfDay().isPresent()).isEqualTo(false);
    }
}
