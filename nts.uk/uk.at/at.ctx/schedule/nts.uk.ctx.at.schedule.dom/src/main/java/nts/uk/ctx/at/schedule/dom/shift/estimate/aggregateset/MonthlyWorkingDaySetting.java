package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class MonthlyWorkingDaySetting.
 */
@Getter
@Setter
// 月間勤務日数集計設定
public class MonthlyWorkingDaySetting extends DomainObject{
	
	/** The half day atr. */
	// 半日カウント区分
	private HalfDayWorkCountCat halfDayAtr;
	
	/** The year hd atr. */
	// 年休カウント区分
	private NotUseAtr yearHdAtr;
	
	/** The sphd atr. */
	// 特休カウント区分
	private NotUseAtr sphdAtr;
	
	/** The havy hd atr. */
	// 積休カウント区分
	private NotUseAtr havyHdAtr;
	
	/**
	 * Instantiates a new monthly working day setting.
	 *
	 * @param halfDayAtr the half day atr
	 * @param yearHdAtr the year hd atr
	 * @param sphdAtr the sphd atr
	 * @param havyHdAtr the havy hd atr
	 */
	public MonthlyWorkingDaySetting(HalfDayWorkCountCat halfDayAtr, NotUseAtr yearHdAtr, NotUseAtr sphdAtr, NotUseAtr havyHdAtr){
		this.halfDayAtr = halfDayAtr;
		this.yearHdAtr = yearHdAtr;
		this.sphdAtr = sphdAtr;
		this.havyHdAtr = havyHdAtr;
	}
}
