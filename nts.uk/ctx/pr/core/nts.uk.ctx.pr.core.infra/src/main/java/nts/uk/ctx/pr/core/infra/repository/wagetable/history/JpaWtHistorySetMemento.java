/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WtCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistorySetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtItem;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.ElementSetting;
import nts.uk.ctx.pr.core.dom.wagetable.history.element.StepElementSetting;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableMny;

/**
 * The Class JpaWageTableHistorySetMemento.
 */
public class JpaWtHistorySetMemento implements WtHistorySetMemento {

	/** The type value. */
	protected QwtmtWagetableHist typeValue;

	/**
	 * Instantiates a new jpa wage table history set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWtHistorySetMemento(QwtmtWagetableHist typeValue) {
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
	public void setWageTableCode(WtCode code) {
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
	 * setValueItems(java.util.List)
	 */
	@Override
	public void setValueItems(List<WtItem> elements) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		String companyCode = qwtmtWagetableHistPK.getCcd();
		String wageTableCd = qwtmtWagetableHistPK.getWageTableCd();
		String historyId = qwtmtWagetableHistPK.getHistId();

		List<QwtmtWagetableMny> qwtmtWagetableMnyList = elements.stream().map(item -> {
			QwtmtWagetableMny entity = new QwtmtWagetableMny();
			item.saveToMemento(
					new JpaWtItemSetMemento(companyCode, wageTableCd, historyId, entity));
			return entity;
		}).collect(Collectors.toList());
		this.typeValue.setQwtmtWagetableMnyList(qwtmtWagetableMnyList);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WtHistorySetMemento#setElementSettings(java.util.List)
	 */
	@Override
	public void setElementSettings(List<ElementSetting> elementSettings) {
		QwtmtWagetableHistPK qwtmtWagetableHistPK = this.typeValue.getQwtmtWagetableHistPK();
		String companyCode = qwtmtWagetableHistPK.getCcd();
		String wageTableCd = qwtmtWagetableHistPK.getWageTableCd();
		String historyId = qwtmtWagetableHistPK.getHistId();

		List<QwtmtWagetableEleHist> qwtmtWagetableEleHistList = elementSettings.stream()
				.map(item -> {
					QwtmtWagetableEleHist qwtmtWagetableEleHist = new QwtmtWagetableEleHist();
					QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK = new QwtmtWagetableEleHistPK();
					qwtmtWagetableEleHistPK.setCcd(companyCode);
					qwtmtWagetableEleHistPK.setWageTableCd(wageTableCd);
					qwtmtWagetableEleHistPK.setHistId(historyId);
					qwtmtWagetableEleHistPK.setDemensionNo(item.getDemensionNo().value);
					qwtmtWagetableEleHist.setQwtmtWagetableEleHistPK(qwtmtWagetableEleHistPK);
					if (item instanceof StepElementSetting) {
						StepElementSetting step = (StepElementSetting) item;
						qwtmtWagetableEleHist.setDemensionUpperLimit(step.getUpperLimit());
						qwtmtWagetableEleHist.setDemensionLowerLimit(step.getLowerLimit());
						qwtmtWagetableEleHist.setDemensionInterval(step.getInterval());
					} else {
						qwtmtWagetableEleHist.setDemensionUpperLimit(BigDecimal.ZERO);
						qwtmtWagetableEleHist.setDemensionLowerLimit(BigDecimal.ZERO);
						qwtmtWagetableEleHist.setDemensionInterval(BigDecimal.ONE);						
					}
					return qwtmtWagetableEleHist;
				}).collect(Collectors.toList());

		this.typeValue.setQwtmtWagetableEleHistList(qwtmtWagetableEleHistList);
	}

}
