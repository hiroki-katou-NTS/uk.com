/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.element;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementType;
import nts.uk.ctx.pr.core.dom.wagetable.WtElementRefNo;
import nts.uk.ctx.pr.core.dom.wagetable.element.CertifyMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.ElementMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.LevelMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.RefMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.StepMode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementGetMemento;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.entity.wagetable.history.QwtmtWagetableEleHist;

/**
 * The Class JpaWageTableElementGetMemento.
 */
public class JpaWageTableElementGetMemento implements WageTableElementGetMemento {

	/** The type value. */
	protected QwtmtWagetableElement typeValue;

	/**
	 * Instantiates a new jpa wage table element get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableElementGetMemento(QwtmtWagetableElement typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementGetMemento#
	 * getDemensionNo()
	 */
	@Override
	public DemensionNo getDemensionNo() {
		return DemensionNo.valueOf(this.typeValue.getQwtmtWagetableElementPK().getDemensionNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElementGetMemento#
	 * getElementModeSetting()
	 */
	@Override
	public ElementMode getElementModeSetting() {

		CompanyCode companyCode = new CompanyCode(
				this.typeValue.getQwtmtWagetableElementPK().getCcd());

		Map<Integer, QwtmtWagetableEleHist> eleHistMap = this.typeValue
				.getQwtmtWagetableEleHistList().stream().collect(
						Collectors.toMap(item -> item.getQwtmtWagetableEleHistPK().getDemensionNo(),
								Function.identity()));

		QwtmtWagetableEleHist qwtmtWagetableEleHist = eleHistMap
				.get(this.typeValue.getQwtmtWagetableElementPK().getDemensionNo());

		switch (ElementType.valueOf(this.typeValue.getDemensionType())) {
		case LEVEL:
			return new LevelMode();

		case CERTIFICATION:
			return new CertifyMode();

		default:
			if (ElementType.valueOf(this.typeValue.getDemensionType()).isCodeMode) {
				return new RefMode(ElementType.valueOf(this.typeValue.getDemensionType()),
						companyCode, new WtElementRefNo(this.typeValue.getDemensionRefNo()));
			}

			if (ElementType.valueOf(this.typeValue.getDemensionType()).isRangeMode) {
				return new StepMode(ElementType.valueOf(this.typeValue.getDemensionType()),
						qwtmtWagetableEleHist.getDemensionLowerLimit(),
						qwtmtWagetableEleHist.getDemensionUpperLimit(),
						qwtmtWagetableEleHist.getDemensionInterval());
			}

			return null;
		}

	}

}
