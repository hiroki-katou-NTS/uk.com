/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.settting.worktype.history;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
//計画休暇のルールの履歴
public class PlanVacationHistory extends GeneralHistoryItem<PlanVacationHistory, DatePeriod, GeneralDate> {
	
	/** The company id. */
	private String companyId;
	
	/** The work type code. */
	private String workTypeCode;
	
	/** The max day. */
	private Integer maxDay;

	/**
	 * Instantiates a new plan vacation history.
	 *
	 * @param memento the memento
	 */
	public PlanVacationHistory(PlanVacationHistoryGetMemento memento) {
		super(memento.getHistoryId(), memento.getPeriod());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PlanVacationHistorySetMemento memento) {
		memento.setHistoryId(this.identifier());
		memento.setCompanyId(this.companyId);
		memento.setMaxDay(this.maxDay);
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setPeriod(this.span());
	}
	
	/**
	 * Update start date.
	 *
	 * @param newStartDate the new start date
	 */
	public void updateStartDate(GeneralDate newStartDate) {
		this.changeSpan(this.newSpan(newStartDate, this.span().end()));
	}
	
	/**
	 * Update end date.
	 *
	 * @param newEndDate the new end date
	 */
	public void updateEndDate(GeneralDate newEndDate) {
		this.changeSpan(this.newSpan(this.span().start(), newEndDate));
	}
}
