package nts.uk.ctx.at.request.infra.repository.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTemp;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTempRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.mailsetting.mailapplicationapproval.KrqmtApprovalTemp;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaApprovalTempRepository extends JpaRepository implements ApprovalTempRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private ApprovalTemp toDomain(KrqmtApprovalTemp entity){
		ApprovalTemp appTemp = ApprovalTemp.createFromJavaType(entity.companyId, entity.content);
		return appTemp;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private KrqmtApprovalTemp toEntity(ApprovalTemp domain){
		val entity = new KrqmtApprovalTemp();
		entity.companyId = domain.getCompanyId();
		entity.content = domain.getContent().v();
		return entity;
	}
	/**
	 * get approval template
	 * @author yennth
	 */
	@Override
	public Optional<ApprovalTemp> getAppTem() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqmtApprovalTemp.class).map(c -> toDomain(c));
	}
	/**
	 * update approval template
	 * @author yennth
	 */
	@Override
	public void update(ApprovalTemp appTemp) {
		KrqmtApprovalTemp entity = toEntity(appTemp);
		KrqmtApprovalTemp oldEntity = this.queryProxy().find(entity.companyId, KrqmtApprovalTemp.class).get();
		oldEntity.content = entity.content;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert approval template
	 * @author yennth
	 */
	@Override
	public void insert(ApprovalTemp appTemp) {
		KrqmtApprovalTemp entity = toEntity(appTemp);
		this.commandProxy().insert(entity);
	}
	
}
