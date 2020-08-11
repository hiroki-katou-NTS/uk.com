package nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author anhdt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KSHMT_SHIFT_MASTER_ORG")
public class KshmtShiftMaterOrg extends ContractUkJpaEntity {

	@EmbeddedId
	public KshmtShiftMaterOrgPK kshmtShiftMaterOrgPK;

	@Override
	protected Object getKey() {
		return kshmtShiftMaterOrgPK;
	}

	public ShiftMasterOrganization toDomain() {
		KshmtShiftMaterOrgPK key = this.kshmtShiftMaterOrgPK;
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(key.targetUnit);
		TargetOrgIdenInfor targetinfo = new TargetOrgIdenInfor(unit,unit.value ==0? Optional.of(key.targetId):Optional.empty(), unit.value ==0?Optional.empty(): Optional.of(key.targetId));
		return new ShiftMasterOrganization(key.companyId, targetinfo, Arrays.asList(key.shiftMaterCode));
	}
	
	public ShiftMasterOrganization toDomain(List<String> shiftMasterCodes) {
		KshmtShiftMaterOrgPK key = this.kshmtShiftMaterOrgPK;
		TargetOrganizationUnit unit = TargetOrganizationUnit.valueOf(key.targetUnit);
		TargetOrgIdenInfor targetinfo = new TargetOrgIdenInfor(unit,unit.value ==0? Optional.of(key.targetId):Optional.empty(), unit.value ==0?Optional.empty(): Optional.of(key.targetId));
		return new ShiftMasterOrganization(key.companyId, targetinfo, shiftMasterCodes);
	}

	public static KshmtShiftMaterOrg toEntity(ShiftMasterOrganization domain, String shiftMasterCode) {
		
		KshmtShiftMaterOrgPK key = new KshmtShiftMaterOrgPK();
		key.setCompanyId(domain.getCompanyId());
		key.setShiftMaterCode(shiftMasterCode);
		
		TargetOrgIdenInfor target =  domain.getTargetOrg();	
		Integer unit = target.getUnit().value;
		key.setTargetUnit(unit);
		
		if(unit == TargetOrganizationUnit.WORKPLACE.value) {
			key.setTargetId(target.getWorkplaceId().get());
		} else {
			key.setTargetId(target.getWorkplaceGroupId().get());
		}
		
		return new KshmtShiftMaterOrg(key);	
	}

	public KshmtShiftMaterOrg(KshmtShiftMaterOrgPK key) {
		super();
		this.kshmtShiftMaterOrgPK = key;
	}

	
}
