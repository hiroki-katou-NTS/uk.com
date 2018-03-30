package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdworkapplicationsetting.KrqstWithDrawalAppSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWithdrawalAppSetRepository extends JpaRepository implements WithdrawalAppSetRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private WithdrawalAppSet toDomain(KrqstWithDrawalAppSet entity){
		WithdrawalAppSet domain = WithdrawalAppSet.createFromJavaType(entity.companyId, entity.prePerflex, 
				entity.breakTime, entity.workTime, entity.checkHdTime, entity.typePaidLeave, entity.workChange, 
				entity.timeInit, entity.checkOut, entity.prefixLeave, entity.unitTime, entity.appSimul, 
				entity.bounSeg, entity.directDivi, entity.restTime, entity.overrideSet, entity.calStampMiss);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstWithDrawalAppSet toEntity(WithdrawalAppSet domain){
		val entity = new KrqstWithDrawalAppSet();
		entity.companyId = domain.getCompanyId();
		entity.appSimul = domain.getAppSimul();
		entity.bounSeg = domain.getBounSeg().value;
		entity.breakTime = domain.getBreakTime().value;
		entity.checkHdTime = domain.getCheckHdTime().value;
		entity.checkOut = domain.getCheckOut().value;
		entity.directDivi = domain.getDirectDivi().value;
		entity.prefixLeave = domain.getPrefixLeave().value;
		entity.prePerflex = domain.getPrePerflex().value;
		entity.restTime = domain.getRestTime().value;
		entity.timeInit = domain.getTimeInit().value;
		entity.typePaidLeave = domain.getTypePaidLeave().value;
		entity.unitTime = domain.getUnitTime().value;
		entity.workChange = domain.getWorkChange().value;
		entity.workTime = domain.getWorkTime().value;
		return entity;
	}
	/**
	 * get with drawal app set
	 * @author yennth
	 */
	@Override
	public Optional<WithdrawalAppSet> getWithDraw() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstWithDrawalAppSet.class).map(c -> toDomain(c));
	}
	/**
	 * update with drawal app set
	 * @author yennth
	 */
	@Override
	public void update(WithdrawalAppSet with) {
		KrqstWithDrawalAppSet entity = toEntity(with);
		KrqstWithDrawalAppSet oldEntity = this.queryProxy().find(entity.companyId, KrqstWithDrawalAppSet.class).get();
		oldEntity.appSimul = oldEntity.appSimul;
		oldEntity.bounSeg = entity.bounSeg;
		oldEntity.breakTime = entity.breakTime;
		oldEntity.checkHdTime = entity.checkHdTime;
		oldEntity.checkOut = entity.checkOut;
		oldEntity.directDivi = entity.directDivi;
		oldEntity.prefixLeave = entity.prefixLeave;
		oldEntity.prePerflex = oldEntity.prePerflex;
		oldEntity.restTime = entity.restTime;
		oldEntity.timeInit = entity.timeInit;
		oldEntity.typePaidLeave = entity.typePaidLeave;
		oldEntity.unitTime = oldEntity.unitTime;
		oldEntity.workChange = entity.workChange;
		oldEntity.workTime = entity.workTime;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert with drawal app set
	 * @author yennth
	 */
	@Override
	public void insert(WithdrawalAppSet with) {
		KrqstWithDrawalAppSet entity = toEntity(with);
		this.commandProxy().insert(entity);
	}

}
