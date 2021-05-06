package nts.uk.ctx.at.aggregation.app.command.schedulecounter.initcompanyinfo;

import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountByNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountValue;

public class CriterionAmountByNoCommand {
	
	private Integer frameNo;
	
	private Integer amount;
	
	public CriterionAmountByNo toDomain() {

		return new CriterionAmountByNo(new CriterionAmountNo(frameNo), new CriterionAmountValue(amount));
	}
}
