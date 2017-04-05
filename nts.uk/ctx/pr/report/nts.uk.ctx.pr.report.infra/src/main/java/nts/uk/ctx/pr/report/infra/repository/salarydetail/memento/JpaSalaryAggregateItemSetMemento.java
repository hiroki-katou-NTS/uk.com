/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemName;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemSetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.TaxDivision;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreDetail;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreDetailPK;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHeadPK;

/**
 * The Class JpaSalaryAggregateItemSetMemento.
 */
public class JpaSalaryAggregateItemSetMemento implements SalaryAggregateItemSetMemento {

	/** The agger head. */

	/**
	 * Gets the agger head.
	 *
	 * @return the agger head
	 */
	@Getter
	private QlsptPaylstAggreHead aggerHead;

	/**
	 * Instantiates a new jpa salary aggregate item set memento.
	 *
	 * @param aggerHead
	 *            the agger head
	 */
	public JpaSalaryAggregateItemSetMemento(QlsptPaylstAggreHead aggerHead) {
		this.aggerHead = aggerHead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setCompanyCode(nts.uk.ctx.pr.report.dom.
	 * company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		QlsptPaylstAggreHeadPK pk = new QlsptPaylstAggreHeadPK();
		pk.setCcd(companyCode);
		this.aggerHead.setQlsptPaylstAggreHeadPK(pk);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSalaryAggregateItemCode(nts.uk.ctx.pr.
	 * report.dom.salarydetail.aggregate.SalaryAggregateItemCode)
	 */
	@Override
	public void setSalaryAggregateItemCode(SalaryAggregateItemCode salaryAggregateItemCode) {
		QlsptPaylstAggreHeadPK pk = this.aggerHead.getQlsptPaylstAggreHeadPK();
		pk.setAggregateCd(salaryAggregateItemCode.v());
		this.aggerHead.setQlsptPaylstAggreHeadPK(pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSalaryAggregateItemName(nts.uk.ctx.pr.
	 * report.dom.salarydetail.aggregate.SalaryAggregateItemName)
	 */
	@Override
	public void setSalaryAggregateItemName(SalaryAggregateItemName salaryAggregateItemName) {
		this.aggerHead.setAggregateName(salaryAggregateItemName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSubItemCodes(java.util.Set)
	 */
	@Override
	public void setSubItemCodes(Set<SalaryItem> subItemCodes) {
		this.aggerHead.setQlsptPaylstAggreDetailList(subItemCodes.stream().map(item -> {
			QlsptPaylstAggreDetail detail = new QlsptPaylstAggreDetail();
			QlsptPaylstAggreDetailPK pk = new QlsptPaylstAggreDetailPK();
			pk.setItemCd(item.getSalaryItemCode());
			pk.setCcd(this.aggerHead.getQlsptPaylstAggreHeadPK().getCcd());
			pk.setAggregateCd(this.aggerHead.getQlsptPaylstAggreHeadPK().getAggregateCd());
			detail.setQlsptPaylstAggreDetailPK(pk);
			return detail;
		}).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setTaxDivision(nts.uk.ctx.pr.report.dom.
	 * salarydetail.aggregate.TaxDivision)
	 */
	@Override
	public void setTaxDivision(TaxDivision taxDivision) {
		this.aggerHead.setQlsptPaylstAggreDetailList(
			this.aggerHead.getQlsptPaylstAggreDetailList().stream().map(item -> {
				QlsptPaylstAggreDetail detail = item;
				QlsptPaylstAggreDetailPK pk = detail.getQlsptPaylstAggreDetailPK();
				pk.setCtgAtr(taxDivision.value);
				detail.setQlsptPaylstAggreDetailPK(pk);
				return detail;
			}).collect(Collectors.toList()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setItemCategory(int)
	 */
	@Override
	public void setItemCategory(int itemCategory) {
		// No thing
	}

}
