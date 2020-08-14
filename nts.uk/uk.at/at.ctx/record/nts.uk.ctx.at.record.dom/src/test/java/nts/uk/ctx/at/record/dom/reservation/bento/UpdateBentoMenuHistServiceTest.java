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
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(JMockit.class)
public class UpdateBentoMenuHistServiceTest {

    @Injectable
    UpdateBentoMenuHistService.Require require;

    /**
     * Test case empty : input: cid,
     *                   output : businessException: invalid BentoMenuHistory!
     */
    @Test
    public void testUpdateBentoMenuHist(){
        new Expectations(){
            {
                require.findByCompanyId("cid");
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("invalid BentoMenuHistory!",
                () -> UpdateBentoMenuHistService.register(require,new DatePeriod(GeneralDate.today()
                        ,GeneralDate.max()),"histId","cid"));
    }
    /**
     * Test case
     */
    @Test
    public void testUpdateBentoMenuHist_01(){
        val list = new ArrayList<DateHistoryItem>();
        val dateHistoryItem = new DateHistoryItem("hist",new DatePeriod(GeneralDate.today(),GeneralDate.max()));
        list.add(dateHistoryItem);
        BentoMenuHistory item = new BentoMenuHistory("cid",list);
        new Expectations(){
            {
                require.findByCompanyId("cid");
                result = Optional.of(item);
            }
        };
        NtsAssert.businessException("invalid BentoMenuHistory!",
                () -> UpdateBentoMenuHistService.register(require,new DatePeriod(GeneralDate.today()
                        ,GeneralDate.max()),"histId","cid"));
    }
    /**
     * ItemToBeChanged.end() # (newSpan.end())
     */
    @Test
    public void testUpdateBentoMenuHist_02(){
        val list = new ArrayList<DateHistoryItem>();
        val date = new DateHistoryItem("histId",new DatePeriod(GeneralDate.today(),GeneralDate.max()));
        list.add(date);
        BentoMenuHistory item = new BentoMenuHistory("cid",list);
        new Expectations(){
            {
                require.findByCompanyId("cid");
                result = Optional.of(item);
            }
        };
        assertThatThrownBy(()->UpdateBentoMenuHistService.register(require,new DatePeriod(GeneralDate.today().addDays(1)
                ,GeneralDate.today().addDays(4)),"histId","cid")).isInstanceOf(RuntimeException.class);
    }
    @Test
    public void testUpdateBentoMenuHist_03(){
        val list = new ArrayList<DateHistoryItem>();
        val date = new DateHistoryItem("histId",new DatePeriod(GeneralDate.today(),GeneralDate.max()));
        list.add(date);
        BentoMenuHistory item = new BentoMenuHistory("cid",list);
        new Expectations(){
            {
                require.findByCompanyId("cid");
                result = Optional.of(item);
            }
        };
        NtsAssert.atomTask(
                ()->UpdateBentoMenuHistService.register(require,new DatePeriod(GeneralDate.today().addDays(4)
                ,GeneralDate.max()),"histId","cid"),
                any->require.update(any.get())
        );
    }

    @Test
    public void testUpdateBentoMenuHist_04(){

        val date2 = new DateHistoryItem("histId1",new DatePeriod(GeneralDate.today().addDays(-6),GeneralDate.today().addDays(-1)));
        val date = new DateHistoryItem("histId",new DatePeriod(GeneralDate.today(),GeneralDate.max()));
        val list = new ArrayList<DateHistoryItem>();
        list.add(date2);
        list.add(date);

        BentoMenuHistory item = new BentoMenuHistory("cid",list);
        new Expectations(){
            {
                require.findByCompanyId("cid");
                result = Optional.of(item);
            }
        };
        NtsAssert.atomTask(
                ()->UpdateBentoMenuHistService.register(require,new DatePeriod(GeneralDate.today().addDays(4)
                        ,GeneralDate.max()),"histId","cid"),
                any->require.update(any.get())
        );
    }

}
