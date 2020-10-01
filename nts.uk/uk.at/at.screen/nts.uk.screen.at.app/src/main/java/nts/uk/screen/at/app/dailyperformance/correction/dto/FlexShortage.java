package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthParent;
import nts.uk.screen.at.app.dailyperformance.correction.flex.BreakTimeDay;
import nts.uk.screen.at.app.dailyperformance.correction.flex.CalcFlexDto;

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
	private DPMonthParent monthParent;
	private boolean retiredFlag;
	private CalcFlexDto calc;
	private String redConditionMessage;
	private String messageNotForward;

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
	
	public FlexShortage createMonthParent(DPMonthParent dPMonthParent){
		this.monthParent = dPMonthParent;
		return this;
	}
	
	public FlexShortage createRetiredFlag(boolean flag){
		this.retiredFlag = flag;
		return this;
	}
	
	public FlexShortage createCalcFlex(CalcFlexDto calc){
		this.calc = calc;
		return this;
	}
	
	public FlexShortage createRedConditionMessage(String condition){
		this.redConditionMessage = condition;
		return this;
	}
	
	public FlexShortage createNotForward(String message){
		this.messageNotForward = message;
		return this;
	}
}
