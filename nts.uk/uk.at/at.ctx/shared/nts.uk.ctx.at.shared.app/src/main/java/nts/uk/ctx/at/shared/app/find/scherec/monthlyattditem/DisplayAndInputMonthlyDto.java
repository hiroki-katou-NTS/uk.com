package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.DisplayAndInputControlDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DisplayAndInputControl;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.DisplayAndInputMonthly;

@Getter
@Setter
@NoArgsConstructor
public class DisplayAndInputMonthlyDto {

	private int itemMonthlyId;
	
	private boolean toUse;
	
	/**本人が変更できる*/
	private boolean youCanChangeIt;
	/**他人が変更できる*/
	private boolean canBeChangedByOthers;
	public DisplayAndInputMonthlyDto(int itemMonthlyId, boolean toUse, boolean youCanChangeIt, boolean canBeChangedByOthers) {
		super();
		this.itemMonthlyId = itemMonthlyId;
		this.toUse = toUse;
		this.youCanChangeIt = youCanChangeIt;
		this.canBeChangedByOthers = canBeChangedByOthers;
	}
	
	public static DisplayAndInputMonthlyDto fromDomain( DisplayAndInputMonthly domain) {
		return new DisplayAndInputMonthlyDto(
				domain.getItemMonthlyId(),
				domain.isToUse(),
				domain.getInputControlMonthly().isYouCanChangeIt(),
				domain.getInputControlMonthly().isCanBeChangedByOthers()
				);
	}
	
}
