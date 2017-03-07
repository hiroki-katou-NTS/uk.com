/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.history;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.CertifyMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.CodeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.LevelMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.RangeItem;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableCd;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableNum;

/**
 * The Class JpaWageTableDetailGetMemento.
 */
public class JpaWageTableDetailGetMemento implements WageTableDetailGetMemento {

	/** The type value. */
	protected QwtmtWagetableEleHist typeValue;

	/**
	 * Instantiates a new jpa wage table detail get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableDetailGetMemento(QwtmtWagetableEleHist typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento#
	 * getDemensionNo()
	 */
	@Override
	public DemensionNo getDemensionNo() {
		return DemensionNo.valueOf(this.typeValue.getQwtmtWagetableEleHistPK().getDemensionNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDetailGetMemento#
	 * getElementModeSetting()
	 */
	@Override
	public ElementMode getElementModeSetting() {
		CompanyCode companyCode = new CompanyCode(
				this.typeValue.getQwtmtWagetableEleHistPK().getCcd());

		QwtmtWagetableElement qwtmtWagetableElement = this.typeValue.getQwtmtWagetableElement();
		List<QwtmtWagetableCd> qwtmtWagetableCdList = this.typeValue.getQwtmtWagetableCdList();
		List<QwtmtWagetableNum> qwtmtWagetableNumList = this.typeValue.getQwtmtWagetableNumList();
		List<CodeItem> codeItems = null;
		List<RangeItem> rangeItems = null;

		// case MASTER_REF | CODE_REF | LEVEL | CERTIFICATION
		if (ElementType.valueOf(qwtmtWagetableElement.getDemensionType()).isCodeMode) {
			codeItems = this.getCodeItems(qwtmtWagetableCdList);
		}

		// case ITEM_DATA_REF | EXPERIENCE_FIX | AGE_FIX | FAMILY_MEM_FIX |
		// WORKING_DAY | COME_LATE
		if (ElementType.valueOf(qwtmtWagetableElement.getDemensionType()).isRangeMode) {
			rangeItems = this.getRangeItems(qwtmtWagetableNumList);
		}

		switch (ElementType.valueOf(qwtmtWagetableElement.getDemensionType())) {
		case MASTER_REF: {
			return new RefMode(ElementType.MASTER_REF, companyCode,
					new WtElementRefNo(qwtmtWagetableElement.getDemensionRefNo()), codeItems);
		}

		case CODE_REF:
			return new RefMode(ElementType.CODE_REF, companyCode,
					new WtElementRefNo(qwtmtWagetableElement.getDemensionRefNo()), codeItems);

		case ITEM_DATA_REF:
			return new StepMode(ElementType.ITEM_DATA_REF, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case EXPERIENCE_FIX:
			return new StepMode(ElementType.EXPERIENCE_FIX, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case AGE_FIX:
			return new StepMode(ElementType.EXPERIENCE_FIX, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case FAMILY_MEM_FIX:
			return new StepMode(ElementType.EXPERIENCE_FIX, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case WORKING_DAY:
			return new StepMode(ElementType.WORKING_DAY, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case COME_LATE:
			return new StepMode(ElementType.COME_LATE, this.typeValue.getDemensionLowerLimit(),
					this.typeValue.getDemensionUpperLimit(), this.typeValue.getDemensionInterval(),
					rangeItems);

		case LEVEL:
			return new LevelMode(ElementType.LEVEL, codeItems);

		// case CERTIFICATION:
		default:
			return new CertifyMode(ElementType.CERTIFICATION, codeItems);
		}
	}

	/**
	 * Gets the range items.
	 *
	 * @param qwtmtWagetableNumList
	 *            the qwtmt wagetable num list
	 * @return the range items
	 */
	private List<RangeItem> getRangeItems(List<QwtmtWagetableNum> qwtmtWagetableNumList) {
		return qwtmtWagetableNumList.stream()
				.map(item -> new RangeItem(item.getQwtmtWagetableNumPK().getElementNumNo(),
						item.getElementStr().doubleValue(), item.getElementEnd().doubleValue(),
						item.getElementId()))
				.collect(Collectors.toList());
	}

	/**
	 * Gets the code items.
	 *
	 * @param qwtmtWagetableCdList
	 *            the qwtmt wagetable cd list
	 * @return the code items
	 */
	private List<CodeItem> getCodeItems(List<QwtmtWagetableCd> qwtmtWagetableCdList) {
		return qwtmtWagetableCdList.stream()
				.map(item -> new CodeItem(item.getQwtmtWagetableCdPK().getElementCd(),
						item.getElementId()))
				.collect(Collectors.toList());
	}

}
