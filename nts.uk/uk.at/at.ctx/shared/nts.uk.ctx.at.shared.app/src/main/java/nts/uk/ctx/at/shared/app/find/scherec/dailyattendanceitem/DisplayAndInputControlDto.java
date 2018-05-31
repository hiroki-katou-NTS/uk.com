package nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DisplayAndInputControl;

@Getter
@Setter
@NoArgsConstructor
public class DisplayAndInputControlDto {
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**利用する*/
	private boolean toUse;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;
	
	public DisplayAndInputControlDto(int itemDailyID, boolean toUse, boolean canBeChangedByOthers, boolean youCanChangeIt) {
		super();
		this.itemDailyID = itemDailyID;
		this.toUse = toUse;
		this.canBeChangedByOthers = canBeChangedByOthers;
		this.youCanChangeIt = youCanChangeIt;
	}
	
	public static DisplayAndInputControlDto fromDomain( DisplayAndInputControl domain) {
		return new DisplayAndInputControlDto(
				domain.getItemDailyID(),
				domain.isToUse(),
				domain.getInputControl().isCanBeChangedByOthers(),
				domain.getInputControl().isYouCanChangeIt()
				);
	}
	
}
