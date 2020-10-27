package nts.uk.ctx.at.request.infra.repository.application.appabsence.appforspecleave;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppHdSp;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.appforspecleave.KrqdtAppHdSpPK;
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
	public Optional<AppForSpecLeave> getAppForSpecLeaveById(String companyId, String appId) {
		return this.queryProxy().find(new KrqdtAppHdSpPK(companyId, appId), KrqdtAppHdSp.class)
				.map(c->toDomain(c));
	}

	private AppForSpecLeave toDomain(KrqdtAppHdSp entity){
		return AppForSpecLeave.createFromJavaType(entity.getKrqdtAppHdSpPK().getAppId(),
				entity.isMournerFlg(),
				entity.getRelationshipCD(),
				entity.getRelationshipReason());
	}

	@Override
	public void addSpecHd(AppForSpecLeave specHd) {
		this.commandProxy().insert(toEntity(specHd));
		
	}
	private KrqdtAppHdSp toEntity(AppForSpecLeave domain){
		val entity = new KrqdtAppHdSp();
		entity.setKrqdtAppHdSpPK(new KrqdtAppHdSpPK(AppContexts.user().companyId(), domain.getAppID()));
		entity.setVersion(new Long(0L));
		entity.setMournerFlg(domain.isMournerFlag());;
		entity.setRelationshipCD(domain.getRelationshipCD().v());;
		entity.setRelationshipReason(domain.getRelationshipReason().v());
		return entity;
	}

	@Override
	public void updateSpecHd(AppForSpecLeave specHd) {
		this.commandProxy().update(toEntity(specHd));
	}

	@Override
	public void deleteSpecHd(AppForSpecLeave specHd) {
		KrqdtAppHdSpPK key = new KrqdtAppHdSpPK(AppContexts.user().companyId(), specHd.getAppID());
		this.commandProxy().remove(KrqdtAppHdSp.class, key);
	}
}
