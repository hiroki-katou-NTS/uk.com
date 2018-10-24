package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;


@Stateless
public class SalGenParaDateHistoryService {

    @Inject
    private SalGenParaDateHistRepository salGenParaDateHistRepository;

    public void updateDateHistory(String cId, String paraNo, String hisId, GeneralDate start, GeneralDate end){
        Optional<SalGenParaDateHistory> dateHistory = salGenParaDateHistRepository.getAllSalGenParaDateHist(cId , paraNo);
        Optional<DateHistoryItem> itemToBeUpdate = dateHistory.get().getDateHistoryItem().stream()
                .filter(h -> h.identifier().equals(hisId)).findFirst();
        if (!itemToBeUpdate.isPresent()) {
            return;
        }
        dateHistory.get().changeSpan(itemToBeUpdate.get(), new DatePeriod(start, end));
        salGenParaDateHistRepository.update(itemToBeUpdate.get(), cId, paraNo);
        this.updateItemBefore(dateHistory.get(), itemToBeUpdate.get(), cId, paraNo);
    }

    private void updateItemBefore(SalGenParaDateHistory dateHistory, DateHistoryItem item, String cId, String paraNo){
        Optional<DateHistoryItem> itemToBeUpdated = dateHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        salGenParaDateHistRepository.update(itemToBeUpdated.get(), cId, paraNo);
    }

    public void deleteYearMonthHistory(String cId, String hisId, String paraNo){
        Optional<SalGenParaDateHistory> dateHistory = salGenParaDateHistRepository.getAllSalGenParaDateHist(cId, paraNo);
        Optional<DateHistoryItem> itemToBeDelete = dateHistory.get().getDateHistoryItem().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
        if (!itemToBeDelete.isPresent()) {
            return;
        }
        dateHistory.get().remove(itemToBeDelete.get());
        salGenParaDateHistRepository.remove(dateHistory.get().getParaNo(), cId, hisId);
        if (dateHistory.get().getDateHistoryItem().size() > 0 ){
            DateHistoryItem lastestItem = dateHistory.get().getDateHistoryItem().get(0);
            dateHistory.get().exCorrectToRemove(lastestItem);
            salGenParaDateHistRepository.update(lastestItem, cId, dateHistory.get().getParaNo());
        }
    }

}
