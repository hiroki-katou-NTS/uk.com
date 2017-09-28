package nts.uk.ctx.at.request.infra.repository.application.common.appapprovalphase;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhasePK;



@Stateless
public class JpaAppApprovalPhaseRepository extends JpaRepository implements AppApprovalPhaseRepository{
	private final String SELECT= "SELECT c FROM KrqdtAppApprovalPhase c";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtAppApprovalPhase c "
			+ " WHERE c.KrqdtAppApprovalPhasePK.companyID = :companyID "
			+ " AND c.appID = :appID "
			+ "AND c.krqdtAppApprovalPhasePK.phaseID = :phaseID";
	//get List Phase by appID
	private final String SELECT_BY_APP_ID = "SELECT c FROM KrqdtAppApprovalPhase c"
			+ " WHERE c.krqdtAppApprovalPhasePK.companyID = :companyID"
			+ " AND c.appID = :appID";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.krqdtAppApprovalPhasePK.companyID = :companyID";
	
	
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
	public void delete(AppApprovalPhase appAprovalPhase) {
		this.commandProxy().remove(KrqdtAppApprovalPhase.class, new KrqdtAppApprovalPhasePK(appAprovalPhase.getCompanyID(), appAprovalPhase.getPhaseID()));
		this.getEntityManager().flush();
		
	}
	private AppApprovalPhase toDomain(KrqdtAppApprovalPhase entity) {
		return AppApprovalPhase.createFromJavaType(
				entity.krqdtAppApprovalPhasePK.companyID,
				entity.appID,
				entity.krqdtAppApprovalPhasePK.phaseID,
				Integer.valueOf(entity.approvalForm).intValue(),
				Integer.valueOf(entity.dispOrder).intValue(),
				Integer.valueOf(entity.approvalATR).intValue(),
				null);
	}
	private KrqdtAppApprovalPhase toEntity (AppApprovalPhase domain){
		return new KrqdtAppApprovalPhase (
					new KrqdtAppApprovalPhasePK(domain.getCompanyID(), domain.getPhaseID()),
					domain.getAppID(),
					domain.getApprovalForm().value,
					domain.getDispOrder(),
					domain.getApprovalATR().value
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
