package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Value;

@Value
public class OptionalWidgetInfoDTO {
	
	/** OverTime Work Number 残業指示件数 */
	private int overTime = 0;
	
	/** Instructions Holiday Number 休出指示件数 */
	private int holidayInstruction = 0;
	
	/** Approved Number 承認された件数 */
	private int approved = 0;
	
	/** Approved Number 承認された件数 */
	private int unApproved = 0;
	
	/** Denied Number 否認された件数 */
	private int denied = 0;

	/** Remand Number 差し戻し件数 */
	private int remand = 0;
	
}
