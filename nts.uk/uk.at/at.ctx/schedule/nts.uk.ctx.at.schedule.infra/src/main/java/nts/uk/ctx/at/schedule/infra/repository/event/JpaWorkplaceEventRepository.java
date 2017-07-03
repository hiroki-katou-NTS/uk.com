/**
 * 9:58:23 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.infra.entity.event.KsmmtWorkplaceEvent;
import nts.uk.ctx.at.schedule.infra.entity.event.KsmmtWorkplaceEventPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaWorkplaceEventRepository extends JpaRepository implements WorkplaceEventRepository {

	private final String SELECT_BY_LISTDATE = "SELECT a FROM KsmmtWorkplaceEvent a WHERE a.ksmmtWorkplaceEventPK.workplaceId = :workplaceId AND a.ksmmtWorkplaceEventPK.date IN :lstDate";

	@Override
	public List<WorkplaceEvent> getWorkplaceEventsByListDate(String workplaceId, List<BigDecimal> lstDate) {
		return this.queryProxy().query(SELECT_BY_LISTDATE, KsmmtWorkplaceEvent.class)
				.setParameter("workplaceId", workplaceId).setParameter("lstDate", lstDate).getList().stream()
				.map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public Optional<WorkplaceEvent> findByPK(String workplaceId, BigDecimal date) {
		return this.queryProxy().find(new KsmmtWorkplaceEventPK(workplaceId, date), KsmmtWorkplaceEvent.class)
				.map(entity -> toDomain(entity));
	}

	@Override
	public void addEvent(WorkplaceEvent domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void updateEvent(WorkplaceEvent domain) {
		this.commandProxy().update(fromDomain(domain));
	}

	@Override
	public void removeEvent(WorkplaceEvent domain) {
		this.commandProxy().remove(KsmmtWorkplaceEvent.class, new KsmmtWorkplaceEventPK(domain.getWorkplaceId(), domain.getDate()));;
	}

	private WorkplaceEvent toDomain(KsmmtWorkplaceEvent entity) {
		return WorkplaceEvent.createFromJavaType(entity.ksmmtWorkplaceEventPK.workplaceId,
				entity.ksmmtWorkplaceEventPK.date, entity.eventName);
	}

	private KsmmtWorkplaceEvent fromDomain(WorkplaceEvent domain) {
		return new KsmmtWorkplaceEvent(new KsmmtWorkplaceEventPK(domain.getWorkplaceId(), domain.getDate()),
				domain.getEventName().v());
	}

}
