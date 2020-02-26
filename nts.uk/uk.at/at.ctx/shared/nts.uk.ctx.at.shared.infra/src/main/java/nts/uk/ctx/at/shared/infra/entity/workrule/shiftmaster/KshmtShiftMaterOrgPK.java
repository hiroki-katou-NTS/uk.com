package nts.uk.ctx.at.shared.infra.entity.workrule.shiftmaster;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * @author anhdt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@Data
public class KshmtShiftMaterOrgPK {

	public KshmtShiftMaterOrgPK(String companyId, String shiftMaterCode, TargetOrgIdenInfor targetOrg) {
		this.companyId = companyId;
		this.shiftMaterCode = shiftMaterCode;
		Integer unit = targetOrg.getUnit().value;
		this.setTargetUnit(unit);
		if(unit == TargetOrganizationUnit.WORKPLACE.value) {
			this.setTargetId(targetOrg.getWorkplaceId().get());
		} else {
			this.setTargetId(targetOrg.getWorkplaceGroupId().get());
		}
	}

	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "SHIFT_MASTER_CD")
	public String shiftMaterCode;
	
	@Column(name = "TARGET_UNIT")
	public Integer targetUnit;
	
	@Column(name = "TARGET_ID")
	public String targetId;
	
}
