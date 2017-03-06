/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.element;

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
	 * Instantiates a new jpa certify group get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableElementGetMemento(QwtmtWagetableElement typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public DemensionNo getDemensionNo() {
		return DemensionNo.valueOf(this.typeValue.getQwtmtWagetableElementPK().getDemensionNo());
	}

	@Override
	public ElementMode getElementModeSetting() {
		CompanyCode companyCode = new CompanyCode(
				this.typeValue.getQwtmtWagetableElementPK().getCcd());
		// TODO: can xem xet lai.
		QwtmtWagetableEleHist qwtmtWagetableEleHist = this.typeValue.getQwtmtWagetableEleHistList()
				.get(0);

		switch (ElementType.valueOf(this.typeValue.getDemensionType())) {
		case MASTER_REF:
			return new RefMode(ElementType.MASTER_REF, companyCode,
					new WtElementRefNo(this.typeValue.getDemensionRefNo()));

		case CODE_REF:
			return new RefMode(ElementType.CODE_REF, companyCode,
					new WtElementRefNo(this.typeValue.getDemensionRefNo()));

		case ITEM_DATA_REF:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.ITEM_DATA_REF,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case EXPERIENCE_FIX:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.EXPERIENCE_FIX,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case AGE_FIX:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.EXPERIENCE_FIX,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case FAMILY_MEM_FIX:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.EXPERIENCE_FIX,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case WORKING_DAY:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.WORKING_DAY,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case COME_LATE:
			if (qwtmtWagetableEleHist == null) {
				// TODO: Add msg.
				throw new RuntimeException("");
			}
			return new StepMode(ElementType.COME_LATE,
					qwtmtWagetableEleHist.getDemensionLowerLimit(),
					qwtmtWagetableEleHist.getDemensionUpperLimit(),
					qwtmtWagetableEleHist.getDemensionInterval());

		case LEVEL:
			return new LevelMode();

		// case CERTIFICATION:
		default:
			return new CertifyMode();
		}
	}

}
