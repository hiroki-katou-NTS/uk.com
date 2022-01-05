package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.history.DateHistoryItem;

@RunWith(JMockit.class)
public class BentoMenuHistServiceTest {

    @Injectable
    BentoMenuHistService.Require require;

    /**
     * Test case : BentoMenuHist = empty
     */
    @Test
    public void testBentoMenuServiceHist() {
        new Expectations() {
            {
                require.getBentoMenu("cid", GeneralDate.max());
                result = Optional.empty();
            }
        };
        NtsAssert.atomTask(
                () -> BentoMenuHistService.register(require, "cid", new DatePeriod(GeneralDate.today(), GeneralDate.max())),
                any -> require.add(any.get()));
    }

    /**
     * Test case : itemUpdate == itemAdd.
     */
    @Test
    public void testBentoMenuServiceHist_1() {

        DateHistoryItem item = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(), GeneralDate.max()));

        val bento = new BentoMenuHistory(item.identifier(), item, Collections.emptyList());
        new Expectations() {
            {
                require.getBentoMenu("cid", GeneralDate.max());
                result = Optional.of(bento);
            }
        };
        NtsAssert.businessException("Msg_102", () -> {
        	AtomTask persist = BentoMenuHistService.register(require, "cid", new DatePeriod(GeneralDate.today(), GeneralDate.max()));
        	persist.run();
        });


    }

    @Test
    public void testBentoMenuServiceHist_3() {

        DateHistoryItem item = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(), GeneralDate.max()));
        val bento = new BentoMenuHistory(item.identifier(), item, Collections.emptyList());

        new Expectations() {
            {
                require.getBentoMenu("cid", GeneralDate.max());
                result = Optional.of(bento);
            }
        };
        NtsAssert.atomTask(
                () -> BentoMenuHistService.register(require, "cid", new DatePeriod(GeneralDate.today().addDays(3), GeneralDate.max())),
                any -> require.add(any.get()),
                any -> require.update(any.get()));

    }

}

