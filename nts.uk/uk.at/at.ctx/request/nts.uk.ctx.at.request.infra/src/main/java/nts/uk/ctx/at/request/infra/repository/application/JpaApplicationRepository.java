package nts.uk.ctx.at.request.infra.repository.application;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApplicationRepository extends JpaRepository implements ApplicationRepository {

	@Override
	public Optional<Application> findByID(String companyID, String appID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Application application) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Application application) {
		// TODO Auto-generated method stub
		
	}

}
