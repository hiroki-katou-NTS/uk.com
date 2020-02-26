package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 賃金テーブル
 */
@Getter
public class WageTableHistory extends AggregateRoot
		implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 賃金テーブルコード
	 */
	private WageTableCode wageTableCode;

	/**
	 * 有効期間
	 */
	private List<YearMonthHistoryItem> validityPeriods;

	@Override
	public List<YearMonthHistoryItem> items() {
		return this.validityPeriods;
	}

	public WageTableHistory(String companyId, String wageTableCode, List<YearMonthHistoryItem> history) {
		this.cid = companyId;
		this.wageTableCode = new WageTableCode(wageTableCode);
		this.validityPeriods = history;
	}
	
	@Override
	public void exCorrectToRemove(YearMonthHistoryItem itemToBeRemoved) {
		this.validityPeriods.get(0)
				.changeSpan(new YearMonthPeriod(this.validityPeriods.get(0).start(), itemToBeRemoved.end()));
	}

}
