/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

import java.util.ArrayList;
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

}
