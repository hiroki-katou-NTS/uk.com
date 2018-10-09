package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
 * 
 * @author HungTT - 計算式履歴
 *
 */

@Getter
public class FormulaHistory extends AggregateRoot {
	
	private String companyId;
	private FormulaCode formulaCode;
	private YearMonthHistoryItem effectivePeriod;

}
