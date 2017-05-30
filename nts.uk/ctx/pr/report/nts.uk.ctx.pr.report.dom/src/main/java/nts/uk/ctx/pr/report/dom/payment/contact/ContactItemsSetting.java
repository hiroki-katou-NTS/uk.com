/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.contact;

import java.util.Set;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ContactItemsSetting.
 */
@Getter
public class ContactItemsSetting extends AggregateRoot {

	/** The contact items code. */
	private ContactItemsCode contactItemsCode;

	/** The comment initial cp. */
	private ReportComment initialCpComment;

	/** The comment month cp. */
	private ReportComment monthCpComment;

	/** The month em comments. */
	private Set<EmpComment> monthEmComments;

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new contact items setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public ContactItemsSetting(ContactItemsSettingGetMemento memento) {
		this.contactItemsCode = memento.getContactItemsCode();
		this.initialCpComment = memento.getInitialCpComment();
		this.monthCpComment = memento.getMonthCpComment();
		this.monthEmComments = memento.getMonthEmComments();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(ContactItemsSettingSetMemento memento) {
		memento.setContactItemsCode(this.contactItemsCode);
		memento.setInitialCpComment(this.initialCpComment);
		memento.setMonthCpComment(this.monthCpComment);
		memento.setMonthEmComments(this.monthEmComments);
	}
}
