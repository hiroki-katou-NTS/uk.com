/**
 * 9:57:57 AM Jun 13, 2017
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
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KscmtEventCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.event.KscmtEventComPK;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaCompanyEventRepository extends JpaRepository implements CompanyEventRepository {

	private static final String SELECT_BY_LISTDATE = "SELECT a FROM KscmtEventCom a WHERE a.kscmtEventComPK.companyId = :companyId AND a.kscmtEventComPK.date IN :lstDate";

	@Override
	public List<CompanyEvent> getCompanyEventsByListDate(String companyId, List<GeneralDate> lstDate) {
		List<CompanyEvent> resultList = new ArrayList<>();
		CollectionUtil.split(lstDate, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LISTDATE, KscmtEventCom.class)
								  .setParameter("companyId", companyId)
								  .setParameter("lstDate", subList)
								  .getList().stream().map(entity -> toDomain(entity))
								  .collect(Collectors.toList()));
		});
		return resultList;
	}

	@Override
	public Optional<CompanyEvent> findByPK(String companyId, GeneralDate date) {
		return this.queryProxy().find(new KscmtEventComPK(companyId, date), KscmtEventCom.class)
				.map(entity -> toDomain(entity));
	}

	@Override
	public void addEvent(CompanyEvent domain) {
		this.commandProxy().insert(fromDomain(domain));
	}

	@Override
	public void updateEvent(CompanyEvent domain) {
		Optional<KscmtEventCom> entity = this.queryProxy()
				.find(new KscmtEventComPK(domain.getCompanyId(), domain.getDate()), KscmtEventCom.class);
		if (entity.isPresent()) {
			entity.get().eventName = domain.getEventName().v();
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void removeEvent(CompanyEvent domain) {
		Optional<KscmtEventCom> entity = this.queryProxy()
				.find(new KscmtEventComPK(domain.getCompanyId(), domain.getDate()), KscmtEventCom.class);
		if (entity.isPresent()) {
			this.commandProxy().remove(KscmtEventCom.class,
					new KscmtEventComPK(domain.getCompanyId(), domain.getDate()));
		}
	}

	private CompanyEvent toDomain(KscmtEventCom entity) {
		return CompanyEvent.createFromJavaType(entity.kscmtEventComPK.companyId, entity.kscmtEventComPK.date,
				entity.eventName);
	}

	private KscmtEventCom fromDomain(CompanyEvent domain) {
		return new KscmtEventCom(new KscmtEventComPK(domain.getCompanyId(), domain.getDate()),
				domain.getEventName().v());
	}

}
