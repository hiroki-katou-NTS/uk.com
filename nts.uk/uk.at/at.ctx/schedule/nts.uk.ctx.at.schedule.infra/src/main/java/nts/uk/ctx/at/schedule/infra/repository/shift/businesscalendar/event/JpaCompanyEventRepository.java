/**
 * 9:57:57 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.event;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KsmmtCompanyEvent;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KsmmtCompanyEventPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaCompanyEventRepository extends JpaRepository implements CompanyEventRepository {

	private static final String SELECT_BY_LISTDATE = "SELECT a FROM KsmmtCompanyEvent a WHERE a.ksmmtCompanyEventPK.companyId = :companyId AND a.ksmmtCompanyEventPK.date IN :lstDate";

	@Override
	public List<CompanyEvent> getCompanyEventsByListDate(String companyId, List<GeneralDate> lstDate) {
		return this.queryProxy().query(SELECT_BY_LISTDATE, KsmmtCompanyEvent.class).setParameter("companyId", companyId)
				.setParameter("lstDate", lstDate).getList().stream().map(entity -> toDomain(entity))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<CompanyEvent> findByPK(String companyId, GeneralDate date) {
		return this.queryProxy().find(new KsmmtCompanyEventPK(companyId, date), KsmmtCompanyEvent.class)
				.map(entity -> toDomain(entity));
	}

	@Override
	public void addEvent(CompanyEvent domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void updateEvent(CompanyEvent domain) {
		Optional<KsmmtCompanyEvent> entity = this.queryProxy()
				.find(new KsmmtCompanyEventPK(domain.getCompanyId(), domain.getDate()), KsmmtCompanyEvent.class);
		if (entity.isPresent()) {
			entity.get().eventName = domain.getEventName().v();
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void removeEvent(CompanyEvent domain) {
		Optional<KsmmtCompanyEvent> entity = this.queryProxy()
				.find(new KsmmtCompanyEventPK(domain.getCompanyId(), domain.getDate()), KsmmtCompanyEvent.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(KsmmtCompanyEvent.class,
					new KsmmtCompanyEventPK(domain.getCompanyId(), domain.getDate()));
		}
	}

	private CompanyEvent toDomain(KsmmtCompanyEvent entity) {
		return CompanyEvent.createFromJavaType(entity.ksmmtCompanyEventPK.companyId, entity.ksmmtCompanyEventPK.date,
				entity.eventName);
	}

	private KsmmtCompanyEvent fromDomain(CompanyEvent domain) {
		return new KsmmtCompanyEvent(new KsmmtCompanyEventPK(domain.getCompanyId(), domain.getDate()),
				domain.getEventName().v());
	}

}
