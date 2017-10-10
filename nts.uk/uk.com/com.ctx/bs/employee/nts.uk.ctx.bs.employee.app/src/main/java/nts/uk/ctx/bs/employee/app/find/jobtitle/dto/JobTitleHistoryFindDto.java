package nts.uk.ctx.bs.employee.app.find.jobtitle.dto;

import lombok.Data;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.HistoryId;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.JobTitleHistorySetMemento;
import nts.uk.ctx.bs.employee.dom.jobtitle.history.Period;

/**
 * Instantiates a new job title history find dto.
 */
@Data
public class JobTitleHistoryFindDto implements JobTitleHistorySetMemento {
	
	/** The history id. */
    public String historyId;

    /** The period. */
    public Period period;
    
	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	@Override
	public void setHistoryId(HistoryId historyId) {
		this.historyId = historyId.v();
	}

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	@Override
	public void setPeriod(Period period) {
		this.period = period;
	}
}
