package nts.uk.ctx.at.schedule.dom.shift.total.times;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;

/**
 * Gets the work type atr.
 *
 * @return the work type atr
 */
@Getter
public class TotalSubjects extends DomainObject{

	/** The work type code. */
	//勤務種類コード
	private WorkTypeCode workTypeCode;
	
	/** The sift code. */
	//就業時間帯コード
	private Integer workTypeAtr;


	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TotalSubjectsSetMemento memento) {
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkTypeAtr(this.workTypeAtr);
	}

	/**
	 * Instantiates a new total subjects.
	 *
	 * @param workTypeCode the work type code
	 * @param workTypeAtr the work type atr
	 */
	public TotalSubjects(WorkTypeCode workTypeCode, Integer workTypeAtr) {
		this.workTypeCode = workTypeCode;
		this.workTypeAtr = workTypeAtr;
	}

}
