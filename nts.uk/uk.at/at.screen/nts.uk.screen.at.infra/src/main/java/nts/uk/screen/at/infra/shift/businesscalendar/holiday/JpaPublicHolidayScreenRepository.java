package nts.uk.screen.at.infra.shift.businesscalendar.holiday;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.holiday.KscmtPublicHoliday;
import nts.uk.screen.at.app.shift.businesscalendar.holiday.PublicHolidayScreenRepository;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaPublicHolidayScreenRepository extends JpaRepository implements PublicHolidayScreenRepository {

	private static final String GET_BY_CID_AND_DATE = "SELECT s FROM KscmtPublicHoliday s"
			+ " WHERE s.kscmtPublicHolidayPK.companyId = :companyId"
			+ " AND s.kscmtPublicHolidayPK.date >= :startDate AND s.kscmtPublicHolidayPK.date <= :endDate";

	@Override
	public List<GeneralDate> findDataPublicHoliday(String companyId, WorkplaceIdAndDateScreenParams params) {
		return this.queryProxy().query(GET_BY_CID_AND_DATE, KscmtPublicHoliday.class)
				.setParameter("companyId", companyId).setParameter("startDate", params.startDate)
				.setParameter("endDate", params.endDate).getList(x -> x.kscmtPublicHolidayPK.date);
	}

}