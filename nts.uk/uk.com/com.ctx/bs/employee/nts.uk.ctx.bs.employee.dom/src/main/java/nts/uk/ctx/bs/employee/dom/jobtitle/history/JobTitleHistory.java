package nts.uk.ctx.bs.employee.dom.jobtitle.history;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

@Getter
//職場履歴
public class JobTitleHistory extends DomainObject {
	
	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The period. */
	//期間
	private Period period;

	/**
	 * Instantiates a new job title history.
	 *
	 * @param memento the memento
	 */
	public JobTitleHistory(JobTitleHistoryGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.period = memento.getPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(JobTitleHistorySetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setPeriod(this.period);
	}
}
