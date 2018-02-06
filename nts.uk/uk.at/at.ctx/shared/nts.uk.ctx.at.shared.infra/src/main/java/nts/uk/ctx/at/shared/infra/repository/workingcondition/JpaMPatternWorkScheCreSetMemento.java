package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaMonthlyPatternWorkScheduleCreSetMemento.
 */
public class JpaMPatternWorkScheCreSetMemento implements MonthlyPatternWorkScheduleCreSetMemento{

	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa monthly pattern work schedule cre set memento.
	 *
	 * @param entity the entity
	 */
	public JpaMPatternWorkScheCreSetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreSetMemento#setReferenceType(nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr)
	 */
	@Override
	public void setReferenceType(TimeZoneScheduledMasterAtr referenceType) {
		if (referenceType != null) {
			this.kshmtScheduleMethod.setMPatternWorkScheCreate(referenceType.value);
		}
	}
}
