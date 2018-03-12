package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.hdappset;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdapplicationsetting.KrqstTimeHdAppSet;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaTimeHdAppSetRepository extends JpaRepository implements TimeHdAppSetRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static TimeHdAppSet toDomain(KrqstTimeHdAppSet entity){
		TimeHdAppSet domain = TimeHdAppSet.createFromJavaType(entity.companyId, entity.checkDay, 
				entity.use60h, entity.useAttend2, entity.nameBefore2, entity.useBefore, entity.nameBefore, 
				entity.actualDisp, entity.checkOver, entity.useTimeHd, entity.useTimeYear, entity.usePrivate, 
				entity.privateName, entity.unionLeave, entity.unionName, entity.useAfter2, entity.nameAfter2, 
				entity.useAfter, entity.nameAfter);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstTimeHdAppSet toEntity(TimeHdAppSet domain){
		val entity = new KrqstTimeHdAppSet();
		entity.companyId = domain.getCompanyId();
		entity.actualDisp = domain.getActualDisp().value;
		entity.checkDay = domain.getCheckDay().value;
		entity.checkOver = domain.getCheckOver().value;
		entity.nameAfter = domain.getNameAfter() == null ? null : domain.getNameAfter().v();
		entity.nameAfter2 = domain.getNameAfter2() == null ? null : domain.getNameAfter2().v();
		entity.nameBefore = domain.getNameBefore() == null ? null : domain.getNameBefore().v();
		entity.nameBefore2 = domain.getNameBefore2() == null ? null : domain.getNameBefore2().v();
		entity.privateName = domain.getPrivateName() == null ? null : domain.getPrivateName().v();
		entity.unionLeave = domain.getUnionLeave().value;
		entity.unionName = domain.getUnionName() == null ? null : domain.getUnionName().v();
		entity.use60h = domain.getUse60h().value;
		entity.useAfter = domain.getUseAfter().value;
		entity.useAfter2 = domain.getUseAfter2().value;
		entity.useAttend2 = domain.getUseAttend2().value;
		entity.useBefore = domain.getUseBefore().value;
		entity.usePrivate = domain.getUsePrivate().value;
		entity.useTimeHd = domain.getUseTimeHd().value;
		entity.useTimeYear = domain.getUseTimeYear().value;
		return entity;
	}
	/**
	 * get time holiday app set by companyid
	 * @author yennth
	 */
	@Override
	public Optional<TimeHdAppSet> getByCid() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstTimeHdAppSet.class).map(x -> toDomain(x));
	}
	/**
	 * update time holiday app set
	 * @author yennth
	 */
	@Override
	public void update(TimeHdAppSet timeHd) {
		KrqstTimeHdAppSet entity = toEntity(timeHd);
		KrqstTimeHdAppSet oldEntity = this.queryProxy().find(entity.companyId, KrqstTimeHdAppSet.class).get();
		oldEntity.actualDisp = entity.actualDisp;
		oldEntity.checkDay = entity.checkDay;
		oldEntity.checkOver = entity.checkOver;
		oldEntity.nameAfter = entity.nameAfter;
		oldEntity.nameAfter2 = entity.nameAfter2;
		oldEntity.nameBefore = entity.nameBefore;
		oldEntity.nameBefore2 = entity.nameBefore2;
		oldEntity.privateName = entity.privateName;
		oldEntity.unionLeave = entity.unionLeave;
		oldEntity.unionName = entity.unionName;
		oldEntity.use60h = entity.use60h;
		oldEntity.useAfter = entity.useAfter;
		oldEntity.useAfter2 = entity.useAfter2;
		oldEntity.useAttend2 = entity.useAttend2;
		oldEntity.useBefore = entity.useBefore;
		oldEntity.usePrivate = entity.usePrivate;
		oldEntity.useTimeHd = entity.useTimeHd;
		oldEntity.useTimeYear = entity.useTimeYear;
		this.commandProxy().update(oldEntity);
	}
	
	/**
	 * insert time holiday app set 
	 * @author yennth
	 */
	@Override
	public void insert(TimeHdAppSet timeHd) {
		KrqstTimeHdAppSet entity = toEntity(timeHd);
		this.commandProxy().insert(entity);
	}
	
}
