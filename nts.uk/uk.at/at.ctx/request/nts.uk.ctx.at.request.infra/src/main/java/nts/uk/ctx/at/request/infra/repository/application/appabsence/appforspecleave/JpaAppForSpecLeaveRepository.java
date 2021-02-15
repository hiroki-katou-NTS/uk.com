package nts.uk.ctx.at.request.infra.repository.application.appabsence.appforspecleave;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave_Old;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppForSpecLeave;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppForSpecLeavePK;
import nts.uk.shr.com.context.AppContexts;
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
	public Optional<AppForSpecLeave_Old> getAppForSpecLeaveById(String companyId, String appId) {
		return this.queryProxy().find(new KrqdtAppForSpecLeavePK(companyId, appId), KrqdtAppForSpecLeave.class)
				.map(c->toDomain(c));
	}

	private AppForSpecLeave_Old toDomain(KrqdtAppForSpecLeave entity){
		return AppForSpecLeave_Old.createFromJavaType(entity.getKrqdtAppForSpecLeavePK().getAppId(),
				entity.isMournerFlg(),
				entity.getRelationshipCD(),
				entity.getRelationshipReason());
	}

	@Override
	public void addSpecHd(AppForSpecLeave_Old specHd) {
		this.commandProxy().insert(toEntity(specHd));
		
	}
	private KrqdtAppForSpecLeave toEntity(AppForSpecLeave_Old domain){
		val entity = new KrqdtAppForSpecLeave();
		entity.setKrqdtAppForSpecLeavePK(new KrqdtAppForSpecLeavePK(AppContexts.user().companyId(), domain.getAppID()));
		entity.setVersion(new Long(0L));
		entity.setMournerFlg(domain.isMournerFlag());;
		entity.setRelationshipCD(domain.getRelationshipCD().v());;
		entity.setRelationshipReason(domain.getRelationshipReason().v());
		return entity;
	}

	@Override
	public void updateSpecHd(AppForSpecLeave_Old specHd) {
		this.commandProxy().update(toEntity(specHd));
	}

	@Override
	public void deleteSpecHd(AppForSpecLeave_Old specHd) {
		KrqdtAppForSpecLeavePK key = new KrqdtAppForSpecLeavePK(AppContexts.user().companyId(), specHd.getAppID());
		this.commandProxy().remove(KrqdtAppForSpecLeave.class, key);
	}
}
