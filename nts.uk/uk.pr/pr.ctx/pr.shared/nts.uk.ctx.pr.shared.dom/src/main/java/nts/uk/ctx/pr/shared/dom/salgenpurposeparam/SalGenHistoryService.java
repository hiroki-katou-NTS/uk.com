package nts.uk.ctx.pr.shared.dom.salgenpurposeparam;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class SalGenHistoryService {
    @Inject
    private SalGenParaYMHistRepository mSalGenParaYMHistRepository;
    @Inject
    private SalGenParaDateHistRepository mSalGenParaDateHistRepository;

    private final static int MODE_HISTORY_YEARMONTH = 1;

    public void addSalGenParam(String newHistID,String paraNo, String start, String end, SalGenParaValue salGenParaValue,int modeHistory){
        String cId = AppContexts.user().companyId();
        if(modeHistory == MODE_HISTORY_YEARMONTH){
            YearMonthHistoryItem yearMonthItem = new YearMonthHistoryItem(newHistID, new YearMonthPeriod(new YearMonth(Integer.valueOf(start)), new YearMonth(Integer.valueOf(end))));
            SalGenParaYearMonthHistory itemtoBeAdded = new SalGenParaYearMonthHistory(paraNo,cId,new ArrayList<YearMonthHistoryItem>());
            Optional<SalGenParaYearMonthHistory>  salGenParaYearMonthHistory = mSalGenParaYMHistRepository.getAllSalGenParaYMHist(cId,paraNo);
            if (salGenParaYearMonthHistory.isPresent()) {
                itemtoBeAdded = salGenParaYearMonthHistory.get();
            }
            itemtoBeAdded.add(yearMonthItem);
            this.addHistory(yearMonthItem,salGenParaValue, cId,paraNo);
            this.updateItemBefore(salGenParaYearMonthHistory.get(), yearMonthItem, cId,paraNo);

        }
        else{
            DateHistoryItem dateHistoryItem = new DateHistoryItem(newHistID, new DatePeriod(GeneralDate.fromString(start,"yyyy/MM/dd"),GeneralDate.fromString(end,"yyyy/MM/dd")));
            Optional<SalGenParaDateHistory> objectHis = mSalGenParaDateHistRepository.getAllSalGenParaDateHist(AppContexts.user().companyId(),paraNo);
            SalGenParaDateHistory salGenParaDateHistory = new SalGenParaDateHistory(paraNo,AppContexts.user().companyId(), new ArrayList<DateHistoryItem>());
            if (objectHis.isPresent()) {
                salGenParaDateHistory = objectHis.get();
            }
            salGenParaDateHistory.add(dateHistoryItem);
            this.addDateHistory(dateHistoryItem, salGenParaValue,cId,paraNo);
            this.updateItemDateBefore(objectHis.get(), dateHistoryItem, cId,paraNo);

        }
    }


    private void addHistory(YearMonthHistoryItem itemtoBeAdded,SalGenParaValue domainSalGenParaValue, String cId, String paraNo){
        if(itemtoBeAdded == null){
            return;
        }
        mSalGenParaYMHistRepository.add(itemtoBeAdded,domainSalGenParaValue, cId,paraNo);
    }
    private void updateItemBefore(SalGenParaYearMonthHistory salGenParaYearMonthHistory, YearMonthHistoryItem item, String cId, String paraNo){
        Optional<YearMonthHistoryItem> itemToBeUpdated = salGenParaYearMonthHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        mSalGenParaYMHistRepository.update(itemToBeUpdated.get(),cId,paraNo);
    }


    private void addDateHistory(DateHistoryItem itemtoBeAdded,SalGenParaValue domainSalGenParaValue, String cId, String paraNo){
        if(itemtoBeAdded == null){
            return;
        }
        mSalGenParaDateHistRepository.add(itemtoBeAdded,domainSalGenParaValue,paraNo,cId);
    }
    private void updateItemDateBefore(SalGenParaDateHistory salGenParaDateHistory, DateHistoryItem item, String cId, String paraNo){
        Optional<DateHistoryItem> itemToBeUpdated = salGenParaDateHistory.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        mSalGenParaDateHistRepository.update(itemToBeUpdated.get(), paraNo,cId);
    }


}
