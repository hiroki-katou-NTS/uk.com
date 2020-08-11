package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(JMockit.class)
public class RegisterReservationLunchServiceTest {

    @Injectable
    RegisterReservationLunchService.Require require;

    @Test
    public void atomTask() {

        OperationDistinction operationDistinction = Helper.Reservation.operationDistinction.DUMMY;
        Achievements achievements = Helper.Reservation.achievements.DUMMY;
        GeneralDate date = GeneralDate.max();

        CorrectionContent correctionContent = Helper.Reservation.correctionContent.DUMMY;
        BentoMenu menu = new BentoMenu(
                "historyId",
                Arrays.asList(Helper.Menu.Item.bentoReserveFrame(1, true, true)),
                Helper.ClosingTime.UNLIMITED);

        new Expectations() {{
            String cid = "0000001";
            require.getBentoMenu(cid,date);
            result = menu;
        }};

        BentoReservationSetting bentoReservationSetting = new BentoReservationSetting(
                "0000001",operationDistinction,null,achievements);
        new Expectations() {{
            String cid = "0000001";
            require.getReservationSettings(cid);
            result = bentoReservationSetting;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(
                        require, operationDistinction, achievements, correctionContent, null),
                any -> require.registerBentoMenu(any.get(),any.get()),
                any -> require.inSert(any.get(),any.get(),any.get()),
                any -> require.update(any.get())
                );
    }

}
