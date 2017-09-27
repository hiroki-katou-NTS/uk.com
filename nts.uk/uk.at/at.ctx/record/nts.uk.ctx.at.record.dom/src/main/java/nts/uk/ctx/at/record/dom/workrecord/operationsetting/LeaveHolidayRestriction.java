package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import java.math.BigDecimal;

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

	public LeaveHolidayRestriction(BigDecimal displayAtr, BigDecimal remainingNumberCheck) {
		this.displayAtr = toBooleanValue(displayAtr);
		this.remainingNumberCheck = toBooleanValue(remainingNumberCheck);
		this.validate();
	}

	private void validate() {
		if (!this.displayAtr) {
			this.remainingNumberCheck = false;
		}
	}

	private Boolean toBooleanValue(BigDecimal decimalNumber) {
		if (decimalNumber.intValue() == 0) {
			return false;
		} else if (decimalNumber.intValue() == 1) {
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
