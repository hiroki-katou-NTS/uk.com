package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpCorpHealthOffHisService {

    @Inject
    private AffOfficeInformationRepository affOfficeInformationRepository;

    @Inject
    private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepository;

    public void addHistory(EmpCorpHealthOffHis domain){

    }

    /**
     *
     * @param domain
     * @param itemAdded
     * @param histItem
     */
    public void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation histItem){
        if(domain.getPeriod().isEmpty()){
            empCorpHealthOffHisRepository.add(domain, itemAdded, histItem);
        } else {
            val lastItem = domain.getPeriod().get(0);
            lastItem.changeSpan(new DatePeriod(lastItem.start(), itemAdded.start()));
            empCorpHealthOffHisRepository.add(domain, itemAdded, histItem);
            empCorpHealthOffHisRepository.update(lastItem);
        }
    }

    /**
     *
     * @param domain
     * @param itemUpdate
     * @param updateInfo
     */
    public void update(EmpCorpHealthOffHis domain, DateHistoryItem itemUpdate, AffOfficeInformation updateInfo){
        List<DateHistoryItem> listHist = domain.getPeriod();
        int currentIndex = listHist.indexOf(itemUpdate);
        empCorpHealthOffHisRepository.update(itemUpdate, updateInfo);
        try {
            DateHistoryItem itemBefore = listHist.get(currentIndex+1);
            itemBefore.changeSpan(new DatePeriod(itemBefore.start(), itemUpdate.start().addDays(-1)));
            empCorpHealthOffHisRepository.update(itemBefore);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    /**
     *
     * @param domain
     * @param itemDelete
     */
    public void delete(EmpCorpHealthOffHis domain, DateHistoryItem itemDelete){
        final String MAX_DATE = "9999/12/31";
        final String FORMAT_DATE_YYYYMMDD = "yyyy/MM/dd";
        List<DateHistoryItem> listHist = domain.getPeriod();
        int currentIndex = listHist.indexOf(itemDelete);
        empCorpHealthOffHisRepository.delete(itemDelete.identifier());
        try {
            DateHistoryItem itemBefore = listHist.get(currentIndex + 1);
            itemBefore.changeSpan(new DatePeriod(itemBefore.start(), itemDelete.end() == null ? itemDelete.end() : GeneralDate.fromString(MAX_DATE, FORMAT_DATE_YYYYMMDD)));
            empCorpHealthOffHisRepository.update(itemBefore);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    public void addAll(List<EmpCorpHealthOffParam> domains){
        empCorpHealthOffHisRepository.addAll(domains);
    }

    public void updateAllBefore(List<DateHistoryItem> items){
        empCorpHealthOffHisRepository.updateAll(items);
    }

}
