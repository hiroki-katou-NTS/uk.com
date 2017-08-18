package nts.uk.ctx.at.request.infra.repository.application.lateorleaveearly;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.infra.entity.application.common.lateorleaveearly.KrqdtAppLateOrLeave;
import nts.uk.ctx.at.request.infra.entity.application.common.lateorleaveearly.KrqdtAppLateOrLeavePK;


public class JpaLateOrLeaveEarlyRepository extends JpaRepository implements LateOrLeaveEarlyRepository {
	
	private final String SELECT= "SELECT c FROM KrqdtAppLateOrLeave c";
	private final String SELECT_SINGLE = "SELECT c FROM KrqdtAppLateOrLeave c WHERE c.KrqdtAppLateOrLeavePK.companyID = :companyID AND c.KrqdtAppLateOrLeavePK.appID = :appID";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.KrqdtAppLateOrLeavePK.companyID = :companyID";

	@Override
	public Optional<LateOrLeaveEarly> findByCode(String companyID, String appID) {
		return this.queryProxy()
				.query(SELECT_SINGLE, KrqdtAppLateOrLeave.class)
				.setParameter("companyID", companyID)
				.setParameter("appID", appID)
				.getSingle(c -> toDomain(c));
	}
	/**
	 * Add
	 * @param lateOrLeaveEarly
	 * @return  
	 */
	@Override
	public void add(LateOrLeaveEarly lateOrLeaveEarly) {
		this.commandProxy().insert(toEntity(lateOrLeaveEarly));
		
	}
	/**
	 * Update
	 * @param lateOrLeaveEarly
	 * @return 
	 */	
	@Override
	public void update(LateOrLeaveEarly lateOrLeaveEarly) {
		KrqdtAppLateOrLeave newEntity = toEntity(lateOrLeaveEarly);
		KrqdtAppLateOrLeave updateEntity = this.queryProxy().find(newEntity.krqdtAppLateOrLeavePK, KrqdtAppLateOrLeave.class).get();
		updateEntity.actualCancelAtr = newEntity.actualCancelAtr;
		updateEntity.early1 = newEntity.early1;
		updateEntity.earlyTime1 = newEntity.earlyTime1;
		updateEntity.late1 = newEntity.late1;
		updateEntity.lateTime1 = newEntity.lateTime1;
		updateEntity.early2 = newEntity.early2;
		updateEntity.earlyTime2 = newEntity.earlyTime2;
		updateEntity.late2 = newEntity.late2;
		updateEntity.lateTime2 = newEntity.early2;
		this.commandProxy().update(updateEntity);
		
	}

	@Override
	public void remove(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppLateOrLeave.class, new KrqdtAppLateOrLeavePK(companyID, appID));
		this.getEntityManager().flush();
		
	}
	
	private LateOrLeaveEarly toDomain(KrqdtAppLateOrLeave entity) {
		return LateOrLeaveEarly.createFromJavaType(
				entity.krqdtAppLateOrLeavePK.companyID,
				entity.krqdtAppLateOrLeavePK.appID,
				Integer.valueOf(entity.actualCancelAtr).intValue(),
				Integer.valueOf(entity.early1).intValue(),
				entity.earlyTime1,
				Integer.valueOf(entity.late1).intValue(),
				entity.lateTime1,
				Integer.valueOf(entity.early2).intValue(),
				entity.earlyTime2,
				Integer.valueOf(entity.late2).intValue(),
				entity.lateTime2);
	}
	private KrqdtAppLateOrLeave toEntity (LateOrLeaveEarly domain){
		return new KrqdtAppLateOrLeave (
					new KrqdtAppLateOrLeavePK(domain.getCompanyID(), domain.getAppID()),
					domain.getActualCancelAtr().toString(),
					domain.getEarly1().toString(),
					domain.getEarlyTime1().toString(),
					domain.getLate1().toString(),
					domain.getLateTime1().toString(),
					domain.getEarly2().toString(),
					domain.getEarlyTime2().toString(),
					domain.getLate2().toString(),
					domain.getLateTime2().toString());
	};
	

}
