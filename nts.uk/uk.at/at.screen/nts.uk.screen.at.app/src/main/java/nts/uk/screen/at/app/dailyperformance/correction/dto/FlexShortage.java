package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.flex.BreakTimeDay;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexShortage {

	// フレックス不足時間
	private String value18;
	private String value21;
	private String value189;
	private String value190;
	private String value191;
	private boolean canflex;
	private BreakTimeDay breakTimeDay;
	private boolean showFlex;

	public FlexShortage(String value18, String value21, String value189, String value190, String value191) {
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
