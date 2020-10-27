package nts.uk.screen.at.infra.shift.specificdayset.workplace;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KscmtSpecDateWkp;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceSpecificDateSetScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkplaceSpecificDateSetScreenRepository extends JpaRepository
		implements WorkplaceSpecificDateSetScreenRepository {

	private static final String GET_BY_WORKPLACE_ID_AND_DATE = "SELECT s FROM KscmtSpecDateWkp s"
			+ " WHERE s.kscmtSpecDateWkpPK.workplaceId = :workplaceId"
			+ " AND s.kscmtSpecDateWkpPK.specificDate >= :startDate AND s.kscmtSpecDateWkpPK.specificDate <= :endDate";

	@Override
	public List<GeneralDate> findDataWkpSpecificDateSet(
			WorkplaceIdAndDateScreenParams params) {
		return this.queryProxy().query(GET_BY_WORKPLACE_ID_AND_DATE, KscmtSpecDateWkp.class)
				.setParameter("workplaceId", params.workplaceId).setParameter("startDate", params.startDate)
				.setParameter("endDate", params.endDate).getList(x -> x.kscmtSpecDateWkpPK.specificDate);
	}

}
