/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class TotalSubjects.
 */
@Getter
public class TotalSubjects extends DomainObject {

	/** The work type code. */
	// 勤務種類コード
	private WorkTypeCode workTypeCode;

	/** The work type atr. */
	// 就業時間帯コード
	private WorkTypeAtr workTypeAtr;

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(TotalSubjectsSetMemento memento) {
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setWorkTypeAtr(this.workTypeAtr);
	}

	/**
	 * Instantiates a new total subjects.
	 *
	 * @param memento
	 *            the memento
	 */
	public TotalSubjects(TotalSubjectsGetMemento memento) {
		super();
		this.workTypeCode = memento.getWorkTypeCode();
		this.workTypeAtr = memento.getWorkTypeAtr();
	}

}
