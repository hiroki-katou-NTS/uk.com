package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.InputControlMonthly;

@Getter
@Setter
@NoArgsConstructor
public class DisplayAndInputMonthCmd {
	private int itemMonthlyId;
	
	private String itemDailyName;
	
	private int displayNumber;
	
	private int userCanUpdateAtr;
	
	private boolean toUse;
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	public DisplayAndInputMonthCmd(int itemMonthlyId, String itemDailyName, int displayNumber, int userCanUpdateAtr, boolean toUse, boolean youCanChangeIt, boolean canBeChangedByOthers) {
		super();
		this.itemMonthlyId = itemMonthlyId;
		this.itemDailyName = itemDailyName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.toUse = toUse;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
	}
	
	public static DisplayAndInputMonthly fromCommand(DisplayAndInputMonthCmd command) {
		return new DisplayAndInputMonthly(
				command.getItemMonthlyId(),
				command.isToUse(),
				new InputControlMonthly(
					command.isYouCanChangeIt(),
					command.isCanBeChangedByOthers()
						)
				);
	}
	
	
}
