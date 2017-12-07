package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class MonthlyPatternWorkScheduleCre.
 */
@Getter
public class MonthlyPatternWorkScheduleCre extends DomainObject {
	
	/** The reference type. */
	private TimeZoneScheduledMasterAtr referenceType;
	
	/**
	 * Instantiates a new MonthlyPatternWorkScheduleCre
	 *
	 * @param memento the memento
	 */
	public MonthlyPatternWorkScheduleCre(MonthlyPatternWorkScheduleCreGetMemento memento){
		this.referenceType = memento.getReferenceType();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(MonthlyPatternWorkScheduleCreSetMemento memento) {
		memento.setReferenceType(this.referenceType);
	}
}
