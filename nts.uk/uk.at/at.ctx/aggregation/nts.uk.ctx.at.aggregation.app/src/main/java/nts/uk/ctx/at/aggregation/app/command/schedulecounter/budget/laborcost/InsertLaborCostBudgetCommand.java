package nts.uk.ctx.at.aggregation.app.command.schedulecounter.budget.laborcost;

import java.util.Map;
import java.util.Optional;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudget;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost.LaborCostBudgetAmount;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 
 * @author HieuLt
 *
 */
@Data
public class InsertLaborCostBudgetCommand {

	private int unit;
	private String targetID;
	private GeneralDate ymd;
	private int amount;
	//Map<年月日, 値>: Map
	private Map<GeneralDate, String> lstmap;

	public LaborCostBudget toDomain() {
		if (unit == TargetOrganizationUnit.WORKPLACE.value) {
			return new LaborCostBudget(
					new TargetOrgIdenInfor(EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
							Optional.ofNullable(targetID), Optional.ofNullable(null)),
					ymd, EnumAdaptor.valueOf(amount, LaborCostBudgetAmount.class));
		} else {
			return new LaborCostBudget(
					new TargetOrgIdenInfor(EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class),
							Optional.ofNullable(null), Optional.ofNullable(targetID)),
					ymd, EnumAdaptor.valueOf(amount, LaborCostBudgetAmount.class));
		}

	}
}
