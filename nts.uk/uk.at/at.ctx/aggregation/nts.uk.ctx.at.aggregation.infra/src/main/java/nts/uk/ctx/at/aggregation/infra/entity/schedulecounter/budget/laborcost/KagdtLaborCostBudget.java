package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.budget.laborcost;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetAmount;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author TU-TK
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_CMP")
public class KagdtLaborCostBudget extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KagdtLaborCostBudgetPK pk;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String cid;

	/**
	 * 人件費予算
	 */
	@Column(name = "BUDGET")
	public int budget;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KagdtLaborCostBudget toEntity(String cid, LaborCostBudget domain) {
		return new KagdtLaborCostBudget(new KagdtLaborCostBudgetPK(domain.getTargetOrg().getUnit().value,
				domain.getTargetOrg().getTargetId(), domain.getYmd()), cid, domain.getAmount().v());
	}

	public LaborCostBudget toDomain() {
		return new LaborCostBudget(TargetOrgIdenInfor
				.createFromTargetUnit(TargetOrganizationUnit.valueOf(this.pk.targetUnit), this.pk.targetId),
				this.pk.ymd, new LaborCostBudgetAmount(this.budget));
	}
}
