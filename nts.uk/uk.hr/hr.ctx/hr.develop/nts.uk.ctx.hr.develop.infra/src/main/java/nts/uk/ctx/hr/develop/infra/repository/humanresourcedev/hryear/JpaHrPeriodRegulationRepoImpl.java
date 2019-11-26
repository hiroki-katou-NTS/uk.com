package nts.uk.ctx.hr.develop.infra.repository.humanresourcedev.hryear;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulation;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.HrPeriodRegulationRepository;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class JpaHrPeriodRegulationRepoImpl extends JpaRepository implements HrPeriodRegulationRepository  {

	@Override
	public Optional<HrPeriodRegulation> getByKey(String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
