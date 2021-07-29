package nts.uk.ctx.at.shared.dom.workingcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;


/**
 * The Class WorkScheduleBusCal.
 */
@Getter
// 営業日カレンダーによる勤務予定作成
public class WorkScheduleBusCal extends DomainObject {
	
	/** The reference business day calendar. */
	// 営業日カレンダーの参照先
	private WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar;
	
	/** The reference basic work. */
	// 基本勤務の参照先
	private WorkScheduleMasterReferenceAtr referenceBasicWork;
	
	/** The reference working hours. */
	// 就業時間帯の参照先
	private TimeZoneScheduledMasterAtr referenceWorkingHours;
	
	
	/**
	 * Instantiates a new work schedule bus cal.
	 *
	 * @param memento the memento
	 */
	public WorkScheduleBusCal(WorkScheduleBusCalGetMemento memento){
		this.referenceBusinessDayCalendar = memento.getReferenceBusinessDayCalendar();
		this.referenceBasicWork = memento.getReferenceBasicWork();
		this.referenceWorkingHours = memento.getReferenceWorkingHours();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkScheduleBusCalSetMemento memento) {
		memento.setReferenceBusinessDayCalendar(this.referenceBusinessDayCalendar);
//		memento.setReferenceBasicWork(this.referenceBasicWork);
		memento.setReferenceWorkingHours(this.referenceWorkingHours);
	}

	public WorkScheduleBusCal(WorkScheduleMasterReferenceAtr referenceBusinessDayCalendar,
			WorkScheduleMasterReferenceAtr referenceBasicWork, TimeZoneScheduledMasterAtr referenceWorkingHours) {
		super();
		this.referenceBusinessDayCalendar = referenceBusinessDayCalendar;
		this.referenceBasicWork = referenceBasicWork;
		this.referenceWorkingHours = referenceWorkingHours;
	}
}
