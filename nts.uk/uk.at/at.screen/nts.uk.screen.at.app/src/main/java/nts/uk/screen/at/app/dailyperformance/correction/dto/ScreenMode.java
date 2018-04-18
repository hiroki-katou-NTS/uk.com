package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScreenMode {
	
	NORMAL(0, "通常"),
	APPROVAL(1, "承認");
	
	public int value;
	
	public String name;
}
