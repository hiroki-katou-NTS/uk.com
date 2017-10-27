package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 打刻組み合わせ区分
 *
 */
public enum AppStampCombinationAtr {
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
	HOLIDAY(6,"休出"),
	
	/**
	 * 臨時出勤
	 */
	EXTRAORDINARY_ATTENDANCE(7,"臨時出勤"),
	
	/**
	 * 臨時退勤
	 */
	EXTRAORDINARY_OFFICE_WORK(8,"臨時退勤"),
	
	/**
	 * 半休＋出勤
	 */
	HALF_HOLIDAY_AND_ATTENDANCE(9,"半休＋出勤"),
	
	/**
	 * 半休＋退勤
	 */
	HALF_HOLIDAY_AND_OFFICE_WORK(10,"半休＋退勤"),
	
	/**
	 * 半休＋応援出勤
	 */
	HALF_HOLIDAY_AND_SUPPORT_ATTENDANCE(11,"半休＋応援出勤"),
	
	/**
	 * 半休＋応援退勤
	 */
	HALF_HOLIDAY_AND_SUPPORT_OFFICE_WORK(12,"半休＋応援退勤"),
	
	/**
	 * フレックス＋出勤
	 */
	FLEX_AND_ATTENDANCE(13,"フレックス＋出勤"),
	
	/**
	 * フレックス＋退勤
	 */
	FLEX_AND_OFFICE_WORK(14,"フレックス＋退勤"),
	
	/**
	 * フレックス＋応援出勤
	 */
	FLEX_AND_SUPPORT_ATTENDANCE(15,"フレックス＋応援出勤"),
	
	/**
	 * フレックス＋応援退勤
	 */
	FLEX_AND_SUPPORT_OFFICE_WORK(16,"フレックス＋応援退勤"),
	
	/**
	 * 臨時＋応援出勤
	 */
	EXTRAORDINARY_AND_SUPPORT_ATTENDANCE(17,"臨時＋応援出勤");
	
	public final int value;
	
	public final String name;
	
	AppStampCombinationAtr(int value, String name){
		this.value = value;
		this.name = name;
	}
}
