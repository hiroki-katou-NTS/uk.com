package nts.uk.ctx.at.record.pub.dailyperform.appreflect.goback;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ChangeAppGobackPubAtr {
	/**
	 * 勤務を変更しない
	 */
	NOTCHANGE(0,"勤務を変更しない"),
	/**
	 * 勤務を変更する
	 */
	CHANGE(1, "勤務を変更しない");
	
	public int value;
	
	public String name;
}
