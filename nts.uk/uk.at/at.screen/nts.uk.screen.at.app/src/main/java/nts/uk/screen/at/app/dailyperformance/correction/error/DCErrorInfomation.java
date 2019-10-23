package nts.uk.screen.at.app.dailyperformance.correction.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DCErrorInfomation {
	
	NORMAL(0, "NORMAL"),

	APPROVAL_NOT_EMP(1, "Msg_916"),
	
	ITEM_HIDE_ALL(2, "Msg_1452");

	public int value;

	public String name;
}
