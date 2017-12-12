package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;


/**
 * The Class JpaMonthlyPatternWorkScheduleCreGetMemento.
 */
public class JpaMonthlyPatternWorkScheduleCreGetMemento implements MonthlyPatternWorkScheduleCreGetMemento{
	
	/** The kshmt schedule method. */
	private KshmtScheduleMethod kshmtScheduleMethod;
	
	/**
	 * Instantiates a new jpa monthly pattern work schedule cre get memento.
	 *
	 * @param entity the entity
	 */
	public JpaMonthlyPatternWorkScheduleCreGetMemento(KshmtScheduleMethod entity){
		this.kshmtScheduleMethod = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCreGetMemento#getReferenceType()
	 */
	@Override
	public TimeZoneScheduledMasterAtr getReferenceType() {
		return TimeZoneScheduledMasterAtr.valueOf(this.kshmtScheduleMethod.getMPatternWorkScheCreate());
	}
}
