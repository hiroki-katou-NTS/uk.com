/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.BsymtAffClassHistory_Ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author danpv
 * @author hop.nt
 *
 */
@Stateless
public class JpaAffClassHistory_ver1 extends JpaRepository implements AffClassHistoryRepository_ver1 {

	private static String GET_BY_EID = "select h from BsymtAffClassHistory_Ver1 h"
			+ " where h.sid = :sid and h.cid = :cid ORDER BY h.startDate";
	
	private static String GET_BY_EID_DESC = GET_BY_EID + " DESC";

	private final String GET_BY_SID_DATE = "select h from BsymtAffClassHistory_Ver1 h"
			+ " where h.sid = :sid and h.startDate <= :standardDate and h.endDate >= :standardDate";

	@Override
	public Optional<DateHistoryItem> getByHistoryId(String historyId) {

		Optional<BsymtAffClassHistory_Ver1> optionData = this.queryProxy().find(historyId,
				BsymtAffClassHistory_Ver1.class);
		if (optionData.isPresent()) {
			BsymtAffClassHistory_Ver1 entity = optionData.get();
			return Optional.of(new DateHistoryItem(entity.historyId,
					new DatePeriod(entity.startDate, entity.endDate)));
		}
		return Optional.empty();

	}

	@Override
	public Optional<DateHistoryItem> getByEmpIdAndStandardDate(String employeeId, GeneralDate standardDate) {
		Optional<BsymtAffClassHistory_Ver1> optionData = this.queryProxy()
				.query(GET_BY_SID_DATE, BsymtAffClassHistory_Ver1.class)
				.setParameter("sid", employeeId).setParameter("standardDate", standardDate).getSingle();
		if ( optionData.isPresent() ) {
			BsymtAffClassHistory_Ver1 entity = optionData.get();
			return Optional.of(new DateHistoryItem(entity.historyId,
					new DatePeriod(entity.startDate, entity.endDate)));
		}
		return Optional.empty();
	}


	private Optional<AffClassHistory_ver1> toDomain(List<BsymtAffClassHistory_Ver1> entities) {
		if (entities == null || entities.isEmpty()) {
			return Optional.empty();
		}
		AffClassHistory_ver1 domain = new AffClassHistory_ver1(entities.get(0).cid, entities.get(0).sid,
				new ArrayList<DateHistoryItem>());
		entities.forEach(entity -> {
			DateHistoryItem dateItem = new DateHistoryItem(entity.historyId,
					new DatePeriod(entity.startDate, entity.endDate));
			domain.getPeriods().add(dateItem);
		});
		return Optional.of(domain);
	}

	@Override
	public Optional<AffClassHistory_ver1> getByEmployeeId(String cid, String employeeId) {
		List<BsymtAffClassHistory_Ver1> entities = this.queryProxy().query(GET_BY_EID, BsymtAffClassHistory_Ver1.class)
				.setParameter("sid", employeeId).setParameter("cid", cid).getList();
		return toDomain(entities);
	}

	@Override
	public Optional<AffClassHistory_ver1> getByEmployeeIdDesc(String cid, String employeeId) {
		List<BsymtAffClassHistory_Ver1> entities = this.queryProxy()
				.query(GET_BY_EID_DESC, BsymtAffClassHistory_Ver1.class).setParameter("sid", employeeId)
				.setParameter("cid", cid).getList();
		return toDomain(entities);
	}

	@Override
	public void add(String cid, String sid, DateHistoryItem itemToBeAdded) {
		BsymtAffClassHistory_Ver1 entity = new BsymtAffClassHistory_Ver1(itemToBeAdded.identifier(), cid, sid,
				itemToBeAdded.start(), itemToBeAdded.end());
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(DateHistoryItem item) {
		Optional<BsymtAffClassHistory_Ver1> historyItemOpt = this.queryProxy().find(item.identifier(),
				BsymtAffClassHistory_Ver1.class);
		if (!historyItemOpt.isPresent()) {
			throw new RuntimeException("Invalid KmnmtAffClassHistory_Ver1");
		}
		BsymtAffClassHistory_Ver1 entity = historyItemOpt.get();
		entity.startDate = item.start();
		entity.endDate = item.end();
		this.commandProxy().update(entity);

	}

	@Override
	public void delete(String histId) {
		Optional<BsymtAffClassHistory_Ver1> existItem = this.queryProxy().find(histId, BsymtAffClassHistory_Ver1.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid KmnmtAffClassHistory_Ver1");
		}
		this.commandProxy().remove(BsymtAffClassHistory_Ver1.class, histId);
	}

}
