package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DisplayAndInputControl;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.InputControlOfAttendanceItem;

@Getter
@Setter
@NoArgsConstructor
public class DisplayAndInputControlCmd {
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	private String itemDailyName;
	
	private int displayNumber;
	
	private int userCanUpdateAtr;
	
	/**利用する*/
	private boolean toUse;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;

	public DisplayAndInputControlCmd(int itemDailyID, String itemDailyName, int displayNumber, int userCanUpdateAtr, boolean toUse, boolean canBeChangedByOthers, boolean youCanChangeIt) {
		super();
		this.itemDailyID = itemDailyID;
		this.itemDailyName = itemDailyName;
		this.displayNumber = displayNumber;
		this.userCanUpdateAtr = userCanUpdateAtr;
		this.toUse = toUse;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.youCanChangeIt = youCanChangeIt;
	}


	public static DisplayAndInputControl fromCommand(DisplayAndInputControlCmd command) {
		return new DisplayAndInputControl(
				command.getItemDailyID(),
				command.isToUse(),
				new InputControlOfAttendanceItem(
					command.isCanBeChangedByOthers(),
					command.isYouCanChangeIt())
				);
	}
	

	
	
}
