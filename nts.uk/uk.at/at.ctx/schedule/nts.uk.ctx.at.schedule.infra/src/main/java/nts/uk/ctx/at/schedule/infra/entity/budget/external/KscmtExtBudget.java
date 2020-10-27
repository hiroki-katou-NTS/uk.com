package nts.uk.ctx.at.schedule.infra.entity.budget.external;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_EXT_BUDGET")
public class KscmtExtBudget extends ContractUkJpaEntity{

	@EmbeddedId
	public KstscExternalBudgetPK kscmtExtBudgetPk;

	@Basic(optional = false)
	@Column(name = "EXTERNAL_BUDGET_NAME")
	@Getter
	@Setter
	public String externalBudgetName;

	@Basic(optional = false)
	@Column(name = "BUDGET_ATR")
	@Getter
	@Setter
	public int budgetAtr;

	@Basic(optional = false)
	@Column(name = "UNIT_ATR")
	@Getter
	@Setter
	public int unitAtr;

	@Override
	protected Object getKey() {
		return kscmtExtBudgetPk;
	}

}
