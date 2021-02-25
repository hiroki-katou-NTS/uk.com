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
public class ReCreateContent extends DomainObject {

	/** The re create atr. */
	// 再作成区分
	private ReCreateAtr reCreateAtr;

	/** The process execution atr. */
	// 処理実行区分
	private ProcessExecutionAtr processExecutionAtr;

	/** The reset atr. */
	// 再設定区分
	private ResetAtr resetAtr;

	// 再作成対象区分
	private RebuildTargetAtr rebuildTargetAtr;

	// 再作成対象者詳細区分
	private RebuildTargetDetailsAtr rebuildTargetDetailsAtr;

	/**
	 * To domain.
	 *
	 * @param memento
	 *            the memento
	 * @return the re create content
	 */
	public ReCreateContent(ScheduleCreateContentGetMemento memento) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		this.reCreateAtr = memento.getReCreateAtr();
//		this.processExecutionAtr = memento.getProcessExecutionAtr();
//		this.resetAtr = new ResetAtr(memento);
//		this.rebuildTargetAtr = memento.getRebuildTargetAtr();
//		this.rebuildTargetDetailsAtr = new RebuildTargetDetailsAtr(memento);
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		memento.setReCreateAtr(this.reCreateAtr);
//		memento.setProcessExecutionAtr(this.processExecutionAtr);
//		this.resetAtr.saveToMemento(memento);
//		memento.setRebuildTargetAtr(this.rebuildTargetAtr);
//		this.rebuildTargetDetailsAtr.saveToMemento(memento);
	}

	public void setReCreateAtr(ReCreateAtr reCreateAtr) {
		this.reCreateAtr = reCreateAtr;
	}

	public void setProcessExecutionAtr(ProcessExecutionAtr processExecutionAtr) {
		this.processExecutionAtr = processExecutionAtr;
	}

	public void setResetAtr(ResetAtr resetAtr) {
		this.resetAtr = resetAtr;
	}

	public void setRebuildTargetAtr(RebuildTargetAtr rebuildTargetAtr) {
		this.rebuildTargetAtr = rebuildTargetAtr;
	}

	public void setRebuildTargetDetailsAtr(RebuildTargetDetailsAtr rebuildTargetDetailsAtr) {
		this.rebuildTargetDetailsAtr = rebuildTargetDetailsAtr;
	}

	public ReCreateContent() {
	}
	
	
}
