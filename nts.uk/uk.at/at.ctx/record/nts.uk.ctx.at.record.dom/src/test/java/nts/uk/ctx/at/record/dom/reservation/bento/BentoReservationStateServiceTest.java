package nts.uk.ctx.at.record.dom.reservation.bento;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class BentoReservationStateServiceTest {

    @Injectable
    BentoReservationStateService.Require require;

    @Test
    public void testBentoReservationState_Empty() {
        new Expectations() {
            {
                require.getClosureStart("employeeId");
                result = Optional.empty();
            }
        };

        boolean check = BentoReservationStateService.check(require, "employeeId",
                GeneralDate.ymd(2020, 8, 17));
        assertThat(check).isEqualTo(false);
    }

    @Test
    public void testBentoReservationState_Equal() {
        new Expectations() {
            {
                require.getClosureStart("employeeId");
                result = Optional.of(GeneralDate.ymd(2020, 8, 17));
            }
        };

        boolean check = BentoReservationStateService.check(require, "employeeId",
                GeneralDate.ymd(2020, 8, 17));
        assertThat(check).isEqualTo(true);
    }

    @Test
    public void testBentoReservationState_Less() {
        new Expectations() {
            {
                require.getClosureStart("employeeId");
                result = Optional.of(GeneralDate.ymd(2020, 8, 16));
            }
        };

        boolean check = BentoReservationStateService.check(require, "employeeId",
                GeneralDate.ymd(2020, 8, 17));
        assertThat(check).isEqualTo(true);
    }

    @Test
    public void testBentoReservationState_Greater() {
        new Expectations() {
            {
                require.getClosureStart("employeeId");
                result = Optional.of(GeneralDate.ymd(2020, 8, 18));
            }
        };

        boolean check = BentoReservationStateService.check(require, "employeeId",
                GeneralDate.ymd(2020, 8, 17));
        assertThat(check).isEqualTo(false);
    }
}
