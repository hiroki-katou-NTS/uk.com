/**
 * 9:58:23 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KsmmtWorkplaceEvent;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KsmmtWorkplaceEventPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaWorkplaceEventRepository extends JpaRepository implements WorkplaceEventRepository {

	private static final String SELECT_BY_LISTDATE = "SELECT a FROM KsmmtWorkplaceEvent a WHERE a.ksmmtWorkplaceEventPK.workplaceId = :workplaceId AND a.ksmmtWorkplaceEventPK.date IN :lstDate";

	@Override
	public List<WorkplaceEvent> getWorkplaceEventsByListDate(String workplaceId, List<GeneralDate> lstDate) {
		List<WorkplaceEvent> resultList = new ArrayList<>();
		CollectionUtil.split(lstDate, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LISTDATE, KsmmtWorkplaceEvent.class)
								  .setParameter("workplaceId", workplaceId)
								  .setParameter("lstDate", subList)
								  .getList().stream()
								  .map(entity -> toDomain(entity)).collect(Collectors.toList()));
		});
		return resultList;
	}

	@Override
	public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
		return this.queryProxy().find(new KsmmtWorkplaceEventPK(workplaceId, date), KsmmtWorkplaceEvent.class)
				.map(entity -> toDomain(entity));
	}

	@Override
	public void addEvent(WorkplaceEvent domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void updateEvent(WorkplaceEvent domain) {
		Optional<KsmmtWorkplaceEvent> entity = this.queryProxy()
				.find(new KsmmtWorkplaceEventPK(domain.getWorkplaceId(), domain.getDate()), KsmmtWorkplaceEvent.class);
		if (entity.isPresent()) {
			entity.get().eventName = domain.getEventName().v();
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void removeEvent(WorkplaceEvent domain) {
		Optional<KsmmtWorkplaceEvent> entity = this.queryProxy()
				.find(new KsmmtWorkplaceEventPK(domain.getWorkplaceId(), domain.getDate()), KsmmtWorkplaceEvent.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(KsmmtWorkplaceEvent.class,
					new KsmmtWorkplaceEventPK(domain.getWorkplaceId(), domain.getDate()));
		}

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
