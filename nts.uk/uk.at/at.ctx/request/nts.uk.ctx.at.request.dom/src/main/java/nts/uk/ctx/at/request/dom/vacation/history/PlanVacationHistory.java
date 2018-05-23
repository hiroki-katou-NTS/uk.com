/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.vacation.history;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.validate.Validatable;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class PlanVacationHistory.
 */

@Getter
//計画休暇のルールの履歴
public class PlanVacationHistory extends GeneralHistoryItem<PlanVacationHistory, DatePeriod, GeneralDate> implements Validatable {
	
	/** The company id. */
	private String companyId;
	
	/** The work type code. */
	private String workTypeCode;
	
	/** The max day. */
	private OptionalMaxDay maxDay;

	/**
	 * Instantiates a new plan vacation history.
	 *
	 * @param memento the memento
	 */
	public PlanVacationHistory(PlanVacationHistoryGetMemento memento) {
		super(memento.getHistoryId(), memento.getPeriod());
		this.companyId = memento.getCompanyId();
		this.maxDay = memento.getMaxDay();
		this.workTypeCode = memento.getWorkTypeCode();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PlanVacationHistorySetMemento memento) {
		memento.setHistoryId(this.identifier());
		memento.setCompanyId(this.companyId);
		memento.setMaxDay(this.maxDay.v());
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
	
	
	/**
	 * Instantiates a new plan vacation history.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param maxDay the max day
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public PlanVacationHistory(String companyId, String workTypeCode, OptionalMaxDay maxDay,
			GeneralDate startDate, GeneralDate endDate){
		super(IdentifierUtil.randomUniqueId(), new DatePeriod(startDate, endDate));
		this.companyId = companyId;
		this.maxDay = maxDay;
		this.workTypeCode = workTypeCode;
	}
	
	/**
	 * Instantiates a new plan vacation history.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param maxDay the max day
	 * @param historyId the history id
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public PlanVacationHistory(String companyId, String workTypeCode, OptionalMaxDay maxDay, String historyId,
			GeneralDate startDate, GeneralDate endDate){
		super(historyId, new DatePeriod(startDate, endDate));
		this.companyId = companyId;
		this.maxDay = maxDay;
		this.workTypeCode = workTypeCode;
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.validate.Validatable#validate()
	 */
	@Override
    public void validate() {
		// check conditional
		if (this.start().after(this.end())) {
			throw new BusinessException("Msg_917");
		}

		if (this.start().year() != this.end().year()) {
			throw new BusinessException("Msg_967");
		}
    }
}
	
