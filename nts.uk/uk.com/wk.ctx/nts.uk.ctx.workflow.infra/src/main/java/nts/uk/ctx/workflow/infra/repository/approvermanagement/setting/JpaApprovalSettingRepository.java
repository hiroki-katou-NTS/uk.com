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
import nts.uk.ctx.workflow.infra.entity.approvermanagement.setting.WwfstApprovalSetting;

@Stateless
public class JpaApprovalSettingRepository extends JpaRepository implements ApprovalSettingRepository {
	private static final String SQL_FIND = "SELECT c FROM WwfstApprovalSetting c WHERE c.companyId = :companyId";

	@Override
	public Optional<PrincipalApprovalFlg> getPrincipalByCompanyId(String companyId) {
		return this.queryProxy().query(SQL_FIND, WwfstApprovalSetting.class)
				.setParameter("companyId", companyId)
				.getSingle(c-> EnumAdaptor.valueOf(c.selfApprovalAtr, PrincipalApprovalFlg.class));
	}
	
	private WwfstApprovalSetting toEntity(ApprovalSetting domain){
		val entity = new WwfstApprovalSetting();
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
		WwfstApprovalSetting entity = toEntity(appro);
		WwfstApprovalSetting oldEntity = this.queryProxy().find(entity.companyId, WwfstApprovalSetting.class).get();
		oldEntity.selfApprovalAtr = entity.selfApprovalAtr;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private ApprovalSetting toDomainApproval(WwfstApprovalSetting entity){
		ApproverRegisterSet approverRegsterSet = new ApproverRegisterSet(EnumAdaptor.valueOf(entity.cmpUnitSet, UseClassification.class),
				EnumAdaptor.valueOf(entity.wkpUnitSet, UseClassification.class),
				EnumAdaptor.valueOf(entity.syaUnitSet, UseClassification.class));
		ApprovalSetting domain = ApprovalSetting.createFromJavaType(entity.companyId, approverRegsterSet, BooleanUtils.toBoolean(entity.selfApprovalAtr));
		return domain;
	}
	
	/**
	 * get approval setting by companyId
	 * @author yennth
	 */
	@Override
	public Optional<ApprovalSetting> getApprovalByComId(String companyId) {
		return this.queryProxy().find(companyId, WwfstApprovalSetting.class).map(x -> toDomainApproval(x));
	}

	/**
	 * insert approval setting
	 * @author yennth
	 */
	@Override
	public void insert(ApprovalSetting appro) {
		WwfstApprovalSetting entity = toEntity(appro);
		this.commandProxy().insert(entity);
	}

	@Override
	public void updateForUnit(ApprovalSetting appro) {
		WwfstApprovalSetting entity = toEntity(appro);
		WwfstApprovalSetting entityUpdate = this.queryProxy().find(entity.companyId, WwfstApprovalSetting.class).get();
		entityUpdate.cmpUnitSet = entity.cmpUnitSet;
		entityUpdate.wkpUnitSet = entity.wkpUnitSet;
		entityUpdate.syaUnitSet = entity.syaUnitSet;
		this.commandProxy().update(entityUpdate);
		
	}
	
}
