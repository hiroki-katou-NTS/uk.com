package nts.uk.ctx.at.schedule.infra.repository.shift.businesscalendar.businesscalendar;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar.StartDayItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar.StartDayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.businesscalendar.KscmtWeekstartCompany;

@Stateless
public class JpaCompanyStartDayRepository extends JpaRepository implements StartDayRepository {

	private static final String SELECT_NO_WHERE = "SELECT c FROM KscmtWeekstartCompany c ";
	private static final String SELECT_BY_COM_ID = SELECT_NO_WHERE 
			+ " WHERE c.kscmtWeekstartCompanyPK.companyId = :companyId";

	private static StartDayItem toDomain(KscmtWeekstartCompany entity) {
		StartDayItem domain = StartDayItem.createFromJavaType(entity.kscmtWeekstartCompanyPK.companyId, entity.startDay);
		return domain;
	}

	@Override
	public Optional<StartDayItem> findByCompany(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM_ID,KscmtWeekstartCompany.class)
				.setParameter("companyId", companyId)
				.getSingle(c-> toDomain(c));
	}

}
