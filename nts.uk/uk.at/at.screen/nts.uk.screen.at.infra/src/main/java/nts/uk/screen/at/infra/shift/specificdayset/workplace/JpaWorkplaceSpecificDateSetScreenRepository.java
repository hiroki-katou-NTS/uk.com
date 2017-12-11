package nts.uk.screen.at.infra.shift.specificdayset.workplace;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.workplace.KsmmtWpSpecDateSet;
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

	private static final String GET_BY_WORKPLACE_ID_AND_DATE = "SELECT s FROM KsmmtWpSpecDateSet s"
			+ " WHERE s.ksmmtWpSpecDateSetPK.workplaceId = :workplaceId"
			+ " AND s.ksmmtWpSpecDateSetPK.specificDate >= :startDate AND s.ksmmtWpSpecDateSetPK.specificDate <= :endDate";

	@Override
	public List<GeneralDate> findDataWkpSpecificDateSet(
			WorkplaceIdAndDateScreenParams params) {
		return this.queryProxy().query(GET_BY_WORKPLACE_ID_AND_DATE, KsmmtWpSpecDateSet.class)
				.setParameter("workplaceId", params.workplaceId).setParameter("startDate", params.startDate)
				.setParameter("endDate", params.endDate).getList(x -> x.ksmmtWpSpecDateSetPK.specificDate);
	}

}
