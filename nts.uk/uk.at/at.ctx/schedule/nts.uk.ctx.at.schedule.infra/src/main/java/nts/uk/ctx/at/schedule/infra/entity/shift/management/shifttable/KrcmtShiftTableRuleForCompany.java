package nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 会社のシフト表のルール entity
 * @author tutk
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SHIFTTBL_RULE_CMP")
public class KrcmtShiftTableRuleForCompany extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID
	 */
	@Id
	@Column(name = "CID")
	public String companyId;
	
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
	
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "krcmtShiftTableRuleForCompany", orphanRemoval = true)
	public KrcmtShiftTableRuleForCompanyAvai krcmtShiftTableRuleForCompanyAvai;

	@Override
	protected Object getKey() {
		return this.companyId;
	}

	public KrcmtShiftTableRuleForCompany(String companyId, int usePublicAtr, int useWorkAvailabilityAtr,
			KrcmtShiftTableRuleForCompanyAvai krcmtShiftTableRuleForCompanyAvai) {
		super();
		this.companyId = companyId;
		this.usePublicAtr = usePublicAtr;
		this.useWorkAvailabilityAtr = useWorkAvailabilityAtr;
		this.krcmtShiftTableRuleForCompanyAvai = krcmtShiftTableRuleForCompanyAvai;
	}

	public static KrcmtShiftTableRuleForCompany toEntity(String companyId,ShiftTableRuleForCompany domain ) {
		return new KrcmtShiftTableRuleForCompany(
				companyId,
				domain.getShiftTableRule().getUsePublicAtr().value,
				domain.getShiftTableRule().getUseWorkAvailabilityAtr().value,
				KrcmtShiftTableRuleForCompanyAvai.toEntity(companyId, domain.getShiftTableRule())
				);
	}
	
	public ShiftTableRuleForCompany toDomain() {
		return new ShiftTableRuleForCompany(krcmtShiftTableRuleForCompanyAvai.toDomain(this.usePublicAtr, this.useWorkAvailabilityAtr));
	}
}
