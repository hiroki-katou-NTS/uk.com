package nts.uk.ctx.at.request.infra.repository.application.common;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.infra.entity.application.common.KrqdpApplicationPK_New;
import nts.uk.ctx.at.request.infra.entity.application.common.KrqdtApplication_New;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaApplicationRepository_New extends JpaRepository implements ApplicationRepository_New {
	
	private final String UPDATE = "UPDATE KrqdtApplication_New a SET a.reversionReason = :reversionReason"
			+ "AND a.appReason = :appReason"
			+ "AND a.stateReflectionReal = :stateReflectionReal"
			+ "WHERE a.krqdpApplicationPK.appID = :appID AND a.krqdpApplicationPK.companyID = :companyID";
	
	@Override
	public Optional<Application_New> findByID(String companyID, String appID) {
		return this.queryProxy().find(new KrqdpApplicationPK_New(companyID, appID), KrqdtApplication_New.class)
				.map(x -> x.toDomain());
	}
	
	@Override
	public void insert(Application_New application) {
		this.commandProxy().insert(KrqdtApplication_New.fromDomain(application));
		this.getEntityManager().flush();
	}

	@Override
	public void update(Application_New application) {
		/*KrqdtApplication_New krqdtApplication = this.queryProxy()
				.find(new KrqdpApplicationPK_New(application.getCompanyID(), application.getAppID()), KrqdtApplication_New.class).get();
		krqdtApplication.reversionReason = application.getReversionReason().v();
		krqdtApplication.appReason = application.getAppReason().v();
		krqdtApplication.stateReflectionReal = application.getReflectionInformation().getStateReflectionReal().value;*/
		this.getEntityManager().createQuery(UPDATE)
			.setParameter("companyID", application.getCompanyID())
			.setParameter("appID", application.getAppID())
			.setParameter("reversionReason", application.getReversionReason().v())
			.setParameter("appReason", application.getAppReason().v())
			.setParameter("stateReflectionReal", application.getReflectionInformation().getStateReflectionReal().value)
			.executeUpdate();
	}

	@Override
	public void updateWithVersion(Application_New application) {
		// TODO Auto-generated method stub
		
	}
}
