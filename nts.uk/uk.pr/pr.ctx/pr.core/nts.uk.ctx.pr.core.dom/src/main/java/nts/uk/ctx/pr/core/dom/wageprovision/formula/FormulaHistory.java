package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 
 * @author HungTT - 計算式履歴
 *
 */

@Getter
public class FormulaHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * 計算式コード
	 */
	private FormulaCode formulaCode;

	/**
	 * 有効期間
	 */
	private List<YearMonthHistoryItem> history;

	@Override
	public List<YearMonthHistoryItem> items() {
		return history;
	}
}
