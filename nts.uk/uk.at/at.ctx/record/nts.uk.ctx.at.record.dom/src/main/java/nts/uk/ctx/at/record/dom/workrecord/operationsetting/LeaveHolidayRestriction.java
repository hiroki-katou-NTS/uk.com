package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

public class LeaveHolidayRestriction {

	/**
	 * 表示区分
	 */
	private boolean displayAtr;

	/**
	 * 残数をチェックする
	 */
	private boolean remainingNumberCheck;

	public LeaveHolidayRestriction(boolean displayAtr, boolean remainingNumberCheck) {
		if (!displayAtr) {
			remainingNumberCheck = false;
		}
		this.displayAtr = displayAtr;
		this.remainingNumberCheck = remainingNumberCheck;
	}

	public boolean isDisplayAtr() {
		return displayAtr;
	}

	public boolean isRemainingNumberCheck() {
		return remainingNumberCheck;
	}
}
