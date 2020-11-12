package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistoryInter;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * process 
 * @author Trung Tran
 *
 */
@Stateless
public class BusinessTypeOfHistoryGeneralRepositoryImpl implements BusinessTypeOfHistoryGeneralRepository {

	@Inject
	private BusinessTypeEmpOfHistoryRepository historyRepos;

	@Override
	public void addBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory) {
		List<DateHistoryItem> history = bEmployeeHistory.getHistory();
		DateHistoryItem item = history.get(history.size() - 1);
		historyRepos.add(bEmployeeHistory.getCompanyId(), bEmployeeHistory.getEmployeeId(), item.identifier(),
				item.start(), item.end());
		updateItemBefore(bEmployeeHistory, item);
	}

	@Override
	public void updateBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item) {
		historyRepos.update(bEmployeeHistory.getCompanyId(), bEmployeeHistory.getEmployeeId(), item.identifier(),
				item.start(), item.end());
		updateItemBefore(bEmployeeHistory, item);
	}

	public void updateItemBefore(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item) {
		Optional<DateHistoryItem> optinal = bEmployeeHistory.immediatelyBefore(item);
		if (optinal.isPresent()) {
			DateHistoryItem itemToBeUpdate = optinal.get();
			historyRepos.update(bEmployeeHistory.getCompanyId(), bEmployeeHistory.getEmployeeId(),
					itemToBeUpdate.identifier(), itemToBeUpdate.start(), itemToBeUpdate.end());
		}
	}

	@Override
	public void deleteBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item) {
		historyRepos.delete(item.identifier());
		if (bEmployeeHistory.getHistory().size() > 0) {
			List<DateHistoryItem> history = bEmployeeHistory.getHistory();
			DateHistoryItem itemToBeUpdate = history.get(history.size() - 1);
			historyRepos.update(bEmployeeHistory.getCompanyId(), bEmployeeHistory.getEmployeeId(),
					itemToBeUpdate.identifier(), itemToBeUpdate.start(), itemToBeUpdate.end());

		}
	}

	/**
	 * @author lanlt
	 * addAll
	 */
	@Override
	public void addAll(List<BusinessTypeOfEmployeeHistory> domains) {
		Map<String, DateHistoryItem> dateHistItemMaps = new HashMap<>();
		domains.stream().forEach(c ->{
			List<DateHistoryItem> history = c.getHistory();
			DateHistoryItem item = history.get(history.size() - 1);
			dateHistItemMaps.put(c.getEmployeeId(), item);
		});
		
		if(!dateHistItemMaps.isEmpty()) {
			historyRepos.addAll(dateHistItemMaps);
			updateAllItemBefore(domains);
		}
	}
		
		
	
	/**
	 * @author lanlt
	 * updateAllItemBefore - cps003
	 * @param histories
	 */
	private void updateAllItemBefore(List<BusinessTypeOfEmployeeHistory> histories) {
		Map<String, DateHistoryItem> dateHistItemMaps = new HashMap<>();
		// Update item before
		histories.stream().forEach(c ->{
			if(c.getHistory().size() > 1) {
				int max = c.getHistory().size();
				DateHistoryItem historyItem  = c.getHistory().get(max - 1);
				Optional<DateHistoryItem> beforeItemOpt = c.immediatelyBefore(historyItem);
				if (!beforeItemOpt.isPresent()) {
					return;
				}
				dateHistItemMaps.put(c.getEmployeeId(), beforeItemOpt.get());
			}
		});
		if(!dateHistItemMaps.isEmpty()) {
			historyRepos.updateAll(dateHistItemMaps);
		}
	}

	/**
	 * @author lanlt
	 * updateAll
	 */
	@Override
	public void updateAll(List<BusinessTypeOfEmployeeHistoryInter> domainsInter) {
		Map<String, DateHistoryItem> dateHistItemMaps = new HashMap<>();
		domainsInter.stream().forEach(c ->{
			dateHistItemMaps.put(c.getBEmployeeHistory().getEmployeeId(), c.getItem());
		});
		
		if(!dateHistItemMaps.isEmpty()) {
			historyRepos.updateAll(dateHistItemMaps);		
		}
		updateAllItemsBefore(domainsInter);
	}
	
	/**
	 * @author lanlt
	 * updateAllItemsBefore
	 * @param domainsInter
	 */
	public void updateAllItemsBefore(List<BusinessTypeOfEmployeeHistoryInter> domainsInter) {
		Map<String, DateHistoryItem> dateHistItemMaps = new HashMap<>();

		domainsInter.stream().forEach(c -> {
			Optional<DateHistoryItem> optinal = c.getBEmployeeHistory().immediatelyBefore(c.getItem());

			if (optinal.isPresent()) {
				DateHistoryItem itemToBeUpdate = optinal.get();
				dateHistItemMaps.put(c.getBEmployeeHistory().getEmployeeId(), itemToBeUpdate);
			}

		});

		if (!dateHistItemMaps.isEmpty()) {
			historyRepos.updateAll(dateHistItemMaps);
		}
	}
}
