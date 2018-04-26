package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import nts.arc.time.GeneralDate;

public class AnnLeaveOfThisMonth {
	
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 付与日数
	 */
	private Double grantDays;
	
	/**
	 * 月初残日数
	 */
	private Double firstMonthRemNumDays;
	
	/**
	 * 月初残時間
	 */
	private Double firstMonthRemNumMinutes;
	
	/**
	 * 使用日数
	 */
	private Double usedDays;
	
	/**
	 * 使用時間
	 */
	private int usedMinutes;
	
	/**
	 * 残日数
	 */
	private Double remainDays;
	
	/**
	 * 残時間
	 */
	private int remainMinutes;

}
