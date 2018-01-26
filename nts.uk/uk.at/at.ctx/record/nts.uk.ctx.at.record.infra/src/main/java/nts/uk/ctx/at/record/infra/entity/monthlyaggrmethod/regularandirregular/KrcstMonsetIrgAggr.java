package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.regularandirregular;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 変形労働時間勤務の月の集計設定　（共通）
 * @author shuichi_ishida
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KrcstMonsetIrgAggr implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 残業時間を変形基準内残業とする */
	@Column(name = "TO_OVERTIME_WIT_IRGCRI")
	public int toOverTimeWithinIrregularCriteria;
	
	/** 就業時間を基準時間外就業時間とする */
	@Column(name = "TO_WORKTIME_OUT_CRI")
	public int toWorkTimeOutsideCriteria;
	
	/** 残業時間の扱い01 */
	@Column(name = "TREAT_OVER_TIME_01")
	public int treatOverTime01;
	/** 残業時間の扱い02 */
	@Column(name = "TREAT_OVER_TIME_02")
	public int treatOverTime02;
	/** 残業時間の扱い03 */
	@Column(name = "TREAT_OVER_TIME_03")
	public int treatOverTime03;
	/** 残業時間の扱い04 */
	@Column(name = "TREAT_OVER_TIME_04")
	public int treatOverTime04;
	/** 残業時間の扱い05 */
	@Column(name = "TREAT_OVER_TIME_05")
	public int treatOverTime05;
	/** 残業時間の扱い06 */
	@Column(name = "TREAT_OVER_TIME_06")
	public int treatOverTime06;
	/** 残業時間の扱い07 */
	@Column(name = "TREAT_OVER_TIME_07")
	public int treatOverTime07;
	/** 残業時間の扱い08 */
	@Column(name = "TREAT_OVER_TIME_08")
	public int treatOverTime08;
	/** 残業時間の扱い09 */
	@Column(name = "TREAT_OVER_TIME_09")
	public int treatOverTime09;
	/** 残業時間の扱い10 */
	@Column(name = "TREAT_OVER_TIME_10")
	public int treatOverTime10;
	
	/** 休出時間の扱い01 */
	@Column(name = "TREAT_HDWK_TIME_01")
	public int treatHolidayWorkTime01;
	/** 休出時間の扱い02 */
	@Column(name = "TREAT_HDWK_TIME_02")
	public int treatHolidayWorkTime02;
	/** 休出時間の扱い03 */
	@Column(name = "TREAT_HDWK_TIME_03")
	public int treatHolidayWorkTime03;
	/** 休出時間の扱い04 */
	@Column(name = "TREAT_HDWK_TIME_04")
	public int treatHolidayWorkTime04;
	/** 休出時間の扱い05 */
	@Column(name = "TREAT_HDWK_TIME_05")
	public int treatHolidayWorkTime05;
	/** 休出時間の扱い06 */
	@Column(name = "TREAT_HDWK_TIME_06")
	public int treatHolidayWorkTime06;
	/** 休出時間の扱い07 */
	@Column(name = "TREAT_HDWK_TIME_07")
	public int treatHolidayWorkTime07;
	/** 休出時間の扱い08 */
	@Column(name = "TREAT_HDWK_TIME_08")
	public int treatHolidayWorkTime08;
	/** 休出時間の扱い09 */
	@Column(name = "TREAT_HDWK_TIME_09")
	public int treatHolidayWorkTime09;
	/** 休出時間の扱い10 */
	@Column(name = "TREAT_HDWK_TIME_10")
	public int treatHolidayWorkTime10;
}
