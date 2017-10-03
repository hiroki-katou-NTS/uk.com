/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class Content.
 */
// 実施内容
@Getter
public class Content extends DomainObject{

	/** The confirm. */
	// 作成時に確定済みにする
	private Boolean confirm;
	
	/** The re created info. */
	// 再作成情報
	private ReCreatedInfo reCreatedInfo;
	
	/** The implement atr. */
	// 実施区分
	private ImplementAtr implementAtr;
	
	/**
	 * To domain.
	 *
	 * @param memento the memento
	 * @return the content
	 */
	public Content toDomain(ExecutionContentGetMemento memento) {
		this.confirm = memento.getConfirm();
		this.reCreatedInfo = new ReCreatedInfo().toDomain(memento);
		this.implementAtr = memento.getImplementAtr();
		return this;
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ExecutionContentSetMemento memento){
		memento.setConfirm(this.confirm);
		this.reCreatedInfo.saveToMemento(memento);
		memento.setImplementAtr(this.implementAtr);
	}
}
