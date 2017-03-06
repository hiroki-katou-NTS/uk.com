/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreDetail;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreDetailPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK;

/**
 * The Class JpaWLAggregateItemSetMemento.
 */
public class JpaWLAggregateItemSetMemento implements WLAggregateItemSetMemento {
	
	/** The entity. */
	private QlsptLedgerAggreHead entity;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The code. */
	private WLAggregateItemCode code;
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/**
	 * Instantiates a new jpa WL aggregate item set memento.
	 *
	 * @param entity the entity
	 * @param companyCode the company code
	 */
	public JpaWLAggregateItemSetMemento(QlsptLedgerAggreHead entity) {
		super();
		this.entity = entity;
		if (this.entity.getQlsptLedgerAggreHeadPK() == null) {
			this.entity.setQlsptLedgerAggreHeadPK(new QlsptLedgerAggreHeadPK());
		}
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCompanyCode(nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		this.entity.getQlsptLedgerAggreHeadPK().setCcd(companyCode.v());
		this.companyCode = companyCode;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCategory(nts.uk.ctx.pr.report.dom.wageledger.WLCategory)
	 */
	@Override
	public void setCategory(WLCategory category) {
		this.entity.getQlsptLedgerAggreHeadPK().setCtgAtr(category.value);
		this.category = category;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType the new payment type
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setPaymentType(nts.uk.ctx.pr.report.dom.wageledger.PaymentType)
	 */
	@Override
	public void setPaymentType(PaymentType paymentType) {
		this.entity.getQlsptLedgerAggreHeadPK().setPayBonusAtr(paymentType.value);
		this.paymentType = paymentType;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCode(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public void setCode(WLAggregateItemCode code) {
		this.entity.getQlsptLedgerAggreHeadPK().setAggregateCd(code.v());
		this.code = code;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setName(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName)
	 */
	@Override
	public void setName(WLAggregateItemName name) {
		this.entity.setAggregateName(name.v());
	}

	/**
	 * Sets the show name zero value.
	 *
	 * @param showNameZeroValue the new show name zero value
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setShowNameZeroValue(java.lang.Boolean)
	 */
	@Override
	public void setShowNameZeroValue(Boolean showNameZeroValue) {
		this.entity.setDispNameZeroAtr(showNameZeroValue ? 1 : 0);
	}

	/**
	 * Sets the show value zero value.
	 *
	 * @param showValueZeroValue the new show value zero value
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setShowValueZeroValue(boolean)
	 */
	@Override
	public void setShowValueZeroValue(boolean showValueZeroValue) {
		this.entity.setDispZeroAtr(showValueZeroValue ? 1 : 0);
	}

	/**
	 * Sets the sub items.
	 *
	 * @param subItems the new sub items
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setSubItems(java.util.Set)
	 */
	@Override
	public void setSubItems(Set<String> subItems) {
		List<QlsptLedgerAggreDetail> itemList = new ArrayList<>();
		subItems.parallelStream().forEach(item -> {
			QlsptLedgerAggreDetail itemEntity = new QlsptLedgerAggreDetail(); 
			String itemCode = "F" + paymentType.value + code.v().substring(code.v().length() - 2);
			itemEntity.setQlsptLedgerAggreDetailPK(new QlsptLedgerAggreDetailPK(
					companyCode.v(), paymentType.value, code.v(), category.value, itemCode));
			itemList.add(itemEntity);
		});
		this.entity.setQlsptLedgerAggreDetailList(itemList);
	}

}
