/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.core.dom.company.CompanyCode;
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

		List<WageTableElement> wagetableElementList = this.typeValue.getWagetableElementList()
				.stream().map(item -> new WageTableElement(new JpaWageTableElementGetMemento(item)))
				.collect(Collectors.toList());

		switch (ElementCount.valueOf(this.typeValue.getDemensionSet())) {
		case One: {
			return new OneDimensionalMode(wagetableElementList);
		}

		case Two: {
			return new TwoDimensionalMode(wagetableElementList);
		}

		case Three: {
			return new ThreeDimensionalMode(wagetableElementList);
		}

		case Finework: {
			return new FineworkDimensionalMode(wagetableElementList);
		}

		// case Qualification:
		default:
			return new QualificaDimensionalMode(wagetableElementList);
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
