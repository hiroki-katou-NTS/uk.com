package nts.uk.ctx.at.shared.pub.worktime.predset;

/**
 * The Interface PredetemineTimeSettingPub.
 */
public interface PredetemineTimeSettingPub {
	
	/**
	 * Checks if is working twice.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return true, if is working twice
	 */
	// 2回勤務かどうかの判断処理
	boolean IsWorkingTwice(String companyId, String workTimeCode);
}
