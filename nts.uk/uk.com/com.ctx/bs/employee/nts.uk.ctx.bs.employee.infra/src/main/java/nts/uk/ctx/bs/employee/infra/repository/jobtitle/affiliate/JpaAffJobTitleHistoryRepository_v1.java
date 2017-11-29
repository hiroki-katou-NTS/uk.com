package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.BsymtAffJobTitleHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAffJobTitleHistoryRepository_v1 extends JpaRepository implements AffJobTitleHistoryRepository_ver1 {

	private final String QUERY_GET_AFFJOBTITLEHIST_BYSID = "select jb " + "from BsymtAffJobTitleHist jb "
			+ "where jb.sid = :sid order by jb.strDate";

	/**
	 * Convert from domain to entity
	 * 
	 * @param employeeId
	 * @param listHist
	 * @return
	 */
	private AffJobTitleHistory_ver1 toAffJobTitleHist(String employeeId, List<BsymtAffJobTitleHist> listHist) {
		AffJobTitleHistory_ver1 domain = new AffJobTitleHistory_ver1(employeeId, new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (BsymtAffJobTitleHist item : listHist) {
			dateItem = new DateHistoryItem(item.getHisId(), new DatePeriod(item.getStrDate(), item.getEndDate()));
			domain.add(dateItem);
		}
		return domain;
	}

	/**
	 * Convert from domain to BsymtAffJobTitleHist entity
	 * 
	 * @param sId
	 * @param domain
	 * @return
	 */
	private BsymtAffJobTitleHist toEntity(String sId, DateHistoryItem domain) {
		return new BsymtAffJobTitleHist(domain.identifier(), sId, domain.start(), domain.end());
	}

	@Override
	public Optional<AffJobTitleHistory_ver1> getListBySid(String sid) {
		// TODO Auto-generated method stub
		List<BsymtAffJobTitleHist> listHist = this.queryProxy()
				.query(QUERY_GET_AFFJOBTITLEHIST_BYSID, BsymtAffJobTitleHist.class).setParameter("sid", sid).getList();
		if (!listHist.isEmpty()) {
			return Optional.of(toAffJobTitleHist(sid, listHist));
		}
		return Optional.empty();
	}

	@Override
	public void add(AffJobTitleHistory_ver1 domain) {
		if (domain.getHistoryItems().isEmpty()) {
			return;
		}
		DateHistoryItem itemToBeAdded = domain.getHistoryItems().get(domain.getHistoryItems().size() - 1);
		this.commandProxy().insert(toEntity(domain.getEmployeeId(), itemToBeAdded));

		// Update item before
		updateItemBefore(domain, itemToBeAdded);
	}

	@Override
	public void update(AffJobTitleHistory_ver1 domain, DateHistoryItem item) {
		Optional<BsymtAffJobTitleHist> itemToBeUpdated = this.queryProxy().find(item.identifier(),
				BsymtAffJobTitleHist.class);

		if (!itemToBeUpdated.isPresent()) {
			throw new RuntimeException("Invalid BsymtAffJobTitleHist");
		}
		// Update entity
		updateEntity(domain.getEmployeeId(), item, itemToBeUpdated.get());
		this.commandProxy().update(itemToBeUpdated.get());

		// Update item before and after
		updateItemBefore(domain, item);
		updateItemAfter(domain, item);
	}

	@Override
	public void delete(AffJobTitleHistory_ver1 domain, DateHistoryItem item) {
		Optional<BsymtAffJobTitleHist> itemToBeDeleted = this.queryProxy().find(item.identifier(),
				BsymtAffJobTitleHist.class);

		if (!itemToBeDeleted.isPresent()) {
			throw new RuntimeException("Invalid BsymtAffJobTitleHist");
		}
		this.commandProxy().remove(BsymtAffJobTitleHist.class, item.identifier());
	}

	/**
	 * Update item before when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	private void updateItemBefore(AffJobTitleHistory_ver1 domain, DateHistoryItem item) {
		// Update item before
		Optional<DateHistoryItem> beforeItem = domain.immediatelyBefore(item);
		if (!beforeItem.isPresent()) {
			return;
		}
		Optional<BsymtAffJobTitleHist> histItem = this.queryProxy().find(beforeItem.get().identifier(),
				BsymtAffJobTitleHist.class);
		if (!histItem.isPresent()) {
			return;
		}
		updateEntity(domain.getEmployeeId(), beforeItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}

	/**
	 * Update entity from domain
	 * 
	 * @param employeeID
	 * @param item
	 * @return
	 */
	private void updateEntity(String employeeID, DateHistoryItem item, BsymtAffJobTitleHist entity) {
		entity.setSid(employeeID);
		entity.setStrDate(item.start());
		entity.setEndDate(item.end());
	}

	/**
	 * Update item after when updating or deleting
	 * 
	 * @param domain
	 * @param item
	 */
	private void updateItemAfter(AffJobTitleHistory_ver1 domain, DateHistoryItem item) {
		// Update item after
		Optional<DateHistoryItem> aferItem = domain.immediatelyAfter(item);
		if (!aferItem.isPresent()) {
			return;
		}
		Optional<BsymtAffJobTitleHist> histItem = this.queryProxy().find(aferItem.get().identifier(),
				BsymtAffJobTitleHist.class);
		if (!histItem.isPresent()) {
			return;
		}
		updateEntity(domain.getEmployeeId(), aferItem.get(), histItem.get());
		this.commandProxy().update(histItem.get());
	}

	@Override
	public Optional<AffJobTitleHistory_ver1> getByHistoryId(String historyId) {
		Optional<BsymtAffJobTitleHist> optionData = this.queryProxy().find(historyId, BsymtAffJobTitleHist.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}

	private AffJobTitleHistory_ver1 toDomain(BsymtAffJobTitleHist ent) {
		AffJobTitleHistory_ver1 domain = new AffJobTitleHistory_ver1(ent.getSid(), new ArrayList<>());
		DateHistoryItem dateItem = new DateHistoryItem(ent.getHisId(),
				new DatePeriod(ent.getStrDate(), ent.getEndDate()));

		domain.add(dateItem);

		return domain;
	}

}
