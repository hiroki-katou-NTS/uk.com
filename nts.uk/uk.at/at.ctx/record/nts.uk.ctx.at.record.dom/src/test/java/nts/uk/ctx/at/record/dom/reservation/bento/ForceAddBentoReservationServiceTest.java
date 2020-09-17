package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class ForceAddBentoReservationServiceTest {

    @Injectable
    ForceAddBentoReservationService.Require require;

    @Test
    public void test_Empty() {

        new Expectations() {

        };
        List<BentoReservationInfoTemp> bentoReservationInfos = new ArrayList<>();
        BentoReservationInfoTemp item1 = new BentoReservationInfoTemp();
        item1.setRegisterInfo(new ReservationRegisterInfo("TEST1"));
        item1.setOrdered(false);

        ReservationDate reservationDate = new ReservationDate(GeneralDate.ymd(2020, 8, 17), ReservationClosingTimeFrame.FRAME1);
        GeneralDateTime dateTime = GeneralDateTime.now();
        Optional<WorkLocationCode> workLocationCode = Optional.empty();
        NtsAssert.atomTask(
                () -> ForceAddBentoReservationService.forceAdd(require, bentoReservationInfos, reservationDate,dateTime, workLocationCode));
    }

    @Test
    public void test_Data() {

        new Expectations() {

        };
        List<BentoReservationInfoTemp> bentoReservationInfos = new ArrayList<>();
        BentoReservationInfoTemp item1 = new BentoReservationInfoTemp();
        item1.setRegisterInfo(new ReservationRegisterInfo("TEST1"));
        item1.setOrdered(false);
        Map<Integer, BentoReservationCount> map1 = new HashMap<Integer, BentoReservationCount>(){{
            put(1, new BentoReservationCount(2));
            put(2, new BentoReservationCount(3));
        }};
        item1.setBentoDetails(map1);
        bentoReservationInfos.add(item1);

        ReservationDate reservationDate = new ReservationDate(GeneralDate.ymd(2020, 8, 17), ReservationClosingTimeFrame.FRAME1);
        GeneralDateTime dateTime = GeneralDateTime.now();
        Optional<WorkLocationCode> workLocationCode = Optional.empty();
        NtsAssert.atomTask(
                () -> ForceAddBentoReservationService.forceAdd(require, bentoReservationInfos, reservationDate,dateTime, workLocationCode),
                any -> require.add(any.get()));
    }
}
