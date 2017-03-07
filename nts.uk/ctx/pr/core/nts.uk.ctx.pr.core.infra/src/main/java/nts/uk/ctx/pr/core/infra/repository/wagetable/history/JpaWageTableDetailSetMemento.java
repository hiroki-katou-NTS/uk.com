/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailSetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableCd;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHistPK;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableNum;

/**
 * The Class JpaWageTableDetailSetMemento.
 */
public class JpaWageTableDetailSetMemento implements WageTableDetailSetMemento {

	/** The type value. */
	protected QwtmtWagetableEleHist typeValue;

	/**
	 * Instantiates a new jpa wage table detail set memento.
	 *
	 * @param companyCode
	 *            the company code
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableDetailSetMemento(String companyCode, String wageTableCd, String histId,
			QwtmtWagetableEleHist typeValue) {
		this.typeValue = typeValue;
		QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK = new QwtmtWagetableEleHistPK();
		qwtmtWagetableEleHistPK.setCcd(companyCode);
		qwtmtWagetableEleHistPK.setWageTableCd(wageTableCd);
		qwtmtWagetableEleHistPK.setHistId(histId);
		this.typeValue.setQwtmtWagetableEleHistPK(qwtmtWagetableEleHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailSetMemento#
	 * setDemensionNo(nts.uk.ctx.pr.core.dom.wagetable.DemensionNo)
	 */
	@Override
	public void setDemensionNo(DemensionNo demensionNo) {
		QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK = this.typeValue
				.getQwtmtWagetableEleHistPK();
		qwtmtWagetableEleHistPK.setDemensionNo(demensionNo.value);
		this.typeValue.setQwtmtWagetableEleHistPK(qwtmtWagetableEleHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailSetMemento#
	 * setElementModeSetting(nts.uk.ctx.pr.core.dom.wagetable.element.
	 * ElementMode)
	 */
	@Override
	public void setElementModeSetting(ElementMode elementModeSetting) {
		// Get primary info.
		QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK = this.typeValue
				.getQwtmtWagetableEleHistPK();
		String companyCode = qwtmtWagetableEleHistPK.getCcd();
		String wageTableCd = qwtmtWagetableEleHistPK.getWageTableCd();
		String histId = qwtmtWagetableEleHistPK.getHistId();
		Integer demensionNo = qwtmtWagetableEleHistPK.getDemensionNo();

		// Set range setting.
		if (elementModeSetting.getElementType().isRangeMode) {
			StepMode stepMode = (StepMode) elementModeSetting;
			this.typeValue.setDemensionLowerLimit(stepMode.getLowerLimit());
			this.typeValue.setDemensionUpperLimit(stepMode.getUpperLimit());
			this.typeValue.setDemensionInterval(stepMode.getInterval());
		} else {
			this.typeValue.setDemensionLowerLimit(BigDecimal.ZERO);
			this.typeValue.setDemensionUpperLimit(BigDecimal.ZERO);
			this.typeValue.setDemensionInterval(BigDecimal.ZERO);
		}

		// Save item
		// case MASTER_REF | CODE_REF | LEVEL | CERTIFICATION
		if (elementModeSetting.getElementType().isCodeMode) {
			List<QwtmtWagetableCd> qwtmtWagetableCdList = ((RefMode) elementModeSetting).getItems()
					.stream().map(item -> {
						QwtmtWagetableCd entity = new QwtmtWagetableCd(companyCode, wageTableCd,
								histId, demensionNo, item.getReferenceCode());
						entity.setElementId(item.getUuid());
						return entity;
					}).collect(Collectors.toList());
			this.typeValue.setQwtmtWagetableCdList(qwtmtWagetableCdList);
		}

		// Save item
		// case ITEM_DATA_REF | EXPERIENCE_FIX | AGE_FIX | FAMILY_MEM_FIX |
		// WORKING_DAY | COME_LATE
		if (elementModeSetting.getElementType().isRangeMode) {
			List<QwtmtWagetableNum> qwtmtWagetableNumList = ((StepMode) elementModeSetting)
					.getItems().stream().map(item -> {
						QwtmtWagetableNum entity = new QwtmtWagetableNum(companyCode, wageTableCd,
								histId, demensionNo, item.getOrderNumber());
						entity.setElementId(item.getUuid());
						return entity;
					}).collect(Collectors.toList());

			this.typeValue.setQwtmtWagetableNumList(qwtmtWagetableNumList);
		}
	}
}
