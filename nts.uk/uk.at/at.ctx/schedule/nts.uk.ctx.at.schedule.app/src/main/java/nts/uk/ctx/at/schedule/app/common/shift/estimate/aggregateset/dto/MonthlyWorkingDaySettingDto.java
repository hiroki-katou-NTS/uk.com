package nts.uk.ctx.at.schedule.app.common.shift.estimate.aggregateset.dto;

import lombok.Data;

/**
 * The Class MonthlyWorkingDaySetting.
 */
@Data
public class MonthlyWorkingDaySettingDto {
	
	/** The half day atr. */
	private int halfDayAtr;
	
	/** The year hd atr. */
	private int yearHdAtr;
	
	/** The sphd atr. */
	private int sphdAtr;
	
	/** The havy hd atr. */
	private int havyHdAtr;
	
	/**
	 * Instantiates a new monthly working day setting.
	 *
	 * @param halfDayAtr the half day atr
	 * @param yearHdAtr the year hd atr
	 * @param sphdAtr the sphd atr
	 * @param havyHdAtr the havy hd atr
	 */
	public MonthlyWorkingDaySettingDto(int halfDayAtr, int yearHdAtr, int sphdAtr, int havyHdAtr){
		this.halfDayAtr = halfDayAtr;
		this.yearHdAtr = yearHdAtr;
		this.sphdAtr = sphdAtr;
		this.havyHdAtr = havyHdAtr;
	}
}
