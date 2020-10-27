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
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KscmtEventWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KscmtEventWkpPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaWorkplaceEventRepository extends JpaRepository implements WorkplaceEventRepository {

	private static final String SELECT_BY_LISTDATE = "SELECT a FROM KscmtEventWkp a WHERE a.kscmtEventWkpPK.workplaceId = :workplaceId AND a.kscmtEventWkpPK.date IN :lstDate";

	@Override
	public List<WorkplaceEvent> getWorkplaceEventsByListDate(String workplaceId, List<GeneralDate> lstDate) {
		List<WorkplaceEvent> resultList = new ArrayList<>();
		CollectionUtil.split(lstDate, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LISTDATE, KscmtEventWkp.class)
								  .setParameter("workplaceId", workplaceId)
								  .setParameter("lstDate", subList)
								  .getList().stream()
								  .map(entity -> toDomain(entity)).collect(Collectors.toList()));
		});
		return resultList;
	}

	@Override
	public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
		return this.queryProxy().find(new KscmtEventWkpPK(workplaceId, date), KscmtEventWkp.class)
				.map(entity -> toDomain(entity));
	}

	@Override
	public void addEvent(WorkplaceEvent domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void updateEvent(WorkplaceEvent domain) {
		Optional<KscmtEventWkp> entity = this.queryProxy()
				.find(new KscmtEventWkpPK(domain.getWorkplaceId(), domain.getDate()), KscmtEventWkp.class);
		if (entity.isPresent()) {
			entity.get().eventName = domain.getEventName().v();
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void removeEvent(WorkplaceEvent domain) {
		Optional<KscmtEventWkp> entity = this.queryProxy()
				.find(new KscmtEventWkpPK(domain.getWorkplaceId(), domain.getDate()), KscmtEventWkp.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(KscmtEventWkp.class,
					new KscmtEventWkpPK(domain.getWorkplaceId(), domain.getDate()));
		}

	}

	private WorkplaceEvent toDomain(KscmtEventWkp entity) {
		return WorkplaceEvent.createFromJavaType(entity.kscmtEventWkpPK.workplaceId,
				entity.kscmtEventWkpPK.date, entity.eventName);
	}

	private KscmtEventWkp fromDomain(WorkplaceEvent domain) {
		return new KscmtEventWkp(new KscmtEventWkpPK(domain.getWorkplaceId(), domain.getDate()),
				domain.getEventName().v());
	}

}
