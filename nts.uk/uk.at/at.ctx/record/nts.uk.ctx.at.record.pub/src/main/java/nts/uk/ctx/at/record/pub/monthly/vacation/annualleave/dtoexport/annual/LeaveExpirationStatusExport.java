package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//休暇期限切れ状態
public enum LeaveExpirationStatusExport {

	/**
	 *  使用可能
	 */
	AVAILABLE(1),

	/**
	 *  期限切れ
	 */
	EXPIRED(0);

	public final int value;

	public boolean IsAVAILABLE() {
		return this.value == LeaveExpirationStatusExport.AVAILABLE.value;
	}

}
