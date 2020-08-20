package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.Helper;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static nts.arc.time.clock.ClockHourMinute.now;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RunWith(JMockit.class)
public class BentoRigisterServiceTest {
    @Injectable
    BentoRegisterService.Require require;

    @Mocked
    AppContexts appContexts;

    /**
     * Init Appcontext
     */
    @Before
    public void init(){
        new Expectations() {{
            AppContexts.user().companyId();
            result = "CID";
        }};
    }

    @Test
    public void register() {

        GeneralDate date = GeneralDate.max();
        Bento newbento = new Bento(2,new BentoName("bentoName"),new BentoAmount(1),
                new BentoAmount(2), new BentoReservationUnitName("string"),true,true, Optional.empty());

        // Mock up
        new Expectations() {{
            ReservationClosingTime time1 = Helper.ClosingTime.startEnd(
                    now(),
                    now().forwardByHours(2));

            ReservationClosingTime time2 = Helper.ClosingTime.startEnd(
                    now().forwardByHours(3),
                    now().forwardByHours(3));
            require.getBentoMenu("CID",date);
            result = new BentoMenu("hisId",new ArrayList<>(Arrays.asList(newbento)),new BentoReservationClosingTime(time1,Optional.of(time2)));

        }};

        NtsAssert.atomTask(
                () -> BentoRegisterService.register(
                        require,newbento),
                any -> require.register(any.get())
        );
    }
}
