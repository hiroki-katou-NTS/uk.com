package nts.uk.screen.at.infra.shift.specificdayset.company;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company.KsmmtComSpecDateSet;
import nts.uk.screen.at.app.shift.specificdayset.company.ComSpecificDateSetScreenRepository;
import nts.uk.screen.at.app.shift.specificdayset.company.StartDateEndDateScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaComSpecificDateSetScreenRepository extends JpaRepository implements ComSpecificDateSetScreenRepository {

	private static final String GET_BY_CID_AND_DATE = "SELECT s FROM KsmmtComSpecDateSet s"
			+ " WHERE s.ksmmtComSpecDateSetPK.companyId = :companyId"
			+ " AND s.ksmmtComSpecDateSetPK.specificDate >= :startDate AND s.ksmmtComSpecDateSetPK.specificDate <= :endDate";

	@Override
	public List<BigDecimal> findDataComSpecificDateSet(String companyId,
			StartDateEndDateScreenParams params) {
		return this.queryProxy().query(GET_BY_CID_AND_DATE, KsmmtComSpecDateSet.class)
				.setParameter("companyId", companyId).setParameter("startDate", params.startDate)
				.setParameter("endDate", params.endDate).getList(x -> x.ksmmtComSpecDateSetPK.specificDate);
	}

}
