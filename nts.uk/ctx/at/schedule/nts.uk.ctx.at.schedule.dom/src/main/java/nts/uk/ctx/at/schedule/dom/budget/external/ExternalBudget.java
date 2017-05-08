package nts.uk.ctx.at.schedule.dom.budget.external;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

public class ExternalBudget extends AggregateRoot {

	@Getter
	private final String companyId;

	@Getter
	private final ExternalBudgetCd externalBudgetCd;

	@Getter
	private final ExternalBudgetName externalBudgetName;

	@Getter
	private BudgetAtr budgetAtr;

	@Getter
	private UnitAtr unitAtr;

	public ExternalBudget(String companyId, ExternalBudgetCd externalBudgetCd, ExternalBudgetName externalBudgetName,
			BudgetAtr budgetAtr, UnitAtr unitAtr) {
		super();
		this.companyId = companyId;
		this.externalBudgetCd = externalBudgetCd;
		this.externalBudgetName = externalBudgetName;
		this.budgetAtr = budgetAtr;
		this.unitAtr = unitAtr;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param companyId
	 *            company ID
	 * @param externalBudgetCd
	 *            externalBudgetCd
	 * @return ExternalBudget
	 */
	public static ExternalBudget createFromJavaType(String companyId, String externalBudgetCd,
			String externalBudgetName, int budgetAtr, int unitAtr) {
		return new ExternalBudget(companyId, new ExternalBudgetCd(externalBudgetCd),
				new ExternalBudgetName(externalBudgetName), 
				EnumAdaptor.valueOf(budgetAtr, BudgetAtr.class),
				EnumAdaptor.valueOf(unitAtr, UnitAtr.class));
	}

}
