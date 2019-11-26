package nts.uk.ctx.hr.develop.infra.repository.humanresourcedev.hryear;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulationRepository;
import nts.uk.ctx.hr.develop.infra.entity.humanresourcedev.hryear.JshmtHrPeriodReg;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class JpaHrPeriodRegulationRepoImpl extends JpaRepository implements HrPeriodRegulationRepository  {

	private static final String SELECT_BY_KEY_AND_CID = "SELECT c FROM JshmtHrPeriodReg c "
			+ "WHERE c.companyId = :companyId "
			+ "AND c.historyId = :historyId";

	@Override
	public Optional<HrPeriodRegulation> getByKeyAndDate(String companyId, String historyId) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_BY_KEY_AND_CID ,JshmtHrPeriodReg.class)
				.setParameter("companyId", companyId)
				.setParameter("historyId", historyId)
				.getSingle(c -> c.toDomain());
	}
}
