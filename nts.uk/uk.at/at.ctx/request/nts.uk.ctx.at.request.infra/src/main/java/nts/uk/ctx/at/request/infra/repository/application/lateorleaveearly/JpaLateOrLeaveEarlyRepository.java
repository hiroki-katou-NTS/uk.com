package nts.uk.ctx.at.request.infra.repository.application.lateorleaveearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.infra.entity.application.common.KrqdtApplication_New;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeave;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeavePK;

@Stateless
public class JpaLateOrLeaveEarlyRepository extends JpaRepository implements LateOrLeaveEarlyRepository {
	
	//private final String SELECT= "SELECT c FROM KrqdtAppLateOrLeave c";
	//private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.KrqdtAppLateOrLeavePK.companyID = :companyID";
	private final String SELECT_SINGLE = "SELECT c"
			+ " FROM KrqdtAppLateOrLeave c"
			+ " WHERE c.krqdtAppLateOrLeavePK.appID = :appID AND c.krqdtAppLateOrLeavePK.companyID = :companyID";
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
		updateEntity.kafdtApplication.appReason = newEntity.kafdtApplication.appReason;
		updateEntity.actualCancelAtr = newEntity.actualCancelAtr;
		updateEntity.early1 = newEntity.early1;
		updateEntity.earlyTime1 = newEntity.earlyTime1;
		updateEntity.late1 = newEntity.late1;
		updateEntity.lateTime1 = newEntity.lateTime1;
		updateEntity.early2 = newEntity.early2;
		updateEntity.earlyTime2 = newEntity.earlyTime2;
		updateEntity.late2 = newEntity.late2;
		updateEntity.lateTime2 = newEntity.lateTime2;
		updateEntity.version = newEntity.version;
		updateEntity.kafdtApplication.version = newEntity.version;
		this.commandProxy().update(updateEntity);
		this.commandProxy().update(updateEntity.kafdtApplication);
		
	}

	@Override
	public void remove(String companyID, String appID) {
		this.commandProxy().remove(KrqdtAppLateOrLeave.class, new KrqdtAppLateOrLeavePK(companyID, appID));
		this.getEntityManager().flush();
		
	}
	
	private LateOrLeaveEarly toDomain(KrqdtAppLateOrLeave entity) {
		KrqdtAppLateOrLeave appLateOrLeaveEntity = entity;
		KrqdtApplication_New applicationEntity = entity.kafdtApplication;
		
		LateOrLeaveEarly lateOrLeaveEarly = new LateOrLeaveEarly (
				appLateOrLeaveEntity.krqdtAppLateOrLeavePK.companyID, 
				appLateOrLeaveEntity.krqdtAppLateOrLeavePK.appID,
				 applicationEntity.prePostAtr,
				 applicationEntity.inputDate,
				 applicationEntity.enteredPersonID,
				 applicationEntity.reversionReason,
				 applicationEntity.appDate,
				 applicationEntity.appReason,
				 applicationEntity.appType,
				 applicationEntity.employeeID,
				 applicationEntity.notReason,
				 applicationEntity.dateTimeReflection,
				 applicationEntity.stateReflection,
				 applicationEntity.forcedReflection,
				 applicationEntity.notReasonReal,
				 applicationEntity.dateTimeReflectionReal,
				 applicationEntity.stateReflectionReal,
				 applicationEntity.forcedReflectionReal,
				 applicationEntity.startDate,
				 applicationEntity.endDate,
				 appLateOrLeaveEntity.early1,
				 appLateOrLeaveEntity.earlyTime1,
				 appLateOrLeaveEntity.late1,
				 appLateOrLeaveEntity.lateTime1,
				 appLateOrLeaveEntity.early2 ,
				 appLateOrLeaveEntity.earlyTime2,
			 	 appLateOrLeaveEntity.late2,
				 appLateOrLeaveEntity.lateTime2);
		lateOrLeaveEarly.setVersion(entity.version);
		return lateOrLeaveEarly;
	}
	
	private KrqdtAppLateOrLeave toEntity(LateOrLeaveEarly domain){
		return new KrqdtAppLateOrLeave (
					new KrqdtAppLateOrLeavePK(domain.getCompanyID(), domain.getAppID()),
					domain.getVersion(),
					domain.getActualCancelAtr(),
					domain.getEarly1().value,
					domain.getEarlyTime1().v(),
					domain.getLate1().value,
					domain.getLateTime1().v(),
					domain.getEarly2().value,
					domain.getEarlyTime2().v(),
					domain.getLate2().value,
					domain.getLateTime2().v(),
					KrqdtApplication_New.fromDomain(domain));
	}
	@Override
	public ApplicationReason findApplicationReason(String companyID, ApplicationType applicationType) {
		// TODO Auto-generated method stub
		return null;
	};
	

}
