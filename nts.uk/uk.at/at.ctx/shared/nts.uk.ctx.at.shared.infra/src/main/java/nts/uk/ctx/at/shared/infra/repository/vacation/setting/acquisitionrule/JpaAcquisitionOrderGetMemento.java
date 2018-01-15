/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.acquisitionrule;

import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrderGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Priority;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule.KarstAcquisitionRule;

/**
 * The Class JpaAcquisitionOrderGetMemento.
 */
public class JpaAcquisitionOrderGetMemento implements AcquisitionOrderGetMemento {

	/** The type value. */
	private KarstAcquisitionRule typeValue;

	/** The va type. */
	private AcquisitionType vaType;

	/**
	 * Instantiates a new jpa acquisition order get memento.
	 *
	 * @param typeValue
	 *            the type value
	 * @param vaType
	 *            the va type
	 */
	public JpaAcquisitionOrderGetMemento(KarstAcquisitionRule typeValue, AcquisitionType vaType) {
		super();
		this.typeValue = typeValue;
		this.vaType = vaType;
	}

	/**
	 * Gets the vacation type.
	 *
	 * @return the vacation type
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcOrderGetMemento#getVacationType()
	 */
	@Override
	public AcquisitionType getVacationType() {
		return this.vaType;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.
	 * VaAcOrderGetMemento#getPriority()
	 */
	@Override
	public Priority getPriority() {
		switch (this.vaType) {
		case AnnualPaidLeave:
			return new Priority(this.typeValue.getAnnualPaid());

		case CompensatoryDayOff:
			return new Priority(this.typeValue.getCompensatoryDayOff());

		case ExsessHoliday:
			return new Priority(this.typeValue.getExsessHoliday());

		case FundedPaidHoliday:
			return new Priority(this.typeValue.getFundedPaidHoliday());

		case SpecialHoliday:
			return new Priority(this.typeValue.getSpecialHoliday());
		// case SubstituteHoliday:
		default:
			return new Priority(this.typeValue.getSabstituteHoliday());
		}
	}

}
