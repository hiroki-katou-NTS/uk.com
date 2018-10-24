package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;


import java.util.ArrayList;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class SalGenParaYearMonthHistoryService {

    @Inject
    private SalGenParaYMHistRepository salGenParaYMHistRepository;

    public void updateYearMonthHistory(String cId, String paraNo,String hisId, YearMonth start, YearMonth end){
        Optional<SalGenParaYearMonthHistory> yearMonthHistory = salGenParaYMHistRepository.getAllSalGenParaYMHist(cId , paraNo);
        Optional<YearMonthHistoryItem> itemToBeUpdate = yearMonthHistory.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId)).findFirst();
        if (!itemToBeUpdate.isPresent()) {
            return;
        }
        yearMonthHistory.get().changeSpan(itemToBeUpdate.get(), new YearMonthPeriod(start, end));
        salGenParaYMHistRepository.update(itemToBeUpdate.get(), cId, paraNo);
        this.updateItemBefore(yearMonthHistory.get(), itemToBeUpdate.get(), cId, paraNo);
    }
    private void updateItemBefore(SalGenParaYearMonthHistory yearMonthHistory, YearMonthHistoryItem item, String cId, String paraNo){
        Optional<YearMonthHistoryItem> itemToBeUpdated = yearMonthHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        salGenParaYMHistRepository.update(itemToBeUpdated.get(), cId, paraNo);
    }

    public void deleteYearMonthHistory(String cId, String hisId, String paraNo){
        Optional<SalGenParaYearMonthHistory> yearMonthHistory = salGenParaYMHistRepository.getAllSalGenParaYMHist(cId, paraNo);
        Optional<YearMonthHistoryItem> itemToBeDelete = yearMonthHistory.get().getHistory().stream()
                .filter(h -> h.identifier().equals(hisId))
                .findFirst();
        if (!itemToBeDelete.isPresent()) {
            return;
        }
        yearMonthHistory.get().remove(itemToBeDelete.get());
        salGenParaYMHistRepository.remove(yearMonthHistory.get().getParaNo(), cId, hisId);
        if (yearMonthHistory.get().getHistory().size() > 0 ){
            YearMonthHistoryItem lastestItem = yearMonthHistory.get().getHistory().get(0);
            yearMonthHistory.get().exCorrectToRemove(lastestItem);
            salGenParaYMHistRepository.update(lastestItem, cId, yearMonthHistory.get().getParaNo());
        }
    }

}
