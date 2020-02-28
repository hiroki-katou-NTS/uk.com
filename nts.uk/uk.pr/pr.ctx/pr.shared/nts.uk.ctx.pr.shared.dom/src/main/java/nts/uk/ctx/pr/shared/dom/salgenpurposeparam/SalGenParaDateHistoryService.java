package nts.uk.ctx.pr.shared.dom.salgenpurposeparam;


import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

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
        salGenParaDateHistRepository.update(itemToBeUpdate.get(), paraNo, cId);
        this.updateItemBefore(dateHistory.get(), itemToBeUpdate.get(), paraNo, cId);
    }

    private void updateItemBefore(SalGenParaDateHistory dateHistory, DateHistoryItem item, String paraNo, String cId){
        Optional<DateHistoryItem> itemToBeUpdated = dateHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        salGenParaDateHistRepository.update(itemToBeUpdated.get(),paraNo, cId);
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
            salGenParaDateHistRepository.update(lastestItem, dateHistory.get().getParaNo(), cId);
        }
    }
    public String getHistoryIdByTargetDate(String paraNo,GeneralDate targetDate){
        Optional<SalGenParaDateHistory> objectHis = salGenParaDateHistRepository.getAllSalGenParaDateHist(AppContexts.user().companyId(),paraNo);
        Optional<DateHistoryItem> resulf =  objectHis.get().getDateHistoryItem()
                .stream()
                .filter(e -> e.start().beforeOrEquals(targetDate) && e.end().afterOrEquals(targetDate))
                .findFirst();
        return resulf.isPresent() ? resulf.get().identifier() : "";
    }
    public String getHistoryIdByStartDate(String paraNo,GeneralDate startDate){
        Optional<SalGenParaDateHistory> objectHis = salGenParaDateHistRepository.getAllSalGenParaDateHist(AppContexts.user().companyId(),paraNo);
        return objectHis.get().getDateHistoryItem()
                .stream()
                .reduce((first, second) -> second).get().identifier();
    }

}
