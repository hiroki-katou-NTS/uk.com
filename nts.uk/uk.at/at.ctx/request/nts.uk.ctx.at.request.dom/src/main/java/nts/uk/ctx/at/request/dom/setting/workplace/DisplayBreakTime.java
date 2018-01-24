package nts.uk.ctx.at.request.dom.setting.workplace;
/**
 * 退勤時刻初期表示区分
 * @author Doan Duy Hung
 *
 */
public enum DisplayBreakTime {
	
	/**
	 * 勤務時間の退勤時刻にシステム時刻を入れる
	 */
	SYSTEM_TIME(0,"勤務時間の退勤時刻にシステム時刻を入れる"),
	
	/**
	 * 勤務時間の退勤時刻にブランクを入れる
	 */
	BLANK(1,"勤務時間の退勤時刻にブランクを入れる");
	
	public final int value;
	
	public final String name;
	
	DisplayBreakTime(int value, String name){
		this.value = value;
		this.name = name;
	}
	
}
