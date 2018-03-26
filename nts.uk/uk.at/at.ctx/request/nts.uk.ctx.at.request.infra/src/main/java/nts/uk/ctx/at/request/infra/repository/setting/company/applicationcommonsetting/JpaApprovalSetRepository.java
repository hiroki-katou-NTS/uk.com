package nts.uk.ctx.at.request.infra.repository.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationcommonsetting.KrqstApprovalSet;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaApprovalSetRepository extends JpaRepository implements ApprovalSetRepository{
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstApprovalSet toEntity(ApprovalSet domain){
		val entity = new KrqstApprovalSet();
		entity.companyId = domain.getCompanyId();
		entity.achiveCon = domain.getAchiveCon().value;
		entity.hdPerform = domain.getHdPerform().value;
		entity.hdPre = domain.getHdPre().value;
		entity.msgAdvance = domain.getMsgAdvance().value;
		entity.msgExceeded = domain.getMsgExceeded().value;
		entity.overtimePerfom = domain.getOvertimePerfom().value;
		entity.overtimePre = domain.getOvertimePre().value;
		entity.reasonDisp = domain.getReasonDisp().value;
		entity.scheduleCon = domain.getScheduleCon().value;
		entity.warnDateDisp = domain.getWarnDateDisp().v();
		return entity;
	}
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static ApprovalSet toDomain(KrqstApprovalSet entity){
		ApprovalSet domain = ApprovalSet.createSimpleFromJavaType(entity.companyId, entity.reasonDisp, 
				entity.overtimePre, entity.hdPre, entity.msgAdvance, entity.overtimePerfom, entity.hdPerform, 
				entity.msgExceeded, entity.warnDateDisp, entity.scheduleCon, entity.achiveCon);
		return domain;
	}
	/**
	 * get approval set by company id
	 * @author yennth
	 */
	@Override
	public Optional<ApprovalSet> getApproval() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstApprovalSet.class).map(x -> toDomain(x));
	}
	/**
	 * update approval set
	 * @author yennth
	 */
	@Override
	public void update(ApprovalSet appro) {
		KrqstApprovalSet entity = toEntity(appro);
		KrqstApprovalSet oldEntity = this.queryProxy().find(entity.companyId, KrqstApprovalSet.class).get();
		oldEntity.achiveCon = entity.achiveCon;
		oldEntity.hdPerform = entity.hdPerform;
		oldEntity.hdPre = entity.hdPre;
		oldEntity.msgAdvance = entity.msgAdvance;
		oldEntity.msgExceeded = entity.msgExceeded;
		oldEntity.overtimePerfom = entity.overtimePerfom;
		oldEntity.overtimePre = entity.overtimePre;
		oldEntity.reasonDisp = entity.reasonDisp;
		oldEntity.scheduleCon = entity.scheduleCon;
		oldEntity.warnDateDisp = entity.warnDateDisp;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert approval set
	 * @author yennth
	 */
	@Override
	public void insert(ApprovalSet appro) {
		KrqstApprovalSet entity = toEntity(appro);
		this.commandProxy().insert(entity);
	}
}
