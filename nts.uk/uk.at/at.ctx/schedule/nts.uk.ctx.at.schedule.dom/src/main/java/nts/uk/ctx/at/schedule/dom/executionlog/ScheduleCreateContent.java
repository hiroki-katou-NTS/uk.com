/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;


/**
 * The Class ScheduleCreateContent.
 */
// スケジュール作成内容
@Getter
public class ScheduleCreateContent extends DomainObject{

	/** The confirm. */
	// 作成時に確定済みにする
	private Boolean confirm;
	
	/** The implement atr. */
	// 実施区分
	private ImplementAtr implementAtr;
	
	/** The execution id. */
	// 実行ID
	private String executionId;
	
	/** The copy start date. */
	// コピー開始日
	private GeneralDate copyStartDate;
	
	// 作成方法区分
	private CreateMethodAtr createMethodAtr;

	/** The content. */
	// 再作成内容
	private ReCreateContent reCreateContent;
	
	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the execution content
	 */
	public ScheduleCreateContent (ScheduleCreateContentGetMemento memento){
		this.confirm = memento.getConfirm();
		this.implementAtr = memento.getImplementAtr();
		this.executionId = memento.getExecutionId();
		this.copyStartDate = memento.getCopyStartDate();
		this.createMethodAtr = memento.getCreateMethodAtr();
		this.reCreateContent = new ReCreateContent(memento);
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento){
		memento.setConfirm(this.confirm);
		memento.setImplementAtr(this.implementAtr);
		memento.setExecutionId(this.executionId);
		memento.setCopyStartDate(this.copyStartDate);
		memento.setCreateMethodAtr(this.createMethodAtr);
		reCreateContent.saveToMemento(memento);
	}

	public void setImplementAtr(ImplementAtr implementAtr) {
		this.implementAtr = implementAtr;
	}
	
	public ScheduleCreateContent() {
	}

	public void setReCreateContent(ReCreateContent reCreateContent) {
		this.reCreateContent = reCreateContent;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public void setCreateMethodAtr(CreateMethodAtr createMethodAtr) {
		this.createMethodAtr = createMethodAtr;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	
}
