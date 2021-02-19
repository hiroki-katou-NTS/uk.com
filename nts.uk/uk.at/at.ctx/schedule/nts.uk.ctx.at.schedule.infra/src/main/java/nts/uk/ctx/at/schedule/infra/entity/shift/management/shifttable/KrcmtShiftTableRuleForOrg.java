package nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 組織別のシフト表のルール Entity
 * @author tutk
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SHIFTTBL_ROLE_ORG")
public class KrcmtShiftTableRuleForOrg extends ContractUkJpaEntity  implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscdtManagementOfShiftTablePk pk;

	/**
	 * 公開運用区分
	 */
	@Column(name = "USE_PUBLICATION_ATR")
	public int usePublicAtr;
	
	/**
	 * 勤務希望運用区分
	 */
	@Column(name = "USE_AVAILABILITY_ATR")
	public int useWorkAvailabilityAtr;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "krcmtShiftTableRuleForOrg", orphanRemoval = true)
	public KrcmtShiftTableRuleForOrgAvai krcmtShiftTableRuleForOrgAvai;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcmtShiftTableRuleForOrg(KscdtManagementOfShiftTablePk pk, int usePublicAtr, int useWorkAvailabilityAtr,
			KrcmtShiftTableRuleForOrgAvai krcmtShiftTableRuleForOrgAvai) {
		super();
		this.pk = pk;
		this.usePublicAtr = usePublicAtr;
		this.useWorkAvailabilityAtr = useWorkAvailabilityAtr;
		this.krcmtShiftTableRuleForOrgAvai = krcmtShiftTableRuleForOrgAvai;
	}
	
	public static KrcmtShiftTableRuleForOrg toEntity(String companyId,ShiftTableRuleForOrganization domain ) {
		return new KrcmtShiftTableRuleForOrg(
				new KscdtManagementOfShiftTablePk(companyId, domain.getTargetOrg().getUnit().value,domain.getTargetOrg().getTargetId()),
				domain.getShiftTableRule().getUsePublicAtr().value,
				domain.getShiftTableRule().getUseWorkAvailabilityAtr().value,
				KrcmtShiftTableRuleForOrgAvai.toEntity(companyId,domain.getTargetOrg().getUnit().value,domain.getTargetOrg().getTargetId(), domain.getShiftTableRule())
				);
	}
	
	public ShiftTableRuleForOrganization toDomain() {
		if(this.useWorkAvailabilityAtr == 0) {
			return  new ShiftTableRuleForOrganization(  TargetOrgIdenInfor.createFromTargetUnit(TargetOrganizationUnit.valueOf(this.pk.targetUnit), this.pk.targetID),new ShiftTableRule(
					NotUseAtr.valueOf(usePublicAtr), 
					NotUseAtr.valueOf(useWorkAvailabilityAtr),
					Optional.empty(), 
					new ArrayList<>(),
					Optional.empty()
					));
		}
		return new ShiftTableRuleForOrganization(  TargetOrgIdenInfor.createFromTargetUnit(TargetOrganizationUnit.valueOf(this.pk.targetUnit), this.pk.targetID),
				
				krcmtShiftTableRuleForOrgAvai.toDomain(this.usePublicAtr, this.useWorkAvailabilityAtr));
	}
}
