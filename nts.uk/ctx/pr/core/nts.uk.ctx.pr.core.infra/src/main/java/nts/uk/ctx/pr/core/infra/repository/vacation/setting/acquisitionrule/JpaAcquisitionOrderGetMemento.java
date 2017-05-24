package nts.uk.ctx.pr.core.infra.repository.vacation.setting.acquisitionrule;

import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.Priority;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionOrderGetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionType;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.acquisitionrule.KmfstAcquisitionRule;

/**
 * The Class JpaAcquisitionOrderGetMemento.
 */
public class JpaAcquisitionOrderGetMemento implements AcquisitionOrderGetMemento {

	/** The type value. */
	private KmfstAcquisitionRule typeValue;

	/** The va type. */
	private AcquisitionType vaType;

	public JpaAcquisitionOrderGetMemento(KmfstAcquisitionRule typeValue, AcquisitionType vaType) {
		super();
		this.typeValue = typeValue;
		this.vaType = vaType;
	}

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
