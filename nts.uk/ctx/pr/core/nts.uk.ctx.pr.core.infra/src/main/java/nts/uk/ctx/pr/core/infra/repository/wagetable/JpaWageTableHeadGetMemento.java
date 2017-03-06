/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.DemensionNo;
import nts.uk.ctx.pr.core.dom.wagetable.ElementCount;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableName;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.FineworkDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.OneDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.QualificaDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.ThreeDimensionalMode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.TwoDimensionalMode;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHead;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;
import nts.uk.ctx.pr.core.infra.repository.wagetable.element.JpaWageTableElementGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaWageTableHeadGetMemento.
 */
public class JpaWageTableHeadGetMemento implements WageTableHeadGetMemento {

	/** The type value. */
	protected QwtmtWagetableHead typeValue;

	/**
	 * Instantiates a new jpa wage table head get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWageTableHeadGetMemento(QwtmtWagetableHead typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.typeValue.getQwtmtWagetableHeadPK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getCode()
	 */
	@Override
	public WageTableCode getCode() {
		return new WageTableCode(this.typeValue.getQwtmtWagetableHeadPK().getWageTableCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getName()
	 */
	@Override
	public WageTableName getName() {
		return new WageTableName(this.typeValue.getWageTableName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#
	 * getDemensionSetting()
	 */
	@Override
	public DemensionalMode getDemensionSetting() {

		Map<DemensionNo, QwtmtWagetableElement> wagetableElementMap = this.typeValue
				.getWagetableElementList().stream().collect(Collectors.toMap(sc -> {
					return DemensionNo.valueOf(sc.getQwtmtWagetableElementPK().getDemensionNo());
				}, Function.identity()));

		QwtmtWagetableElement demension1st = wagetableElementMap.get(DemensionNo.DEMENSION_1ST);
		QwtmtWagetableElement demension2nd = wagetableElementMap.get(DemensionNo.DEMENSION_2ND);
		QwtmtWagetableElement demension3rd = wagetableElementMap.get(DemensionNo.DEMENSION_3RD);

		switch (ElementCount.valueOf(this.typeValue.getDemensionSet())) {
		case One: {
			WageTableElement element = new WageTableElement(
					new JpaWageTableElementGetMemento(demension1st));
			return new OneDimensionalMode(element);
		}

		case Two: {
			WageTableElement element1st = new WageTableElement(
					new JpaWageTableElementGetMemento(demension1st));
			WageTableElement element2nd = new WageTableElement(
					new JpaWageTableElementGetMemento(demension2nd));
			return new TwoDimensionalMode(element1st, element2nd);
		}

		case Three: {
			WageTableElement element1st = new WageTableElement(
					new JpaWageTableElementGetMemento(demension1st));
			WageTableElement element2nd = new WageTableElement(
					new JpaWageTableElementGetMemento(demension2nd));
			WageTableElement element3rd = new WageTableElement(
					new JpaWageTableElementGetMemento(demension3rd));
			return new ThreeDimensionalMode(element1st, element2nd, element3rd);
		}

		case Finework: {
			WageTableElement element1st = new WageTableElement(
					new JpaWageTableElementGetMemento(demension1st));
			WageTableElement element2nd = new WageTableElement(
					new JpaWageTableElementGetMemento(demension2nd));
			WageTableElement element3rd = new WageTableElement(
					new JpaWageTableElementGetMemento(demension3rd));
			return new FineworkDimensionalMode(element1st, element2nd, element3rd);
		}

		// case Qualification:
		default:
			return new QualificaDimensionalMode();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadGetMemento#getMemo()
	 */
	@Override
	public Memo getMemo() {
		return new Memo(this.typeValue.getMemo());
	}
}
