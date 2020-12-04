package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.absenceleaveapp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.KrqdtAppRecAbsPK;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp.KrqdtAppHdSub;
import nts.uk.ctx.at.request.infra.repository.application.JpaApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV
 */
@Stateless
public class JpaAbsenceLeaveAppRepository extends JpaRepository implements AbsenceLeaveAppRepository {

	@Inject 
	private JpaApplicationRepository applicationRepo;

	@Override
	public void insert(AbsenceLeaveApp domain) {
		this.commandProxy().insert(new KrqdtAppHdSub(domain));
	}

	@Override
	public Optional<AbsenceLeaveApp> findByID(String applicationID) {
		String companyId = AppContexts.user().companyId();
		Optional<Application> application = applicationRepo.findByID(applicationID);
		if(application.isPresent()) {
			return this.queryProxy().find(new KrqdtAppRecAbsPK(companyId, applicationID), KrqdtAppHdSub.class).map(c->c.toDomain(application.get()));
		}
		return Optional.empty();
	}

	@Override
	public void update(AbsenceLeaveApp domain) {
		this.commandProxy().update(new KrqdtAppHdSub(domain));
	}

	@Override
	public void remove(String appID) {
		String companyId = AppContexts.user().companyId();
		this.commandProxy().remove(KrqdtAppHdSub.class, new KrqdtAppRecAbsPK(companyId, appID));
	}

	@Override
	public Optional<AbsenceLeaveApp> findByAppId(String applicationID) {
		return this.findByID(applicationID);
	}
	

}
