package nts.uk.ctx.at.request.infra.repository.application.common.appapprovalphase;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhasePK;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeave;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeavePK;


public class JpaAppApprovalPhaseRepository extends JpaRepository implements AppApprovalPhaseRepository{
	private final String SELECT= "SELECT c FROM KrqdtAppApprovalPhase c";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtAppApprovalPhase c "
			+ " WHERE c.KrqdtAppApprovalPhasePK.companyID = :companyID "
			+ " AND c.KrqdtAppApprovalPhasePK.appID = :appID "
			+ "AND c.KrqdtAppApprovalPhasePK.phaseID = :phaseID";
	//get List Phase by appID
	private final String SELECT_BY_APP_ID = "SELECT c FROM KrqdtAppApprovalPhase c"
			+ " WHERE c.KrqdtAppApprovalPhasePK.companyID = :companyID"
			+ " AND c.KrqdtAppApprovalPhasePK.appID = :appID";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.KrqdtAppApprovalPhasePK.companyID = :companyID";
	@Override
	public Optional<AppApprovalPhase> findByCode(String companyID, String appID, String phaseID) {
		return this.queryProxy()
				.query(SELECT_SINGLE, KrqdtAppApprovalPhase.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.setParameter("phaseID", phaseID)
				.getSingle(c -> toDomain(c));
	}
	


	@Override
	public void create(AppApprovalPhase appApprovalPhase) {
		this.commandProxy().insert(toEntity(appApprovalPhase));
		
	}
	@Override
	public void update(AppApprovalPhase appApprovalPhase) {
		KrqdtAppApprovalPhase newEntity = toEntity(appApprovalPhase);
		KrqdtAppApprovalPhase updateEntity = this.queryProxy().find(newEntity.krqdtAppApprovalPhasePK, KrqdtAppApprovalPhase.class).get();
		updateEntity.approvalForm  = newEntity.approvalForm;
		updateEntity.dispOrder = newEntity.dispOrder;
		updateEntity.approvalATR = newEntity.approvalATR;
		this.commandProxy().update(updateEntity);
		
	}
	@Override
	public void delete(String companyID, String appID, String phaseID) {
		this.commandProxy().remove(KrqdtAppLateOrLeave.class, new KrqdtAppLateOrLeavePK(companyID, appID));
		this.getEntityManager().flush();
		
	}
	private AppApprovalPhase toDomain(KrqdtAppApprovalPhase entity) {
		return AppApprovalPhase.createFromJavaType(
				entity.krqdtAppApprovalPhasePK.companyID,
				entity.krqdtAppApprovalPhasePK.appID,
				entity.krqdtAppApprovalPhasePK.phaseID,
				Integer.valueOf(entity.approvalForm).intValue(),
				Integer.valueOf(entity.dispOrder).intValue(),
				Integer.valueOf(entity.approvalATR).intValue());
	}
	private KrqdtAppApprovalPhase toEntity (AppApprovalPhase domain){
		return new KrqdtAppApprovalPhase (
					new KrqdtAppApprovalPhasePK(domain.getCompanyID(), domain.getAppID(), domain.getPhaseID()),
					domain.getApprovalForm().toString(),
					domain.getDispOrder(),
					domain.getApprovalATR().toString()
					);
	}


	//get List Phase by AppID
	
	public List<AppApprovalPhase> findPhaseByAppID(String companyID, String appID) {
		return this.queryProxy()
				.query(SELECT_BY_APP_ID, KrqdtAppApprovalPhase.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getList(c -> toDomain(c));
	}




}
