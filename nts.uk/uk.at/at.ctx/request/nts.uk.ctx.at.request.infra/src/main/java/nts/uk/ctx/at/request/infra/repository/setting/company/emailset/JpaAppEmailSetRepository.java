package nts.uk.ctx.at.request.infra.repository.setting.company.emailset;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.emailset.Division;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppEmailSetRepository extends JpaRepository implements AppEmailSetRepository {

	@Override
	public AppEmailSet findByDivision(Division division) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppEmailSet findByCID(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
