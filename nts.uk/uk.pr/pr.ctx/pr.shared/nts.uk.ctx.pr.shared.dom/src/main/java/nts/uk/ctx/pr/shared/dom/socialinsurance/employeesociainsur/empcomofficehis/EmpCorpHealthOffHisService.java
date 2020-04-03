package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class EmpCorpHealthOffHisService {

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
     * cps003
     * @param domain
     * @param itemAdded
     * @param histItem
     */
    public void addAllCPS003(List<EmpCorpHealthOffHisInter> domainInters){
    	
    	List<EmpCorpHealthOffParam> adds = new ArrayList<>();
    	
    	List<DateHistoryItem> itemToBeUpdatedLst = new ArrayList<>(); 
    	
    	domainInters.stream().forEach(c ->{
    		
    		EmpCorpHealthOffHis domain = c.getDomain();
    		
    		if(domain.getPeriod().isEmpty()) return;
    		
    		// Insert last element
            DateHistoryItem lastItem = domain.getPeriod().get(domain.getPeriod().size() - 1);
            
            adds.add(new EmpCorpHealthOffParam(c.getDomain().getEmployeeId(), lastItem, c.getNewHistInfo().getSocialInsurOfficeCode().v()));
    		
            //Update all item before
			Optional<DateHistoryItem> itemToBeUpdated = c.getDomain().immediatelyBefore(c.getItemToBeUpdated());
			
			if(itemToBeUpdated.isPresent()) {
				
				itemToBeUpdatedLst.add(itemToBeUpdated.get());
				
			}
    	
    	
    	});
    	
    	if(!adds.isEmpty()) {
    		
    		empCorpHealthOffHisRepository.addAll(adds);
    		
    	}
    	
    	if(!itemToBeUpdatedLst.isEmpty()) {
    		
    		updateAllBefore(itemToBeUpdatedLst);
    	
    	}
    	
    }
    
    
    public void updateAllCPS003(List<EmpCorpHealthOffHisInter> domainInters){
    	
    	List<DateHistoryItem> itemToBeUpdatedLst = new ArrayList<>(); 
    	
    	//update item
		List<EmpCorpHealthOffParam> domains = new ArrayList<>(); 
		
    	domainInters.stream().forEach(c ->{
    		
    		EmpCorpHealthOffHis domain = c.getDomain();
    		
    		if(domain.getPeriod().isEmpty()) return;
            
            domains.add(new EmpCorpHealthOffParam(domain.getEmployeeId(), c.getItemToBeUpdated(), c.getNewHistInfo().getSocialInsurOfficeCode().v()));
    		
            //Update all item before
			Optional<DateHistoryItem> itemToBeUpdated = c.getDomain().immediatelyBefore(c.getItemToBeUpdated());
			
			if(itemToBeUpdated.isPresent()) {
				
				itemToBeUpdatedLst.add(itemToBeUpdated.get());
				
			}
			
    	});
    	
    	if(!domains.isEmpty()) {
    		
    		empCorpHealthOffHisRepository.updateAllCps003(domains);
    		
    	}
    	
    	
    	if(!itemToBeUpdatedLst.isEmpty()) {
    		
    		updateAllBefore(itemToBeUpdatedLst);
    		
    	}
    	
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
