package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 所定労働控除時間
 * @author keisuke_hoshina
 *
 */
@Getter
@NoArgsConstructor
public class StatutoryDeductionForFlex {
	/** 実働用 */
	private AttendanceTime forActualWork = AttendanceTime.ZERO;
	/** 割増用 */
	private AttendanceTime forPremium = AttendanceTime.ZERO;
	
	/**
	 * Constructor 
	 */
	public StatutoryDeductionForFlex(AttendanceTime forActualWork, AttendanceTime forPremium) {
		super();
		this.forActualWork = forActualWork;
		this.forPremium = forPremium;
	}
	
	/**
	 * 加算する
	 * @param other 勤怠時間
	 */
	public void add(AttendanceTime other){
		this.forActualWork = this.forActualWork.addMinutes(other.valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.valueAsMinutes());
	}
	
	/**
	 * 加算する
	 * @param other 設定別控除時間
	 */
	public void add(DeductTimeEachSet other){
		this.forActualWork = this.forActualWork.addMinutes(other.getForActualWork().valueAsMinutes());
		this.forPremium = this.forPremium.addMinutes(other.getForPremium().valueAsMinutes());
	}
}
