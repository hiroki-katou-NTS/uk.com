package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 予約構成を追加する
 */
@Stateless
public class BentoMenuHistService {

    public static AtomTask register(Require require, DatePeriod date, String companyId) {

        // Get all list history
        val listBentoMenuHist = require.findByCompanyId(companyId);

        // List history
        BentoMenuHistory listHist = new BentoMenuHistory(companyId,new ArrayList<>());
        if (listBentoMenuHist.isPresent()){
            listHist = listBentoMenuHist.get();
        }

        // Item need to add
        DateHistoryItem itemToBeAdded = DateHistoryItem.createNewHistory(date);

        // Add into old list

        listHist.add(itemToBeAdded);

       val itemToBeUpdated = listHist.immediatelyBefore(itemToBeAdded);
        return AtomTask.of(() -> {
            // Update pre hist
            if (itemToBeUpdated.isPresent()) {
                require.update(itemToBeUpdated.get());
            }
            // Add
            require.add(itemToBeAdded);
        });
    }
    public static interface Require {

        Optional<BentoMenuHistory> findByCompanyId(String companyId);

        void update(DateHistoryItem item);

        void add(DateHistoryItem item);

    }
}