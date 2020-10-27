package nts.uk.screen.at.infra.shift.specificdayset.company;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KscmtSpecDateCom;
import nts.uk.screen.at.app.shift.specificdayset.company.ComSpecificDateSetScreenRepository;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaComSpecificDateSetScreenRepository extends JpaRepository implements ComSpecificDateSetScreenRepository {

	private static final String GET_BY_CID_AND_DATE = "SELECT s FROM KscmtSpecDateCom s"
			+ " WHERE s.kscmtSpecDateComPK.companyId = :companyId"
			+ " AND s.kscmtSpecDateComPK.specificDate >= :startDate AND s.kscmtSpecDateComPK.specificDate <= :endDate";

	@Override
	public List<GeneralDate> findDataComSpecificDateSet(String companyId,
			WorkplaceIdAndDateScreenParams params) {
		return this.queryProxy().query(GET_BY_CID_AND_DATE, KscmtSpecDateCom.class)
				.setParameter("companyId", companyId).setParameter("startDate", params.startDate)
				.setParameter("endDate", params.endDate).getList(x -> x.kscmtSpecDateComPK.specificDate);
	}

}
