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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmploymentHistoryRepository extends JpaRepository implements EmploymentHistoryRepository {

	private final String QUERY_BYEMPLOYEEID = "SELECT ep FROM BsymtEmploymentHist ep"
			+ " WHERE ep.sid = :sid ORDER BY ep.strDate";

	/**
	 * Convert from BsymtEmploymentHist to domain EmploymentHistory
	 * 
	 * @param sid
	 * @param listHist
	 * @return
	 */
	private EmploymentHistory toEmploymentHistory(List<BsymtEmploymentHist> listHist) {
		EmploymentHistory empment = new EmploymentHistory(listHist.get(0).getSid(), new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (BsymtEmploymentHist item : listHist) {
			dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			empment.add(dateItem);
		}
		return empment;
	}

	@Override
	public Optional<EmploymentHistory> getByEmployeeId(String sid) {
		List<BsymtEmploymentHist> listHist = this.queryProxy().query(QUERY_BYEMPLOYEEID, BsymtEmploymentHist.class)
				.setParameter("sid", sid).getList();
		if (!listHist.isEmpty()) {
			return Optional.of(toEmploymentHistory(listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(String sid, DateHistoryItem domain) {
		this.commandProxy().insert(toEntity(sid, domain));
	}

	@Override
	public void update(DateHistoryItem itemToBeUpdated) {
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(itemToBeUpdated.identifier(),
				BsymtEmploymentHist.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtEmploymentHist");
		}
		updateEntity(itemToBeUpdated, histItem.get());
		this.commandProxy().update(histItem.get());

	}

	@Override
	public void delete(String histId) {
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(histId, BsymtEmploymentHist.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtEmploymentHist");
		}
		this.commandProxy().remove(BsymtAffiWorkplaceHist.class, histId);

	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtEmploymentHist toEntity(String employeeID, DateHistoryItem item) {
		String companyId = AppContexts.user().companyId();
		return new BsymtEmploymentHist(item.identifier(), companyId, employeeID, item.start(), item.end());
	}

	/**
	 * Update entity from domain
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(DateHistoryItem item, BsymtEmploymentHist entity) {
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}

	/**
	 * Update item before when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	@SuppressWarnings("unused")
	private void updateItemBefore(EmploymentHistory domain, DateHistoryItem item) {
		// Update item before
		Optional<DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()) {
			return;
		}
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(beforeItem.get().identifier(),
				BsymtEmploymentHist.class);
		if (!histItem.isPresent()) {
			return;
		}
		updateEntity(beforeItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}

	/**
	 * Update item after when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	@SuppressWarnings("unused")
	private void updateItemAfter(EmploymentHistory domain, DateHistoryItem item) {
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()) {
			return;
		}
		Optional<BsymtEmploymentHist> histItem = this.queryProxy().find(aferItem.get().identifier(),
				BsymtEmploymentHist.class);
		if (!histItem.isPresent()) {
			return;
		}
		updateEntity(aferItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}
}
