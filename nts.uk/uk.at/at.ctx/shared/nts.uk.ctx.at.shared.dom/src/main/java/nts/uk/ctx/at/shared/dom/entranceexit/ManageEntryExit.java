/**
 * 
 */
package nts.uk.ctx.at.shared.dom.entranceexit;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class ManageEntryExit.
 *
 * @author hoangdd
 * 入退門管理
 */
@Getter
public class ManageEntryExit extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	// 使用区分
	private NotUseAtr useClassification;

	/**
	 * Instantiates a new manage entry exit.
	 *
	 * @param memento the memento
	 */
	public ManageEntryExit(ManageEntryExitGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.useClassification = memento.getUseCls();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ManageEntryExitSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setUseCls(useClassification.value);
	}
}

