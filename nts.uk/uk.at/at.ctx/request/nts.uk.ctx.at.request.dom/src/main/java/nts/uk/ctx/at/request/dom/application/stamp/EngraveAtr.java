package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author hoangnd
 * Refactor4
 *UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請
 */

//打刻区分
public enum EngraveAtr {
	
	/**
	 * 出勤
	 */
	ATTENDANCE(0,"出勤"),
	
	/**
	 * 退勤
	 */
	OFFICE_WORK(1,"退勤"),
	
	/**
	 * 退勤（残業）
	 */
	OVERTIME(2,"退勤（残業）"),
	
	/**
	 * 外出
	 */
	GO_OUT(3,"外出"),
	
	/**
	 * 戻り
	 */
	RETURN(4,"戻り"),
	
	/**
	 * 早出
	 */
	EARLY(5,"早出"),
	
	/**
	 * 休出
	 */
	HOLIDAY(6,"休出");
	

	public final int value;
	
	public final String name;
	
	EngraveAtr(int value, String name){
		this.value = value;
		this.name = name;
	}
}
