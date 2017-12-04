/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.KmnmtAffClassHistory_Ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author danpv
 * @author hop.nt
 *
 */
@Stateless
public class JpaAffClassHistory_ver1 extends JpaRepository implements AffClassHistoryRepository_ver1 {

	private static String GET_BY_EID = "select h from KmnmtAffClassHistory_Ver1 h where h.sid = :sid ORDER BY h.startDate";

	@Override
	public Optional<AffClassHistory_ver1> getByHistoryId(String historyId) {
		/*
		 * Optional<KmnmtAffClassHistory_Ver1> optionData =
		 * this.queryProxy().find(historyId, KmnmtAffClassHistory_Ver1.class);
		 * if (optionData.isPresent()) { KmnmtAffClassHistory_Ver1 ent =
		 * optionData.get(); return Optional.of(toDomain(ent)); } return
		 * Optional.empty();
		 */
		return Optional.of(
				new AffClassHistory_ver1("909909139840", Arrays.asList(new DateHistoryItem("98765432109876543210654321",
						new DatePeriod(GeneralDate.ymd(2017, 1, 15), GeneralDate.ymd(2017, 6, 15))))));
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
		List<KmnmtAffClassHistory_Ver1> entities = 
				this.queryProxy().query(GET_BY_EID, KmnmtAffClassHistory_Ver1.class)
				.setParameter("sid", employeeId)
				.getList();
		return toDomain(entities);
	}

	@Override
	public void add(String sid, DateHistoryItem itemToBeAdded) {
		KmnmtAffClassHistory_Ver1 entity = new KmnmtAffClassHistory_Ver1(itemToBeAdded.identifier(),
				sid, itemToBeAdded.start(), itemToBeAdded.end());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(DateHistoryItem item) {
		Optional<KmnmtAffClassHistory_Ver1> historyItemOpt = this.queryProxy().find(item.identifier(),
				KmnmtAffClassHistory_Ver1.class);
		if (!historyItemOpt.isPresent()) {
			throw new RuntimeException("Invalid KmnmtAffClassHistory_Ver1");
		}
		KmnmtAffClassHistory_Ver1 entity = historyItemOpt.get();
		entity.startDate = item.start();
		entity.endDate = item.end();
		this.commandProxy().update(entity);

	}

	@Override
	public void delete(String histId) {
		Optional<KmnmtAffClassHistory_Ver1> existItem = this.queryProxy().find(histId, KmnmtAffClassHistory_Ver1.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid KmnmtAffClassHistory_Ver1");
		}
		this.commandProxy().remove(KmnmtAffClassHistory_Ver1.class, histId);
	}

}
