package nts.uk.ctx.at.request.infra.repository.setting.employment.appemploymentsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppEmploymentSetRepository extends JpaRepository implements AppEmploymentSetRepository {

	@Override
	public Optional<AppEmploymentSet> findByCompanyIDAndEmploymentCD(String companyID, String employmentCD) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
