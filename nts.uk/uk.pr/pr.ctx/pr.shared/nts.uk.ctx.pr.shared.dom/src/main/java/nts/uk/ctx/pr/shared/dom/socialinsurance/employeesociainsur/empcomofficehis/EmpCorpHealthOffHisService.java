package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.val;
import nts.uk.shr.com.history.DateHistoryItem;

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

    /**
     *
     * @param domain
     * @param itemAdded
     * @param histItem
     */
    public void add(EmpCorpHealthOffHis domain, DateHistoryItem itemAdded, AffOfficeInformation histItem){
        // Insert last element
        DateHistoryItem lastItem = domain.getPeriod().get(domain.getPeriod().size()-1);
        empCorpHealthOffHisRepository.add(domain, lastItem, histItem);
        updateItemBefore(domain, lastItem);
    }

    /**
     *
     * @param domain
     * @param itemUpdate
     * @param updateInfo
     */
    public void update(EmpCorpHealthOffHis domain, DateHistoryItem itemUpdate, AffOfficeInformation updateInfo){
        empCorpHealthOffHisRepository.update(itemUpdate, updateInfo);
        updateItemBefore(domain, itemUpdate);
    }

    /**
     *
     * @param domain
     * @param itemDelete
     */
    public void delete(EmpCorpHealthOffHis domain, DateHistoryItem itemDelete){
        empCorpHealthOffHisRepository.delete(itemDelete.identifier(), domain.getEmployeeId());
        List<DateHistoryItem> listHist = domain.getPeriod();
        if (listHist.size() > 0) {
            val lastItem = listHist.get(0);
            domain.exCorrectToRemove(lastItem);
            empCorpHealthOffHisRepository.update(lastItem);
        }
    }

    /**
     * Update item before
     * @param domain
     * @param item
     */
    private void updateItemBefore(EmpCorpHealthOffHis domain, DateHistoryItem item){
        Optional<DateHistoryItem> itemToBeUpdated = domain.immediatelyBefore(item);
        if (!itemToBeUpdated.isPresent()){
            return;
        }
        empCorpHealthOffHisRepository.update(itemToBeUpdated.get());
    }

    public void addAll(List<EmpCorpHealthOffParam> domains){
        empCorpHealthOffHisRepository.addAll(domains);
    }

    public void updateAllBefore(List<DateHistoryItem> items){
        empCorpHealthOffHisRepository.updateAll(items);
    }

}
