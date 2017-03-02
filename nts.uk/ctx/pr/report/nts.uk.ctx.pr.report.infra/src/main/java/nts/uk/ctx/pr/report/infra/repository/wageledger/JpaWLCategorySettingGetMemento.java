/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLSettingItem;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail;

/**
 * The Class JpaWLCategorySettingGetMemento.
 */
public class JpaWLCategorySettingGetMemento implements WLCategorySettingGetMemento {
	
	/** The details. */
	private List<QlsptLedgerFormDetail> details;
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/**
	 * Instantiates a new jpa WL category setting get memento.
	 *
	 * @param deatails the deatails
	 * @param category the category
	 * @param paymentType the payment type
	 */
	public JpaWLCategorySettingGetMemento(List<QlsptLedgerFormDetail> details, WLCategory category,
			PaymentType paymentType) {
		super();
		this.details = details;
		this.category = category;
		this.paymentType = paymentType;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingGetMemento
	 * #getCategory()
	 */
	@Override
	public WLCategory getCategory() {
		return this.category;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingGetMemento
	 * #getPaymentType()
	 */
	@Override
	public PaymentType getPaymentType() {
		return this.paymentType;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingGetMemento
	 * #getOutputItems()
	 */
	@Override
	public List<WLSettingItem> getOutputItems() {
		return this.details.stream().map(detail -> {
			return new WLSettingItem(new JpaWLSettingItemGetMemento(detail));
		}).collect(Collectors.toList());
	}

}
