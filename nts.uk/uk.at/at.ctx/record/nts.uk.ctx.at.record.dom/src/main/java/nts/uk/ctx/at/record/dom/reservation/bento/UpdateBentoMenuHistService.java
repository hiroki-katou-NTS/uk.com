package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Optional;
@Stateless
public class UpdateBentoMenuHistService {
    public static AtomTask register(Require require, DatePeriod period,String histotyId,String cid){
        // List all bentomenuhistory
        val listBentoMenuHist = require.findByCompanyId(cid);
        // Create list empty.
        BentoMenuHistory listhist = new BentoMenuHistory(cid,new ArrayList<>());
        if (listBentoMenuHist.isPresent()){
            listhist = listBentoMenuHist.get();
        }
        // Get item update
        Optional<DateHistoryItem> optionalHisItem = listhist.items().stream()
                .filter(x -> x.identifier().equals(histotyId)).findFirst();
        if (!optionalHisItem.isPresent()) {

            throw new BusinessException("invalid BentoMenuHistory!");
        }
        //Update item
         listhist.changeSpan(optionalHisItem.get(), period);

        val itemBefore = listhist.immediatelyBefore(optionalHisItem.get());

        return AtomTask.of(()->{
                require.update(optionalHisItem.get());
                require.update(itemBefore.get());


        });
    }

    public static interface Require{
        Optional<BentoMenuHistory> findByCompanyId(String cid);

        void update(DateHistoryItem item);

    }
}
