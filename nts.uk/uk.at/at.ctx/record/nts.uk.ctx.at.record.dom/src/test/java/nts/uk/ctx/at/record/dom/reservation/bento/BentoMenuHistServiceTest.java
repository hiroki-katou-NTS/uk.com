package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
                require.findByCompanyId("cid");
                result = Optional.empty();
            }
        };
        NtsAssert.atomTask(
                () -> BentoMenuHistService.register(require, new DatePeriod(GeneralDate.today(),
                        GeneralDate.max()), "cid",null),
                any -> require.add(any.get()),
                any -> require.addBentomenu(any.get(),any.get()));
    }

    /**
     * Test case : itemUpdate == itemAdd.
     */
    @Test
    public void testBentoMenuServiceHist_1() {

        DateHistoryItem item = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(), GeneralDate.max()));

        val bento = new BentoMenuHistory("cid", Arrays.asList(item));
        new Expectations() {
            {
                require.findByCompanyId("cid");
                result = Optional.of(bento);
            }
        };
        NtsAssert.businessException("Msg_102",
                () -> BentoMenuHistService.register(require, new DatePeriod(GeneralDate.today(), GeneralDate.max()),
                        "cid",null));


    }

    /**
     * Test case endDate != GeneralDate.max()
     */
    @Test
    public void testBentoMenuServiceHist_2() {

        DateHistoryItem item = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(), GeneralDate.max()));

        val bento = new BentoMenuHistory("cid", Arrays.asList(item));
        new Expectations() {
            {
                require.findByCompanyId("cid");
                result = Optional.of(bento);
            }
        };
        NtsAssert.systemError(
                () -> BentoMenuHistService.register(require, new DatePeriod(GeneralDate.today().addDays(+1)
                        , GeneralDate.today().addDays(+2)), "cid",null));


    }

    @Test
    public void testBentoMenuServiceHist_3() {

        DateHistoryItem item = DateHistoryItem.createNewHistory(new DatePeriod(GeneralDate.today(), GeneralDate.max()));
        val listItemHist = new ArrayList<DateHistoryItem>();
        listItemHist.add(item);
        val bento = new BentoMenuHistory("cid", listItemHist);

        new Expectations() {
            {
                require.findByCompanyId("cid");
                result = Optional.of(bento);
            }
        };
        NtsAssert.atomTask(
                () -> BentoMenuHistService.register(require,
                        new DatePeriod(GeneralDate.today().addDays(3)
                                , GeneralDate.max()), "cid",null),
                any -> require.add(any.get()),
                any -> require.addBentomenu(any.get(),any.get()),
                any -> require.update(any.get()));

    }

}

