package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SPRCheck {
	NOT_INSERT(0), 
	INSERT(1), 
	SHOW_CONFIRM(2);
	public int value;
}
