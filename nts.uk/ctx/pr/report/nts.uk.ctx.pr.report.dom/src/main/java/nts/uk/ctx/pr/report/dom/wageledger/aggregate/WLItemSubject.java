/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class WLAggregateItemSubject.
 */
@Getter
public class WLItemSubject {
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The code. */
	private WLAggregateItemCode code;
	
	/**
	 * Instantiates a new WL item subject.
	 *
	 * @param getMemento the get memento
	 */
	public WLItemSubject(WLItemSubjectGetMemento getMemento) {
		this.category = getMemento.getCategory();
		this.paymentType = getMemento.getPaymentType();
		this.companyCode = getMemento.getCompanyCode();
		this.code = getMemento.getCode();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param setMemento the set memento
	 */
	public void saveToMemento(WLItemSubjectSetMemento setMemento) {
		setMemento.setCateory(this.category);
		setMemento.setCode(this.code);
		setMemento.setPaymentType(this.paymentType);
		setMemento.setCompanyCode(this.companyCode);
	}
}
