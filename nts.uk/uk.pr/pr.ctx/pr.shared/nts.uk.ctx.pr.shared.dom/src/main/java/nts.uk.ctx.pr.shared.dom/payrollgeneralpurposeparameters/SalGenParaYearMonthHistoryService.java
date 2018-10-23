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
       // this.updateItemBefore(yearMonthHistory, itemToBeUpdate.get(), cId, paraNo);
    }

}
