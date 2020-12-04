package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.recruitmentapp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.KrqdtAppRecAbsPK;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.recruitmentapp.KrqdtAppRecruitment;
import nts.uk.ctx.at.request.infra.repository.application.JpaApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV
 */
@Stateless
public class JpaRecruitmentAppRepository extends JpaRepository implements RecruitmentAppRepository {

	@Inject 
	private JpaApplicationRepository applicationRepo;

	@Override
	public void insert(RecruitmentApp domain) {
		this.commandProxy().insert(new KrqdtAppRecruitment(domain));
	}

	@Override
	public Optional<RecruitmentApp> findByID(String applicationID) {
		String companyId = AppContexts.user().companyId();
		Optional<Application> application = applicationRepo.findByID(applicationID);
		if(application.isPresent()) {
			return this.queryProxy().find(new KrqdtAppRecAbsPK(companyId, applicationID), KrqdtAppRecruitment.class).map(c->c.toDomain(application.get()));
		}
		return Optional.empty();
	}

	@Override
	public void update(RecruitmentApp domain) {
		this.commandProxy().update(new KrqdtAppRecruitment(domain));
		
	}

	@Override
	public void remove(String appID) {
		String companyId = AppContexts.user().companyId();
		this.commandProxy().remove(KrqdtAppRecruitment.class, new KrqdtAppRecAbsPK(companyId, appID));
		
	}

	@Override
	public Optional<RecruitmentApp> findByAppId(String appId) {
		return this.findByID(appId);
	}


}
