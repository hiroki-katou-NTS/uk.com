package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.BsymtAffiWorkplaceHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmploymentHistoryRepository extends JpaRepository implements EmploymentHistoryRepository{
	
	private final String QUERY_BYEMPLOYEEID = "SELECT ep"
			+ " FROM BsymtEmploymentHist ep"
			+ " WHERE ep.sid = :sid ORDER BY ep.strDate";
	/**
	 * Convert from BsymtEmploymentHist to domain EmploymentHistory
	 * @param sid
	 * @param listHist
	 * @return
	 */
	private EmploymentHistory toEmploymentHistory(String sid, List<BsymtEmploymentHist> listHist){
		EmploymentHistory empment = new EmploymentHistory(sid, new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (BsymtEmploymentHist item : listHist){
			dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			empment.add(dateItem);
		}
		return empment;
	}
	@Override
	public Optional<EmploymentHistory> getByEmployeeId(String sid) {
		List<BsymtEmploymentHist> listHist = this.queryProxy().query(QUERY_BYEMPLOYEEID, BsymtEmploymentHist.class)
				.setParameter("sid", sid).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toEmploymentHistory(sid, listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(EmploymentHistory domain) {
		// Insert last element
		DateHistoryItem lastItem = domain.getHistoryItems().get(domain.getHistoryItems().size()-1);
		this.commandProxy().insert(toEntity(domain.getEmployeeId(),lastItem));
		
		// Update item before and after
		updateItemBefore(domain,lastItem);
		
	}

	@Override
	public void update(EmploymentHistory domain, DateHistoryItem itemToBeUpdated) {
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(itemToBeUpdated.identifier(), BsymtEmploymentHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtEmploymentHist");
		}
		updateEntity(domain.getEmployeeId(), itemToBeUpdated, histItem.get());
		this.commandProxy().update(histItem.get());
		
		// Update item before and after
		updateItemBefore(domain,itemToBeUpdated);
		updateItemAfter(domain,itemToBeUpdated);
		
	}

	@Override
	public void delete(EmploymentHistory domain, DateHistoryItem itemToBeDeleted) {
		Optional<BsymtEmploymentHist> histItem = null;
		histItem = this.queryProxy().find(itemToBeDeleted.identifier(), BsymtEmploymentHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtEmploymentHist");
		}
		this.commandProxy().remove(BsymtAffiWorkplaceHist.class, itemToBeDeleted.identifier());

		// Update last item
		if (domain.getHistoryItems().size() >0){
			DateHistoryItem lastItem = domain.getHistoryItems().get(domain.getHistoryItems().size()-1);
			histItem = this.queryProxy().find(lastItem.identifier(), BsymtEmploymentHist.class);
			if (!histItem.isPresent()){
				throw new RuntimeException("invalid BsymtEmploymentHist");
			}
			updateEntity(domain.getEmployeeId(), lastItem, histItem.get());
			this.commandProxy().update(histItem.get());
		}
		
	}
	/**
	 * Convert from domain to entity
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtEmploymentHist toEntity(String employeeID, DateHistoryItem item){
		return new BsymtEmploymentHist(item.identifier(),employeeID,item.start(),item.end());
	}
	
	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(String employeeID, DateHistoryItem item,BsymtEmploymentHist entity){	
		entity.setSid(employeeID);
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}
	/**
	 * Update item before when updating or deleting
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(EmploymentHistory domain, DateHistoryItem item){
		// Update item before
		Optional <DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()){
			return;
		}
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(beforeItem.get().identifier(), BsymtEmploymentHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(domain.getEmployeeId(), beforeItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
	
	/**
	 * Update item after when updating or deleting
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(EmploymentHistory domain, DateHistoryItem item){
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()){
			return;
		}
		Optional<BsymtEmploymentHist> histItem  = this.queryProxy().find(aferItem.get().identifier(), BsymtEmploymentHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(domain.getEmployeeId(), aferItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
}
