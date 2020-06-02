package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

/**
 * @author loivt
 * 休暇申請の種類
 */
public enum HolidayType {
	
	/**
	 * 年次有休
	 */
	ANNUALPAIDLEAVE(0),
	
	/**
	 * 代休
	 */
	SUBSTITUTEHOLIDAY(1),
	
	/**
	 * 欠勤
	 */
	ABSENCE(2),
	/**
	 * 特別休暇
	 */
	SPECIALHOLIDAY(3),
	
	/**
	 * 積立年休
	 */
	YEARLYRESERVE(4),
	/**
	 * 休日
	 */
	HOLIDAY(5),
	/**
	 * 時間消化
	 */
	DIGESTIONTIME(6),
	
	/**
	 * 振休
	 */
	RESTTIME(7);
	
	public final int value;
		
	HolidayType(int value){
			this.value = value;
		}

}
