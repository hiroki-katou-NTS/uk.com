package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.Getter;

@Getter
public class DisplayRestriction {
	
	/**
	 * 年次有給休暇の表示制限
	 */
	private LeaveHolidayRestriction year;
	
	/**
	 * 積立年休の表示制限
	 */
	private LeaveHolidayRestriction savingYear;
	
	/**
	 * 振休の表示制限
	 */
	private LeaveHolidayRestriction compensatory;
	
	/**
	 * 代休の表示制限
	 */
	private LeaveHolidayRestriction substitution;
	
}
