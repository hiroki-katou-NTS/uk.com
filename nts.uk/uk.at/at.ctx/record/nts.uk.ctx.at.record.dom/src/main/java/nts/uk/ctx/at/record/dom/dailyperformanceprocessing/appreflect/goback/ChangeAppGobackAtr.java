package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback;

import lombok.AllArgsConstructor;
/**
 * 直行直帰申請．勤務を変更する
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ChangeAppGobackAtr {
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
