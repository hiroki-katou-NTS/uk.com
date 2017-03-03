package nts.uk.ctx.pr.formula.app.find.formulahistory;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaHistory;

@Value
public class FormulaHistoryDto {
	
	String companyCode;
	
	String formulaCode;
	
	String historyId;
	
	int startDate;
	
	int endDate;
	
	public static FormulaHistoryDto fromDomain(FormulaHistory domain){
		return new FormulaHistoryDto(domain.getCompanyCode(), domain.getFormulaCode().v(), domain.getHistoryId() 
				, domain.getStartDate().v(), domain.getEndDate().v());
	}

}
