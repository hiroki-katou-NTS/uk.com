package nts.uk.ctx.pr.formula.dom.formula;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;

@Getter
public class FormulaHistory {

	private CompanyCode companyCode;

	private FormulaCode formulaCode;

	private HistoryId historyId;
	
	private YearMonth startDate;
	
	private YearMonth endDate;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param startDate
	 * @param endDate
	 */
	public FormulaHistory(CompanyCode companyCode, FormulaCode formulaCode, HistoryId historyId, YearMonth startDate,
			YearMonth endDate) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public FormulaHistory createFromJavaType(String companyCode, String formulaCode, String historyId, int startDate, int endDate){
		return new FormulaHistory(new CompanyCode(companyCode), new FormulaCode(formulaCode),
				new HistoryId(historyId), new YearMonth(startDate), new YearMonth(endDate));
	}
	
}
