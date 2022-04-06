package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculationAndMinus;

/**
 * フレックス合計時間
 */
@Getter
public class FlexTimeTotalTimeMonth implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** フレックス時間 */
	private TimeMonthWithCalculationAndMinus flexTime;
	/** 法定内フレックス時間 */
	private AttendanceTimeMonth legalFlexTime;
	/** 法定外フレックス時間 */
	private AttendanceTimeMonth illegalFlexTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeTotalTimeMonth(){
		this.flexTime = TimeMonthWithCalculationAndMinus.ofSameTime(0);
		this.legalFlexTime = new AttendanceTimeMonth(0);
		this.illegalFlexTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param legalTime 法定内フレックス時間
	 * @param illegalTime 法定外フレックス時間
	 * @return 当月フレックス時間
	 */
	public static FlexTimeTotalTimeMonth of(
			TimeMonthWithCalculationAndMinus flexTime,
			AttendanceTimeMonth legalTime,
			AttendanceTimeMonth illegalTime) {
		
		val domain = new FlexTimeTotalTimeMonth();
		domain.flexTime = flexTime;
		domain.legalFlexTime = legalTime;
		domain.illegalFlexTime = illegalTime;
		return domain;
	}
	
	/** フレックス時間をセットする */
	public void setFlex(TimeMonthWithCalculationAndMinus flexTime) {
		
		this.flexTime = flexTime;
	}
	
	public void calcIllegalWith(AttendanceTimeMonthWithMinus flexTime, AttendanceTimeMonth monthlyLegalTime) {
		
		if (monthlyLegalTime.valueAsMinutes() >= flexTime.valueAsMinutes()) {
			this.allFlexAsLegal(new AttendanceTimeMonth(flexTime.valueAsMinutes()));
		} else {
			this.legalFlexTime = new AttendanceTimeMonth(monthlyLegalTime.valueAsMinutes());
			this.illegalFlexTime = new AttendanceTimeMonth(flexTime.valueAsMinutes() - monthlyLegalTime.valueAsMinutes());
		}
	}
	
	public void addFlexCalcTime(AttendanceTimeMonthWithMinus flexTime) {
		
		this.flexTime = this.flexTime.addMinutes(0, flexTime.valueAsMinutes());
	}
	
	public void addFlexTime(AttendanceTimeMonthWithMinus flexTime) {
		
		this.flexTime = this.flexTime.addMinutes(flexTime.valueAsMinutes(), 0);
	}
	
	public void addFlexIllegalTime(AttendanceTimeMonth flexTime) {
		
		this.illegalFlexTime = this.illegalFlexTime.addMinutes(flexTime.valueAsMinutes());
	}
	
	public void addFlexLegalTime(AttendanceTimeMonth flexTime) {
		
		this.legalFlexTime = this.legalFlexTime.addMinutes(flexTime.valueAsMinutes());
	}
	
	public void clearFlexTime() {
		this.flexTime = new TimeMonthWithCalculationAndMinus(new AttendanceTimeMonthWithMinus(0), this.flexTime.getCalcTime());
	}
	
	public void clearFlexCalcTime() {
		this.flexTime = new TimeMonthWithCalculationAndMinus(this.flexTime.getTime(), new AttendanceTimeMonthWithMinus(0));
	}
	
	public void allFlexAsIllegal(AttendanceTimeMonth time) {
		this.illegalFlexTime = time;
		this.legalFlexTime = new AttendanceTimeMonth(0);
	}
	
	public void allFlexAsLegal(AttendanceTimeMonth time) {
		this.legalFlexTime = time;
		this.illegalFlexTime = new AttendanceTimeMonth(0);
	}
	
	public void setFlexIllegal(AttendanceTimeMonth time) {
		this.illegalFlexTime = time;
	}
	
	public void setFlexLegal(AttendanceTimeMonth time) {
		this.legalFlexTime = time;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexTimeTotalTimeMonth target){
		
		this.flexTime = this.flexTime.addMinutes(target.flexTime.getTime().valueAsMinutes(), target.flexTime.getCalcTime().valueAsMinutes());
		this.legalFlexTime = this.legalFlexTime.addMinutes(target.legalFlexTime.v());
		this.illegalFlexTime = this.illegalFlexTime.addMinutes(target.illegalFlexTime.v());
	}
}
