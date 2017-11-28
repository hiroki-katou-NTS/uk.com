package nts.uk.ctx.bs.employee.infra.repository.department.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.department.BsymtAffiDepartmentHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAffDepartmentHistoryRepository  extends JpaRepository implements AffDepartmentHistoryRepository{
	private final String QUERY_GET_AFFDEPARTMENT_BYSID = "select ad "
			+ "from BsymtAffiDepartmentHist ad "
			+ "where ad.sid = :sid order by ad.strDate";
	
	private AffDepartmentHistory toAffDepartment(String sId, List<BsymtAffiDepartmentHist> listHist){
		AffDepartmentHistory affDepart = new AffDepartmentHistory(sId, new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (BsymtAffiDepartmentHist item : listHist){
			dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			affDepart.add(dateItem);
		}
		return affDepart;
	}
	
	@Override
	public Optional<AffDepartmentHistory> getAffDepartmentHistorytByEmployeeId(String employeeId) {
		
		List<BsymtAffiDepartmentHist> listHist = this.queryProxy().query(QUERY_GET_AFFDEPARTMENT_BYSID,BsymtAffiDepartmentHist.class)
				.setParameter("sid", employeeId).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toAffDepartment(employeeId,listHist));
		}
		return Optional.empty();
	}

	@Override
	public void addAffDepartment(AffDepartmentHistory domain) {
		DateHistoryItem itemToBeAdded = domain.getHistoryItems().get(domain.getHistoryItems().size() -1);
		this.commandProxy().insert(toEntity(domain.getEmployeeId(), itemToBeAdded));
		// Update item before
		updateItemBefore(domain, itemToBeAdded);
	}

	@Override
	public void updateAffDepartment(AffDepartmentHistory domain, DateHistoryItem item) {
		Optional<BsymtAffiDepartmentHist> itemToBeUpdated = this.queryProxy().find(item.identifier(), BsymtAffiDepartmentHist.class);
		if (!itemToBeUpdated.isPresent()){
			throw new RuntimeException("Invalid BsymtAffiDepartmentHist");
		}
		updateEntity(domain.getEmployeeId(),item, itemToBeUpdated.get());
		this.commandProxy().update(itemToBeUpdated.get());
		
		// Update item before and after
		updateItemBefore(domain, item);
		updateItemAfter(domain, item);
	}

	@Override
	public void deleteAffDepartment(AffDepartmentHistory domain, DateHistoryItem item) {
		Optional<BsymtAffiDepartmentHist> itemToBeDeleted = this.queryProxy().find(item.identifier(), BsymtAffiDepartmentHist.class);
		if (!itemToBeDeleted.isPresent()){
			throw new RuntimeException("Invalid BsymtAffiDepartmentHist");
		}
		this.commandProxy().remove(BsymtAffiDepartmentHist.class, item.identifier());
		
		// Update item before
		if (domain.getHistoryItems().size() >0){
			DateHistoryItem lastItem = domain.getHistoryItems().get(domain.getHistoryItems().size()-1);
			Optional<BsymtAffiDepartmentHist> histItem  = this.queryProxy().find(lastItem.identifier(), BsymtAffiDepartmentHist.class);
			if (!histItem.isPresent()){
				throw new RuntimeException("invalid BsymtAffiDepartmentHist");
			}
			updateEntity(domain.getEmployeeId(), lastItem, histItem.get());
			this.commandProxy().update(histItem.get());
		}
		
	}
	
	private BsymtAffiDepartmentHist toEntity(String employeeId, DateHistoryItem item){
		return new BsymtAffiDepartmentHist(item.identifier(), employeeId, item.start(), item.end());
	}
	/**
	 * Update item before when updating or deleting
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(AffDepartmentHistory domain, DateHistoryItem item){
		// Update item before
		Optional <DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()){
			return;
		}
		Optional<BsymtAffiDepartmentHist> histItem = this.queryProxy().find(beforeItem.get().identifier(), BsymtAffiDepartmentHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(domain.getEmployeeId(), beforeItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(String employeeID, DateHistoryItem item,BsymtAffiDepartmentHist entity){	
		entity.setSid(employeeID);
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}
	/**
	 * Update item after when updating or deleting
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(AffDepartmentHistory domain, DateHistoryItem item){
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()){
			return;
		}
		Optional<BsymtAffiDepartmentHist> histItem  = this.queryProxy().find(aferItem.get().identifier(), BsymtAffiDepartmentHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(domain.getEmployeeId(), aferItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
}
