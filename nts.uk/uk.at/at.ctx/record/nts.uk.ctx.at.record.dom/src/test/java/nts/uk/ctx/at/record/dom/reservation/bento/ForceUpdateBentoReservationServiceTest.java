package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

@RunWith(JMockit.class)
public class ForceUpdateBentoReservationServiceTest {
    @Injectable
    ForceUpdateBentoReservationService.Require require;

    @Test
    public void test_Empty() {

        new Expectations() {

        };
        List<BentoReservationInfoTemp> bentoReservationInfos = new ArrayList<>();
        BentoReservationInfoTemp item1 = new BentoReservationInfoTemp();
        item1.setRegisterInfo(new ReservationRegisterInfo("TEST1"));
        item1.setOrdered(false);

        ReservationDate reservationDate = new ReservationDate(GeneralDate.ymd(2020, 8, 17), ReservationClosingTimeFrame.FRAME1);
        NtsAssert.atomTask(
                () -> ForceUpdateBentoReservationService.forceUpdate(require, reservationDate, bentoReservationInfos));
    }

    @Test
    public void test_Data() {
        ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo("TEST1");
        ReservationDate reservationDate = new ReservationDate(GeneralDate.ymd(2020, 8, 17), ReservationClosingTimeFrame.FRAME1);

        List<BentoReservationInfoTemp> bentoReservationInfos = new ArrayList<>();
        BentoReservationInfoTemp item1 = new BentoReservationInfoTemp();
        item1.setRegisterInfo(reservationRegisterInfo);
        item1.setOrdered(false);
        Map<Integer, BentoReservationCount> map1 = new HashMap<Integer, BentoReservationCount>(){{
            put(1, new BentoReservationCount(2));
            put(2, new BentoReservationCount(3));
            put(3, new BentoReservationCount(2));
        }};
        item1.setBentoDetails(map1);
        bentoReservationInfos.add(item1);

        GeneralDateTime dateTime = GeneralDateTime.now();
        List<BentoReservationDetail> bentoReservationDetails = new ArrayList<>();
        BentoReservationDetail detail1 = new BentoReservationDetail(1, dateTime, false, new BentoReservationCount(1));
        bentoReservationDetails.add(detail1);;
        BentoReservationDetail detail2 = new BentoReservationDetail(2, dateTime, false, new BentoReservationCount(1));
        bentoReservationDetails.add(detail2);

        BentoReservation bentoReservation = new BentoReservation(reservationRegisterInfo, reservationDate, false, Optional.empty(), bentoReservationDetails);
        new Expectations() {
            {
                require.get(reservationRegisterInfo, reservationDate);
                result = bentoReservation;
            }
        };

        NtsAssert.atomTask(
                () -> ForceUpdateBentoReservationService.forceUpdate(require, reservationDate, bentoReservationInfos),
                any -> require.update(any.get()));
    }
}
