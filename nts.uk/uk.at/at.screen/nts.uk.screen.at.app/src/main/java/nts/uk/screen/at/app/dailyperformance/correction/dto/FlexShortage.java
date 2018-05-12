package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.flex.BreakTimeDay;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexShortage {

	// フレックス不足時間
	private ItemValue value18;
	private ItemValue value21;
	private ItemValue value189;
	private ItemValue value190;
	private ItemValue value191;
	private boolean canflex;
	private BreakTimeDay breakTimeDay;
	private boolean showFlex;

	public FlexShortage(ItemValue value18, ItemValue value21, ItemValue value189, ItemValue value190, ItemValue value191) {
		this.value18 = value18;
		this.value21 = value21;
		this.value189 = value189;
		this.value190 = value190;
		this.value191 = value191;
	}
	
	public FlexShortage createCanFlex(boolean flex){
		this.canflex = flex;
		return this;
	}
	
	public FlexShortage createBreakTimeDay(BreakTimeDay breakTimeDay){
		this.breakTimeDay = breakTimeDay;
		return this;
	}
	
	public FlexShortage createShowFlex(boolean flex){
		this.showFlex = flex;
		return this;
	}
}
