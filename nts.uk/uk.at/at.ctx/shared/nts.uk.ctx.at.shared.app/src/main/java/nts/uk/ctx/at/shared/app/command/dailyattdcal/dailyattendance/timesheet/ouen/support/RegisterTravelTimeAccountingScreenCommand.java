package nts.uk.ctx.at.shared.app.command.dailyattdcal.dailyattendance.timesheet.ouen.support;

import lombok.Getter;

@Getter
public class RegisterTravelTimeAccountingScreenCommand {
	/** 利用する */
	private Integer isUse;
	
	/** 移動時間の計上先 */
	private Integer accountingOfMoveTime;

}
