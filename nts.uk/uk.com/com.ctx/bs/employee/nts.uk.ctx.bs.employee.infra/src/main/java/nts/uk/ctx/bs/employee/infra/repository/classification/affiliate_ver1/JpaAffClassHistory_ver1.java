/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.KmnmtAffClassHistory_Ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaAffClassHistory_ver1 extends JpaRepository implements AffClassHistoryRepository_ver1 {

	private static String GET_BY_EID = "select h from KmnmtAffClassHistory_Ver1 h where h.sid = :sid";

	@Override
	public Optional<AffClassHistory_ver1> getByHistoryId(String historyId) {
		Optional<KmnmtAffClassHistory_Ver1> optionData = this.queryProxy().find(historyId,
				KmnmtAffClassHistory_Ver1.class);
		if (optionData.isPresent()) {
			KmnmtAffClassHistory_Ver1 ent = optionData.get();
			return Optional.of(toDomain(ent));
		}
		return Optional.empty();
	}

	private AffClassHistory_ver1 toDomain(KmnmtAffClassHistory_Ver1 entity) {
		AffClassHistory_ver1 domain = new AffClassHistory_ver1(entity.sid, new ArrayList<DateHistoryItem>());

		DateHistoryItem dateItem = new DateHistoryItem(entity.historyId,
				new DatePeriod(entity.startDate, entity.endDate));
		domain.add(dateItem);

		return domain;
	}

	private Optional<AffClassHistory_ver1> toDomain(List<KmnmtAffClassHistory_Ver1> entities) {
		if (entities.isEmpty()) {
			return Optional.empty();
		}
		AffClassHistory_ver1 domain = new AffClassHistory_ver1(entities.get(0).sid, new ArrayList<DateHistoryItem>());
		entities.forEach(entity -> {
			DateHistoryItem dateItem = new DateHistoryItem(entity.historyId,
					new DatePeriod(entity.startDate, entity.endDate));
			domain.add(dateItem);
		});
		return Optional.of(domain);
	}

	@Override
	public Optional<AffClassHistory_ver1> getByEmployeeId(String employeeId) {
		List<KmnmtAffClassHistory_Ver1> entities = this.queryProxy().query(GET_BY_EID, KmnmtAffClassHistory_Ver1.class)
				.getList();
		return toDomain(entities);
	}

	@Override
	public void add(AffClassHistory_ver1 history) {
		if (history.getPeriods().isEmpty()) {
			return;
		}
		List<DateHistoryItem> periods = history.getPeriods();
		DateHistoryItem historyItem = periods.get(periods.size() - 1);
		KmnmtAffClassHistory_Ver1 entity = new KmnmtAffClassHistory_Ver1(historyItem.identifier(),
				history.getEmployeeId(), historyItem.start(), historyItem.end());
		this.commandProxy().insert(entity);

		updateItemBefore(history, historyItem);
	}

	@Override
	public void update(AffClassHistory_ver1 history, DateHistoryItem item) {
		Optional<KmnmtAffClassHistory_Ver1> historyItemOpt = this.queryProxy().find(item.identifier(),
				KmnmtAffClassHistory_Ver1.class);
		if (!historyItemOpt.isPresent()) {
			throw new RuntimeException("Invalid KmnmtAffClassHistory_Ver1");
		}
		KmnmtAffClassHistory_Ver1 entity = historyItemOpt.get();
		entity.startDate = item.start();
		entity.endDate = item.end();
		this.commandProxy().update(entity);

		updateItemBefore(history, item);
		updateItemAfter(history, item);

	}

	private void updateItemBefore(AffClassHistory_ver1 history, DateHistoryItem item) {
		// Update item before
		Optional<DateHistoryItem> beforeItemOpt = history.immediatelyBefore(item);

		if (beforeItemOpt.isPresent()) {

			Optional<KmnmtAffClassHistory_Ver1> beforeEntOpt = this.queryProxy().find(beforeItemOpt.get().identifier(),
					KmnmtAffClassHistory_Ver1.class);

			if (beforeEntOpt.isPresent()) {

				KmnmtAffClassHistory_Ver1 beforeEnt = beforeEntOpt.get();
				beforeEnt.endDate = beforeItemOpt.get().end();

				this.commandProxy().update(beforeEnt);
			}

		}

	}

	private void updateItemAfter(AffClassHistory_ver1 history, DateHistoryItem item) {
		// Update item before
		Optional<DateHistoryItem> afterItemOpt = history.immediatelyAfter(item);

		if (afterItemOpt.isPresent()) {

			Optional<KmnmtAffClassHistory_Ver1> afterEntOpt = this.queryProxy().find(afterItemOpt.get().identifier(),
					KmnmtAffClassHistory_Ver1.class);

			if (afterEntOpt.isPresent()) {

				KmnmtAffClassHistory_Ver1 afterEnt = afterEntOpt.get();
				afterEnt.startDate = afterItemOpt.get().start();

				this.commandProxy().update(afterEnt);
			}

		}

	}

}
