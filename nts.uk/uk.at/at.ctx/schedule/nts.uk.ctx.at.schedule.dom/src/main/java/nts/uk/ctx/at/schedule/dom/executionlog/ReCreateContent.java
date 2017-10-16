/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ReCreateContent.
 */
// 再作成内容
@Getter
public class ReCreateContent extends DomainObject{
	
	/** The re create atr. */
	// 再作成区分
	private ReCreateAtr reCreateAtr;

	/** The process execution atr. */
	// 処理実行区分
	private ProcessExecutionAtr processExecutionAtr;
	
	/** The reset atr. */
	// 再設定区分
	private ResetAtr resetAtr;

	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the re create content
	 */
	public ReCreateContent(ScheduleCreateContentGetMemento memento) {
		this.reCreateAtr = memento.getReCreateAtr();
		this.processExecutionAtr = memento.getProcessExecutionAtr();
		this.resetAtr = new ResetAtr(memento);
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento) {
		memento.setReCreateAtr(this.reCreateAtr);
		memento.setProcessExecutionAtr(this.processExecutionAtr);
		this.resetAtr.saveToMemento(memento);
	}

}
