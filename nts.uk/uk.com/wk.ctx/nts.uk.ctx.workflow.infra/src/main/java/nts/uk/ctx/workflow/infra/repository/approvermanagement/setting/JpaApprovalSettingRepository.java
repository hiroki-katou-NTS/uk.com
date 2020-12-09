package nts.uk.ctx.workflow.infra.repository.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.PrincipalApprovalFlg;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.WwfmtApprovalSetting;

@Stateless
public class JpaApprovalSettingRepository extends JpaRepository implements ApprovalSettingRepository {
	private static final String SQL_FIND = "SELECT c FROM WwfmtApprovalSetting c WHERE c.companyId = :companyId";

	@Override
	public Optional<PrincipalApprovalFlg> getPrincipalByCompanyId(String companyId) {
		return this.queryProxy().query(SQL_FIND, WwfmtApprovalSetting.class)
				.setParameter("companyId", companyId)
				.getSingle(c-> EnumAdaptor.valueOf(c.selfApprovalAtr, PrincipalApprovalFlg.class));
	}
	
	private WwfmtApprovalSetting toEntity(ApprovalSetting domain){
		val entity = new WwfmtApprovalSetting();
		entity.companyId = domain.getCompanyId();
		entity.selfApprovalAtr = BooleanUtils.toInteger(domain.getPrinFlg());
		ApproverRegisterSet approverRegsterSet = domain.getApproverRegsterSet();
		if (approverRegsterSet != null) {
			entity.cmpUnitSet = approverRegsterSet.getCompanyUnit().value;
			entity.wkpUnitSet = approverRegsterSet.getWorkplaceUnit().value;
			entity.syaUnitSet = approverRegsterSet.getEmployeeUnit().value;			
		}
		return entity;
	}
	/**
	 * update approvalSetting
	 * @author yennth
	 */
	@Override
	public void update(ApprovalSetting appro) {
		WwfmtApprovalSetting entity = toEntity(appro);
		WwfmtApprovalSetting oldEntity = this.queryProxy().find(entity.companyId, WwfmtApprovalSetting.class).get();
		oldEntity.principalApprovalFlg = entity.principalApprovalFlg;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private ApprovalSetting toDomainApproval(WwfmtApprovalSetting entity){
		ApprovalSetting domain = ApprovalSetting.createFromJavaType(entity.companyId, entity.principalApprovalFlg);
		return domain;
	}
	
	/**
	 * get approval setting by companyId
	 * @author yennth
	 */
	@Override
	public Optional<ApprovalSetting> getApprovalByComId(String companyId) {
		return this.queryProxy().find(companyId, WwfmtApprovalSetting.class).map(x -> toDomainApproval(x));
	}

	/**
	 * insert approval setting
	 * @author yennth
	 */
	@Override
	public void insert(ApprovalSetting appro) {
		WwfmtApprovalSetting entity = toEntity(appro);
		this.commandProxy().insert(entity);
	}

	@Override
	public void updateForUnit(ApprovalSetting appro) {
		WwfmtApprovalSetting entity = toEntity(appro);
		WwfmtApprovalSetting entityUpdate = this.queryProxy().find(entity.companyId, WwfmtApprovalSetting.class).get();
		entityUpdate.cmpUnitSet = entity.cmpUnitSet;
		entityUpdate.wkpUnitSet = entity.wkpUnitSet;
		entityUpdate.syaUnitSet = entity.syaUnitSet;
		this.commandProxy().update(entityUpdate);
		
	}
	
}
