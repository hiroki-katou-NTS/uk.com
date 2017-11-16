/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.classification.affiliate;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class AffClassHistory.
 */
// 謇�螻槫�鬘槫ｱ･豁ｴ
@Getter
public class AffClassHistory extends AggregateRoot {

	/** The classification code. */
	// 蛻�鬘槭さ繝ｼ繝�
	private ClassificationCode classificationCode;

	/** The period. */
	// 譛滄俣
	private DatePeriod period;

	/** The employee id. */
	// 遉ｾ蜩｡ID
	private String employeeId;
	
	
	/**
	 * Instantiates a new classification history.
	 *
	 * @param memento the memento
	 */
	public AffClassHistory(AffClassHistoryGetMemento memento) {
		this.classificationCode = memento.getClassificationCode();
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AffClassHistorySetMemento memento){
		memento.setClassificationCode(this.classificationCode);
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
	}
}
