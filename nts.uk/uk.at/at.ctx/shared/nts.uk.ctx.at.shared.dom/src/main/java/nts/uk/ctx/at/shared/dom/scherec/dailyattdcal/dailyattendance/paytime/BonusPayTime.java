package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;

/**
 * 加給時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class BonusPayTime {
	//加給項目ＮＯ
	private int BonusPayTimeItemNo;
	//加給時間
	private AttendanceTime bonusPayTime;
	//所定内加給
	private TimeWithCalculation withinBonusPay;
	//所定外加給
	private TimeWithCalculation excessBonusPayTime;
	
	/**
	 * Constructor 
	 */
	public BonusPayTime(int bonusPayTimeItemNo, AttendanceTime bonusPayTime, TimeWithCalculation withinBonusPay,
			TimeWithCalculation excessBonusPayTime) {
		super();
		BonusPayTimeItemNo = bonusPayTimeItemNo;
		this.bonusPayTime = bonusPayTime;
		this.withinBonusPay = withinBonusPay;
		this.excessBonusPayTime = excessBonusPayTime;
	}
	
	public void controlUpperTime(AttendanceTime upperTime) {
		this.bonusPayTime = this.bonusPayTime.greaterThan(upperTime)?upperTime:this.bonusPayTime;
		this.withinBonusPay = TimeWithCalculation.createTimeWithCalculation(this.withinBonusPay.getTime().greaterThan(upperTime)?upperTime:this.withinBonusPay.getTime(),
																			this.withinBonusPay.getCalcTime().greaterThan(upperTime)?upperTime:this.withinBonusPay.getCalcTime());
		this.excessBonusPayTime = TimeWithCalculation.createTimeWithCalculation(this.excessBonusPayTime.getTime().greaterThan(upperTime)?upperTime:this.excessBonusPayTime.getTime(),
																				this.excessBonusPayTime.getCalcTime().greaterThan(upperTime)?upperTime:this.excessBonusPayTime.getCalcTime());
	}

	public void replaceValueByPClogInfo(AttendanceTime bonusPayTime,AttendanceTime withinCalcTime, AttendanceTime excessCalcTime) {
		//加給(属性が変更されたら対応)
		this.withinBonusPay.replaceCalcTime(withinCalcTime);
		this.excessBonusPayTime.replaceCalcTime(excessCalcTime);
	}


}
