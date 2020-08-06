package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
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
            require.getBentoMenu(null,date);
            result = menu;
        }};

        NtsAssert.atomTask(
                () -> RegisterReservationLunchService.register(require, operationDistinction, achievements, correctionContent, null));
    }

}
