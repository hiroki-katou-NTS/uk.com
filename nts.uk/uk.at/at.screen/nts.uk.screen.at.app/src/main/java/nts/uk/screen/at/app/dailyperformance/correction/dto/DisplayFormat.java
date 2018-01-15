package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayFormat {
	/*個人別*/
	Individual(0),
	/*日付別*/
	ByDate(1),
	/* エラーアラーム*/
	ErrorAlarm(2);
	
	public final int value;
}
