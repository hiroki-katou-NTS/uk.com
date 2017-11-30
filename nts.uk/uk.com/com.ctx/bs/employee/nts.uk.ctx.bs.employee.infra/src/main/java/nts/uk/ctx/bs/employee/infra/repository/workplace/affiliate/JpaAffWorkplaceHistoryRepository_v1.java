/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.BsymtAffiWorkplaceHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaAffWorkplaceHistoryRepository.
 */
@Stateless
public class JpaAffWorkplaceHistoryRepository_v1 extends JpaRepository implements AffWorkplaceHistoryRepository_v1 {
	private final String QUERY_GET_AFFWORKPLACEHIST_BYSID = "SELECT aw FROM BsymtAffiWorkplaceHist aw "
			+ "WHERE aw.sid = :sid ORDER BY aw.strDate";
	private static final String SELECT_BY_EMPID_STANDDATE = "SELECT aw FROM BsymtAffiWorkplaceHist aw"
			+ " WHERE aw.sid = :employeeId AND aw.strDate <= :standDate <= aw.endDate";
	
	/**
	 * Convert from domain to entity
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtAffiWorkplaceHist toEntity(String employeeID, DateHistoryItem item){
		return new BsymtAffiWorkplaceHist(item.identifier(),employeeID,item.start(),item.end());
	}
	
	/**
	 * Update entity from domain
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(DateHistoryItem item,BsymtAffiWorkplaceHist entity){	
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}
	/**
	 * Convert from entity to domain
	 * @param entity
	 * @return
	 */
	private AffWorkplaceHistory_ver1 toDomainTemp(String employeeId, List<BsymtAffiWorkplaceHist> listHist){
		AffWorkplaceHistory_ver1 domain = new AffWorkplaceHistory_ver1(employeeId, new ArrayList<DateHistoryItem>());
		for (BsymtAffiWorkplaceHist item : listHist){
			DateHistoryItem dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			domain.add(dateItem);
		}
		return domain;
	}
	@Override
	public Optional<AffWorkplaceHistory_ver1> getAffWorkplaceHistByEmployeeId(String employeeId) {
		List<BsymtAffiWorkplaceHist> listHist = this.queryProxy().query(QUERY_GET_AFFWORKPLACEHIST_BYSID,BsymtAffiWorkplaceHist.class)
				.setParameter("sid", employeeId).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toDomainTemp(employeeId, listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(AffWorkplaceHistory_ver1 domain) {
		if (domain.getHistoryItems().isEmpty()){
			return;
		}
		// Insert last element
		DateHistoryItem lastItem = domain.getHistoryItems().get(domain.getHistoryItems().size()-1);
		this.commandProxy().insert(toEntity(domain.getEmployeeId(),lastItem));
		
		// Update item before and after
		updateItemBefore(domain,lastItem);
		
	}

	@Override
	public void delete(AffWorkplaceHistory_ver1 domain, DateHistoryItem item) {
		
		Optional<BsymtAffiWorkplaceHist> histItem = this.queryProxy().find(item.identifier(), BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		this.commandProxy().remove(BsymtAffiWorkplaceHist.class, item.identifier());
		
		// Update last item
		if (domain.getHistoryItems().size() >0){
			DateHistoryItem lastItem = domain.getHistoryItems().get(domain.getHistoryItems().size()-1);
			histItem = this.queryProxy().find(lastItem.identifier(), BsymtAffiWorkplaceHist.class);
			if (!histItem.isPresent()){
				throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
			}
			updateEntity(lastItem, histItem.get());
			this.commandProxy().update(histItem.get());
		}
	}

	@Override
	public void update(AffWorkplaceHistory_ver1 domain, DateHistoryItem item) {
		Optional<BsymtAffiWorkplaceHist> histItem = this.queryProxy().find(item.identifier(), BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		updateEntity(item, histItem.get());
		this.commandProxy().update(histItem.get());
		
		// Update item before and after
		updateItemBefore(domain,item);
		updateItemAfter(domain,item);
	}
	/**
	 * Update item before when updating 
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(AffWorkplaceHistory_ver1 domain, DateHistoryItem item){
		// Update item before
		Optional <DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()){
			return;
		}
		Optional<BsymtAffiWorkplaceHist> histItem = this.queryProxy().find(beforeItem.get().identifier(), BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(beforeItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
	
	/**
	 * Update item after when updating
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(AffWorkplaceHistory_ver1 domain, DateHistoryItem item){
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()){
			return;
		}
		Optional<BsymtAffiWorkplaceHist> histItem  = this.queryProxy().find(aferItem.get().identifier(), BsymtAffiWorkplaceHist.class);
		if (!histItem.isPresent()){
			return;
		}
		updateEntity(aferItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}

	@Override
	public Optional<AffWorkplaceHistory_ver1> getByEmpIdAndStandDate(String employeeId, GeneralDate standDate) {
		List<BsymtAffiWorkplaceHist> listHist = this.queryProxy().query(SELECT_BY_EMPID_STANDDATE,BsymtAffiWorkplaceHist.class)
				.setParameter("employeeId", employeeId)
				.setParameter("standDate", standDate).getList();
		if (!listHist.isEmpty()){
			return Optional.of(toDomainTemp(employeeId, listHist));
		}
		return Optional.empty();
	}
	
}
