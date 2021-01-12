package nts.uk.ctx.at.request.dom.application.overtime.time36;
/**
 * エラー情報
 * @author Doan Duy Hung
 *
 */
public enum Time36ErrorInfo {
	/**
	 * 月間エラー
	 */
	MONTHLY_ERROR(0,"月間エラー"),
	
	/**
	 * 年間エラー
	 */
	ANNUAL_ERROR(1,"年間エラー"),
	
	/**
	 * 上限月間時間エラー
	 */
	MAX_MONTH_TIME_ERROR(2,"上限月間時間エラー"),
	
	/**
	 * 上限複数月平均時間エラー
	 */
	AVERAGE_MONTH_TIME_ERROR(3,"上限複数月平均時間エラー"),
	
	/**
	 * 上限年間時間エラー
	 */
	MAX_YEAR_TIME_ERROR(4,"上限年間時間エラー");
	
	public final int value;
	
	public final String name;
	
	Time36ErrorInfo(int value, String name){
		this.value = value;
		this.name = name;
	}
}
