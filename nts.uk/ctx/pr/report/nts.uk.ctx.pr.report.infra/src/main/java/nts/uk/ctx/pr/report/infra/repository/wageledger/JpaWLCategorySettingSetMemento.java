/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingSetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLSettingItem;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail;

/**
 * The Class JpaWLCategorySettingSetMemento.
 */
public class JpaWLCategorySettingSetMemento implements WLCategorySettingSetMemento {
	
	/** The category entitys. */
	private List<QlsptLedgerFormDetail> categoryEntities;
	
	/**
	 * Instantiates a new jpa WL category setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWLCategorySettingSetMemento(List<QlsptLedgerFormDetail> categoryEntities) {
		this.categoryEntities = categoryEntities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingSetMemento
	 * #setCategory(nts.uk.ctx.pr.report.dom.wageledger.WLCategory)
	 */
	@Override
	public void setCategory(WLCategory category) {
		this.categoryEntities.forEach(
				cate -> cate.getQlsptLedgerFormDetailPK().setCtgAtr(category.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingSetMemento
	 * #setPaymentType(nts.uk.ctx.pr.report.dom.wageledger.PaymentType)
	 */
	@Override
	public void setPaymentType(PaymentType paymentType) {
		this.categoryEntities.forEach(
				cate -> cate.getQlsptLedgerFormDetailPK().setPayBonusAtr(paymentType.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySettingSetMemento
	 * #setOutputItems(java.util.List)
	 */
	@Override
	public void setOutputItems(List<WLSettingItem> outputItems) {
		this.categoryEntities = outputItems.stream().map(item -> {
			QlsptLedgerFormDetail detail = new QlsptLedgerFormDetail();
			item.saveToMemento(new JpaWLSettingItemSetMemento(detail));
			return detail;
		}).collect(Collectors.toList());
	}

}
