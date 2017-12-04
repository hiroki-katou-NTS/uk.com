package nts.uk.ctx.bs.employee.dom.employee.history;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class AffCompanyHistDomainService {
	@Inject
	private AffCompanyHistRepository affCompanyHistRepository;
	
	/** Add new affiliation history */
	public void add(AffCompanyHistByEmployee domain, String pId){
		// Insert last item
		AffCompanyHistItem itemToBeAdded = domain.getLstAffCompanyHistoryItem().get(domain.getLstAffCompanyHistoryItem().size() -1);
		affCompanyHistRepository.add(domain.getSId(), pId, itemToBeAdded);
		// Update item before
		updateItemBefore(domain,itemToBeAdded);
	}
	/** Update one affiliation history */
	public void update(AffCompanyHistByEmployee domain, AffCompanyHistItem itemToBeUpdated){

		affCompanyHistRepository.update(itemToBeUpdated);
		
		// Update item before and after
		updateItemBefore(domain, itemToBeUpdated);
		updateItemAfter(domain, itemToBeUpdated);
	}
	
	/** Delete one affiliation history */
	public void delete(AffCompanyHistByEmployee domain, AffCompanyHistItem itemToBeDelted, String pId){
		affCompanyHistRepository.remove(pId,domain.getSId(),itemToBeDelted.getHistoryId());
		
		// Update last item
		if (domain.getLstAffCompanyHistoryItem().size() > 0){
			AffCompanyHistItem itemToBeUpdated = domain.getLstAffCompanyHistoryItem().get(domain.getLstAffCompanyHistoryItem().size()-1);
			affCompanyHistRepository.update(itemToBeUpdated);
		}
	}
	/**
	 * Update item before
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(AffCompanyHistByEmployee domain, AffCompanyHistItem item){
		Optional<AffCompanyHistItem> itemToBeUpdated = domain.immediatelyBefore(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		affCompanyHistRepository.update(itemToBeUpdated.get());
	}
	/**
	 * Update item after
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(AffCompanyHistByEmployee domain, AffCompanyHistItem item){
		Optional<AffCompanyHistItem> itemToBeUpdated = domain.immediatelyAfter(item);
		if (!itemToBeUpdated.isPresent()){
			return;
		}
		affCompanyHistRepository.update(itemToBeUpdated.get());
	}
}
