/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;

/**
 * The Class ClosureHistorry.
 */
// 締め変更履歴
@Getter
public class ClosureHistory extends DomainObject {
	
	/** The company id. */
	private CompanyId companyId;

	/** The closure id. */
	// 締めＩＤ
	private ClosureId closureId;

	/** The closure name. */
	// 名称: 締め名称
	private ClosureName closureName;

	/** The end year month. */
	// 終了年月: 年月
	@Setter
	private YearMonth endYearMonth;

	/** The closure date. */
	// 締め日: 日付
	private ClosureDate closureDate;

	/** The start year month. */
	// 開始年月: 年月
	private YearMonth startYearMonth;

	
	/**
	 * Instantiates a new closure history.
	 *
	 * @param memento the memento
	 */
	public ClosureHistory(ClosureHistoryGetMemento memento) {
		this.closureName = memento.getClosureName();
		this.closureId = memento.getClosureId();
		this.companyId = memento.getCompanyId();
		this.endYearMonth = memento.getEndDate();
		this.closureDate = memento.getClosureDate();
		this.startYearMonth = memento.getStartDate();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ClosureHistorySetMemento memento) {
		memento.setClosureName(this.closureName);
		memento.setClosureId(this.closureId);
		memento.setCompanyId(this.companyId);
		memento.setEndDate(this.endYearMonth);
		memento.setClosureDate(this.closureDate);
		memento.setStartDate(this.startYearMonth);
	}

	/**
	 * To closure date.
	 *
	 * @return the int
	 */
	public int toClosureDate() {
		if (this.getClosureDate().getLastDayOfMonth()) {
			return 0;
		}
		return this.getClosureDate().getClosureDay().v();
	}
}
