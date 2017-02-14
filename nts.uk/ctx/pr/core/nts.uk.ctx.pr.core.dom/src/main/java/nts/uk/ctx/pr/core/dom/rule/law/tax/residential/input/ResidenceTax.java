package nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input;

import lombok.Getter;

@Getter
public class ResidenceTax {
	private int month;
	private MonthlyResidenceTax monthlyResidenceTax;
	public ResidenceTax(int month, MonthlyResidenceTax monthlyResidenceTax) {
		super();
		this.month = month;
		this.monthlyResidenceTax = monthlyResidenceTax;
	}
	

}
