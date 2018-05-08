package nts.uk.ctx.at.record.dom.workrecord.operationsetting.old;

public class LeaveHolidayRestriction {

	/**
	 * 表示区分
	 */
	private Boolean displayAtr;

	/**
	 * 残数をチェックする
	 */
	private Boolean remainingNumberCheck;

	public LeaveHolidayRestriction(Boolean displayAtr, Boolean remainingNumberCheck) {
		this.displayAtr = displayAtr;
		this.remainingNumberCheck = remainingNumberCheck;
		this.validate();
	}

	public LeaveHolidayRestriction(int displayAtr, int remainingNumberCheck) {
		this.displayAtr = toBooleanValue(displayAtr);
		this.remainingNumberCheck = toBooleanValue(remainingNumberCheck);
		this.validate();
	}

	private void validate() {
		if (!this.displayAtr) {
			this.remainingNumberCheck = false;
		}
	}

	private Boolean toBooleanValue(int decimalNumber) {
		if (decimalNumber == 0) {
			return false;
		} else if (decimalNumber == 1) {
			return true;
		} else {
			return null;
		}
	}

	public boolean isDisplayAtr() {
		return displayAtr;
	}

	public boolean isRemainingNumberCheck() {
		return remainingNumberCheck;
	}
}
