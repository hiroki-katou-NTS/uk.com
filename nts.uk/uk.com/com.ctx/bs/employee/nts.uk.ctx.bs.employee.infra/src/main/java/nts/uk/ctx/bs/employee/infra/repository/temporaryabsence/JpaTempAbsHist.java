package nts.uk.ctx.bs.employee.infra.repository.temporaryabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHistory;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaTempAbsHist extends JpaRepository implements TempAbsHistRepository {

	private final String QUERY_GET_TEMPORARYABSENCE_BYSID = "select ta from BsymtTempAbsHistory ta where ta.sid = :sid order by ta.startDate";

	/**
	 * Convert from domain to entity
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private BsymtTempAbsHistory toEntity(String employeeID, DateHistoryItem item) {
		return new BsymtTempAbsHistory(item.identifier(), employeeID, item.start(), item.end());
	}

	/**
	 * Update entity from domain
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(String employeeID, DateHistoryItem item, BsymtTempAbsHistory entity) {
		entity.sid = employeeID;
		entity.startDate = item.start();
		entity.endDate = item.end();
	}

	/**
	 * Convert from entity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private TempAbsenceHistory toDomain(BsymtTempAbsHistory entity) {
		TempAbsenceHistory domain = new TempAbsenceHistory(entity.sid, new ArrayList<DateHistoryItem>());

		DateHistoryItem dateItem = new DateHistoryItem(entity.histId, new DatePeriod(entity.startDate, entity.endDate));
		domain.add(dateItem);

		return domain;
	}

	@Override
	public void addTemporaryAbsenceHist(TempAbsenceHistory domain) {

		if (domain.getDateHistoryItems().size() > 0) {
			// Insert last element
			DateHistoryItem lastItem = domain.getDateHistoryItems().get(domain.getDateHistoryItems().size() - 1);
			this.commandProxy().insert(toEntity(domain.getEmployeeId(), lastItem));

			// Update item before and after
			updateItemBefore(domain, lastItem);
		}
	}

	@Override
	public void updateTemporaryAbsenceHist(TempAbsenceHistory domain, DateHistoryItem item) {

		Optional<BsymtTempAbsHistory> histItem = this.queryProxy().find(item.identifier(), BsymtTempAbsHistory.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffiWorkplaceHist");
		}
		updateEntity(domain.getEmployeeId(), item, histItem.get());
		this.commandProxy().update(histItem.get());

		// Update item before and after
		updateItemBefore(domain, item);
		updateItemAfter(domain, item);

	}

	@Override
	public void deleteTemporaryAbsenceHist(TempAbsenceHistory domain, DateHistoryItem item) {
		Optional<BsymtTempAbsHistory> histItem = null;
		histItem = this.queryProxy().find(item.identifier(), BsymtTempAbsHistory.class);
		if (!histItem.isPresent()) {
			throw new RuntimeException("invalid BsymtTempAbsHistory");
		}
		this.commandProxy().remove(BsymtTempAbsHistory.class, item.identifier());

		// Update item before
		if (domain.getDateHistoryItems().size() > 0) {
			DateHistoryItem lastItem = domain.getDateHistoryItems().get(domain.getDateHistoryItems().size() - 1);
			histItem = this.queryProxy().find(lastItem.identifier(), BsymtTempAbsHistory.class);
			if (!histItem.isPresent()) {
				throw new RuntimeException("invalid BsymtTempAbsHistory");
			}
			updateEntity(domain.getEmployeeId(), lastItem, histItem.get());
			this.commandProxy().update(histItem.get());
		}
	}

	/**
	 * Update item before when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(TempAbsenceHistory domain, DateHistoryItem item) {
		// Update item before
		Optional<DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()) {
			return;
		}
		Optional<BsymtTempAbsHistory> histItem = this.queryProxy().find(beforeItem.get().identifier(),
				BsymtTempAbsHistory.class);
		if (histItem.isPresent()) {
			updateEntity(domain.getEmployeeId(), beforeItem.get(), histItem.get());
			this.commandProxy().update(histItem.get());
		}
	}

	/**
	 * Update item after when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(TempAbsenceHistory domain, DateHistoryItem item) {
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()) {
			return;
		}
		Optional<BsymtTempAbsHistory> histItem = this.queryProxy().find(aferItem.get().identifier(),
				BsymtTempAbsHistory.class);
		if (histItem.isPresent()) {
			updateEntity(domain.getEmployeeId(), aferItem.get(), histItem.get());
			this.commandProxy().update(histItem.get());
		}
	}

	@Override
	public Optional<TempAbsenceHistory> getByHistId(String histId) {
		Optional<BsymtTempAbsHistory> existItem = this.queryProxy().find(histId, BsymtTempAbsHistory.class);
		if (existItem.isPresent()) {
			return Optional.of(toDomain(existItem.get()));
		}
		return Optional.empty();
	}

	private TempAbsenceHistory toDomainTemp(String employeeId, List<BsymtTempAbsHistory> listHist) {
		TempAbsenceHistory domain = new TempAbsenceHistory(employeeId, new ArrayList<DateHistoryItem>());
		for (BsymtTempAbsHistory item : listHist) {
			DateHistoryItem dateItem = new DateHistoryItem(item.histId, new DatePeriod(item.startDate, item.endDate));
			domain.add(dateItem);
		}
		return domain;
	}

	@Override
	public Optional<TempAbsenceHistory> getByEmployeeId(String employeeId) {
		List<BsymtTempAbsHistory> listHist = this.queryProxy()
				.query(QUERY_GET_TEMPORARYABSENCE_BYSID, BsymtTempAbsHistory.class).setParameter("sid", employeeId)
				.getList();
		if (!listHist.isEmpty()) {
			return Optional.of(toDomainTemp(employeeId, listHist));
		}
		return Optional.empty();
	}

}
