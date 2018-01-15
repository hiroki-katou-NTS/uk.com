package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 休暇申請の種類 */
public enum VacationAppType {
	/** 0:積立年休 */
	YEARLYRESERVED(0),
	/** 1:特別休暇 */
	SPECIALHOLIDAY(1),
	/** 2:欠勤 */
	ABSENCE(2),
	/** 3:時間消化 */
	TIMEDIGESTION(3),
	/** 4:振休 */
	PAUSE(4),
	/** 5:年次有休 */
	ANNUALPAID(5),
	/** 6:休日 */
	HOLIDAY(6),
	/** 7:代休 */
	SUBSTITUTEHOLIDAY(7);

	public final int value;

	VacationAppType(int value) {
		this.value = value;
	}

}
