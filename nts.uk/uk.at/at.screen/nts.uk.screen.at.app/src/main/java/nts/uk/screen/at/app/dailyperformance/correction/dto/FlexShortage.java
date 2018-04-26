package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.flex.BreakTimeDay;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexShortage {
	//フレックス不足時間
	private String value18;
	private String value21;
	private String value189;
	private String value190;
	private String value191;
	private BreakTimeDay breakTimeDay;
	private boolean canflex;
}
