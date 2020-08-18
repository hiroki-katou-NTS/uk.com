/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;


/**
 * The Class ScheduleCreateContent.
 */
//Domain: スケジュール作成内容
@Getter
public class ScheduleCreateContent extends AggregateRoot{

	/** The execution id. */
	// 実行ID
	private String executionId;

	/** The re confirm. */
	// 	確定済みにする
	private Boolean confirm;

	/** The creation type. */
	//作成種類
	private ImplementAtr creationType;
	
	/** The specify creation. */
	// 作成方法の指定
	private SpecifyCreation specifyCreation;
	
	//再作成条件
	private RecreateCondition recreateCondition;

	//TODO: bien tam thoi, se xoa sau khi co tai lieu moi
	private ImplementAtr implementAtr;
	private ReCreateContent reCreateContent;
	private CreateMethodAtr createMethodAtr;
	
	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the execution content
	 */
	public ScheduleCreateContent (ScheduleCreateContentGetMemento memento){
		this.executionId = memento.getExecutionId();
		this.confirm = memento.getConfirm();
		this.creationType = memento.getCreationType();
		this.specifyCreation = memento.getSpecifyCreation();
		this.recreateCondition = memento.getRecreateCondition();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ScheduleCreateContentSetMemento memento){
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		memento.setConfirm(this.confirm);
//		memento.setImplementAtr(this.implementAtr);
//		memento.setExecutionId(this.executionId);
//		memento.setCopyStartDate(this.copyStartDate);
//		memento.setCreateMethodAtr(this.createMethodAtr);
//		reCreateContent.saveToMemento(memento);
	}

	public void setImplementAtr(ImplementAtr implementAtr) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		this.implementAtr = implementAtr;
	}
	
	public ScheduleCreateContent() {
	}

	public void setReCreateContent(ReCreateContent reCreateContent) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		this.reCreateContent = reCreateContent;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	public void setCreateMethodAtr(CreateMethodAtr createMethodAtr) {
		//TODO Sua domain: スケジュール作成内容 se tiep tuc khi co tai lieu moi cua man ksc001
//		this.createMethodAtr = createMethodAtr;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
	
}
