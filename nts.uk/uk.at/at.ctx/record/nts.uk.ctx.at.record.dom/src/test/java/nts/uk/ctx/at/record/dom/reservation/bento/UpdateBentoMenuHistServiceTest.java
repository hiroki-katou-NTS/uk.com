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
public class UpdateBentoMenuHistServiceTest {

    @Injectable
    UpdateBentoMenuHistService.Require require;

    /**
     * ItemToBeChanged.end() # (newSpan.end())
     */
    @Test
    public void testUpdateBentoMenuHist_02() {
        NtsAssert.businessException("Msg_3261", () -> {
        	AtomTask persist = UpdateBentoMenuHistService.register(require, "cid", new DatePeriod(GeneralDate.today().decrease(), GeneralDate.max()), GeneralDate.today());
        	persist.run();
        });
    }

    @Test
    public void testUpdateBentoMenuHist_03() {
        val dateBefore = new DateHistoryItem("histId", new DatePeriod(GeneralDate.today(), GeneralDate.max()));
        BentoMenuHistory itemBefore = new BentoMenuHistory(dateBefore.identifier(), dateBefore, Collections.emptyList());
        val date = new DateHistoryItem("histId", new DatePeriod(GeneralDate.today().addDays(4), GeneralDate.max()));
        BentoMenuHistory item = new BentoMenuHistory(date.identifier(), date, Collections.emptyList());
        new Expectations() {
            {
                require.getBentoMenu("cid", GeneralDate.today().decrease());
                result = Optional.of(itemBefore);
                
                require.getBentoMenu("cid", GeneralDate.today().addDays(4).decrease());
                result = Optional.of(item);
            }
        };
        NtsAssert.atomTask(
                () -> UpdateBentoMenuHistService.register(require, "cid", new DatePeriod(GeneralDate.today().addDays(4), GeneralDate.max()), GeneralDate.today()),
                any -> require.update(any.get())
        );
    }

}
