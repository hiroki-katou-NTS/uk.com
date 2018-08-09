package nts.uk.ctx.at.request.app.command.setting.company.request.apptypesetting;

import lombok.Getter;

@Getter
public class DisplayReasonCmd {
	/**
	 * 休暇申請の種類
	 */
	private int appType;
	
	/**
	 * 定型理由の表示
	 */
	private int displayFixedReason;
	
	/**
	 * 申請理由の表示
	 */
	private int displayAppReason;
}
