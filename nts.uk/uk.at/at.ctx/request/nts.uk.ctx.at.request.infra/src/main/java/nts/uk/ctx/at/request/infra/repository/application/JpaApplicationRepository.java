package nts.uk.ctx.at.request.infra.repository.application;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.infra.entity.application.KrqdpApplication;
import nts.uk.ctx.at.request.infra.entity.application.KrqdtApplication;
import nts.uk.shr.com.context.AppContexts;

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
		this.commandProxy().insert(KrqdtApplication.fromDomain(application));
		this.getEntityManager().flush();
		
	}

	@Override
	public void update(Application application) {
		this.commandProxy().update(KrqdtApplication.fromDomain(application));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String appID) {
		String companyID = AppContexts.user().companyId();
		this.commandProxy().remove(KrqdtApplication.class, new KrqdpApplication(companyID, appID));
		this.getEntityManager().flush();
	}

}
