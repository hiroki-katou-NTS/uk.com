package nts.uk.ctx.at.request.infra.repository.application.appabsence.appforspecleave;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppForSpecLeave;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppForSpecLeavePK;
@Stateless
public class JpaAppForSpecLeaveRepository extends JpaRepository implements AppForSpecLeaveRepository{

	/**
	 * get Application For Special Leave By Id
	 * @author hoatt
	 * @param companyId
	 * @param appId
	 * @return
	 */
	@Override
	public Optional<AppForSpecLeave> getAppForSpecLeaveById(String companyId, String appId) {
		return this.queryProxy().find(new KrqdtAppForSpecLeavePK(companyId, appId), KrqdtAppForSpecLeave.class)
				.map(c->toDomain(c));
	}

	private AppForSpecLeave toDomain(KrqdtAppForSpecLeave entity){
		return AppForSpecLeave.createFromJavaType(entity.getKrqdtAppForSpecLeavePK().getAppId(),
				entity.isMournerFlg(),
				entity.getRelationshipCD(),
				entity.getRelationshipReason());
	}
}
