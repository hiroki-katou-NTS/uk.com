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

	/**
	 * @param year
	 * @param savingYear
	 * @param compensatory
	 * @param substitution
	 */
	public DisplayRestriction(LeaveHolidayRestriction year, LeaveHolidayRestriction savingYear,
			LeaveHolidayRestriction compensatory, LeaveHolidayRestriction substitution) {
		super();
		this.year = year;
		this.savingYear = savingYear;
		this.compensatory = compensatory;
		this.substitution = substitution;
	}
	
	/**
	 * @param yearDisplayAtr
	 * @param yearRemainingNumberCheck
	 * @param savingYearDisplayAtr
	 * @param savingYearRemainingNumberCheck
	 * @param compensatoryDisplayAtr
	 * @param compensatoryRemainingNumberCheck
	 * @param substitutionDisplayAtr
	 * @param substitutionRemainingNumberCheck
	 */
	public DisplayRestriction(Boolean yearDisplayAtr, Boolean yearRemainingNumberCheck, 
			Boolean savingYearDisplayAtr, Boolean savingYearRemainingNumberCheck,
			Boolean compensatoryDisplayAtr, Boolean compensatoryRemainingNumberCheck, 
			Boolean substitutionDisplayAtr, Boolean substitutionRemainingNumberCheck) {
		super();
		this.year = new LeaveHolidayRestriction(yearDisplayAtr, yearRemainingNumberCheck);
		this.savingYear = new LeaveHolidayRestriction(savingYearDisplayAtr, savingYearRemainingNumberCheck);
		this.compensatory = new LeaveHolidayRestriction(compensatoryDisplayAtr, compensatoryRemainingNumberCheck);
		this.substitution = new LeaveHolidayRestriction(substitutionDisplayAtr, substitutionRemainingNumberCheck);
	}

	/**
	 * @param yearDisplayAtr
	 * @param yearRemainingNumberCheck
	 * @param savingYearDisplayAtr
	 * @param savingYearRemainingNumberCheck
	 * @param compensatoryDisplayAtr
	 * @param compensatoryRemainingNumberCheck
	 * @param substitutionDisplayAtr
	 * @param substitutionRemainingNumberCheck
	 */
	public DisplayRestriction(int yearDisplayAtr, int yearRemainingNumberCheck, 
			int savingYearDisplayAtr, int savingYearRemainingNumberCheck,
			int compensatoryDisplayAtr, int compensatoryRemainingNumberCheck, 
			int substitutionDisplayAtr, int substitutionRemainingNumberCheck) {
		super();
		this.year = new LeaveHolidayRestriction(yearDisplayAtr, yearRemainingNumberCheck);
		this.savingYear = new LeaveHolidayRestriction(savingYearDisplayAtr, savingYearRemainingNumberCheck);
		this.compensatory = new LeaveHolidayRestriction(compensatoryDisplayAtr, compensatoryRemainingNumberCheck);
		this.substitution = new LeaveHolidayRestriction(substitutionDisplayAtr, substitutionRemainingNumberCheck);
	}
	
}
