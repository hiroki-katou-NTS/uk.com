/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMny;

/**
 * The Class JpaWageTableHistorySetMemento.
 */
public class JpaWageTableHistorySetMemento implements WageTableHistorySetMemento {

	/** The type value. */
	protected QwtmtWagetableHist typeValue;

	/**
	 * Instantiates a new jpa wage table history set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHistorySetMemento(QwtmtWagetableHist typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setCompanyCode(nts.uk.ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = new QwtmtWagetableHistPK();
		qwtmtWagetableHistPK.setCcd(companyCode.v());
		this.typeValue.setQwtmtWagetableHistPK(qwtmtWagetableHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setCode(nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public void setCode(WageTableCode code) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		qwtmtWagetableHistPK.setWageTableCd(code.v());
		this.typeValue.setQwtmtWagetableHistPK(qwtmtWagetableHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setHistoryId(java.lang.String)
	 */
	@Override
	public void setHistoryId(String historyId) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		qwtmtWagetableHistPK.setHistId(historyId);
		this.typeValue.setQwtmtWagetableHistPK(qwtmtWagetableHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setApplyRange(nts.uk.ctx.pr.core.dom.insurance.MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.typeValue.setStrYm(applyRange.getStartMonth().v());
		this.typeValue.setEndYm(applyRange.getEndMonth().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setDemensionDetail(java.util.List)
	 */
	@Override
	public void setDemensionDetail(List<WageTableElement> demensionDetails) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		String companyCode = qwtmtWagetableHistPK.getCcd();
		String wageTableCd = qwtmtWagetableHistPK.getWageTableCd();
		String historyId = qwtmtWagetableHistPK.getHistId();

		List<QwtmtWagetableEleHist> qwtmtWagetableEleHistList = demensionDetails.stream()
				.map(item -> {
					QwtmtWagetableEleHist qwtmtWagetableEleHist = new QwtmtWagetableEleHist();
					item.saveToMemento(new JpaWageTableDetailSetMemento(companyCode, wageTableCd,
							historyId, qwtmtWagetableEleHist));
					return qwtmtWagetableEleHist;
				}).collect(Collectors.toList());

		this.typeValue.setQwtmtWagetableEleHistList(qwtmtWagetableEleHistList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistorySetMemento#
	 * setValueItems(java.util.List)
	 */
	@Override
	public void setValueItems(List<WageTableItem> elements) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		String companyCode = qwtmtWagetableHistPK.getCcd();
		String wageTableCd = qwtmtWagetableHistPK.getWageTableCd();
		String historyId = qwtmtWagetableHistPK.getHistId();

		List<QwtmtWagetableMny> qwtmtWagetableMnyList = elements.stream().map(item -> {
			QwtmtWagetableMny entity = new QwtmtWagetableMny();
			item.saveToMemento(
					new JpaWageTableItemSetMemento(companyCode, wageTableCd, historyId, entity));
			return entity;
		}).collect(Collectors.toList());
		this.typeValue.setQwtmtWagetableMnyList(qwtmtWagetableMnyList);
	}

}
