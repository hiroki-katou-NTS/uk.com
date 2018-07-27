package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class MonthlyPatternWorkScheduleCre.
 */
@Getter
// 月間パターンによる勤務予定作成
public class MonthlyPatternWorkScheduleCre extends DomainObject {
	
	/** The reference type. */
	// 勤務種類と就業時間帯の参照先
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

	public MonthlyPatternWorkScheduleCre(int referenceType) {
		super();
		this.referenceType = EnumAdaptor.valueOf(referenceType, TimeZoneScheduledMasterAtr.class);
	}
	
	
}
