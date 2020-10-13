package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * 
 * @author nampt
 * 日別実績の計算区分 - root
 *
 */
@Getter
@NoArgsConstructor
public class CalAttrOfDailyPerformance extends AggregateRoot {
	
	//社員ID: 社員ID
	private String employeeId;
	
	//年月日: 年月日
	private GeneralDate ymd;
	
	//計算区分
	private CalAttrOfDailyAttd calcategory;
	
//	/**
//	 * 計算区分を全て「する」に変更する
//	 */
//	public static CalAttrOfDailyPerformance turnAllAtrTrue() {
//		//残業時間の自動計算設定を打刻から計算するに
//		this.overtimeSetting
//		//フレックス
//		this.flexExcessTime
//		//休出
//		this.holidayTimeSetting
//		
//	} 

	public CalAttrOfDailyPerformance reCreate(AutoCalAtrOvertime atr) {
		return new CalAttrOfDailyPerformance(this.employeeId,
											 this.ymd,
											 new CalAttrOfDailyAttd(
														 this.calcategory.getFlexExcessTime(),
														 this.calcategory.getRasingSalarySetting(),
														 this.calcategory.getHolidayTimeSetting(),
														 this.calcategory.getOvertimeSetting().changeNormalAutoCalcSetting(atr),
														 this.calcategory.getLeaveEarlySetting(),
														 this.calcategory.getDivergenceTime()
													 )
											 );
	}

public CalAttrOfDailyPerformance(String employeeId, GeneralDate ymd, AutoCalFlexOvertimeSetting flexExcessTime,
		AutoCalRaisingSalarySetting rasingSalarySetting, AutoCalRestTimeSetting holidayTimeSetting,
		AutoCalOvertimeSetting overtimeSetting, AutoCalcOfLeaveEarlySetting leaveEarlySetting,
		AutoCalcSetOfDivergenceTime divergenceTime) {
	super();
	this.employeeId = employeeId;
	this.ymd = ymd;
	this.calcategory = new CalAttrOfDailyAttd(flexExcessTime, rasingSalarySetting, holidayTimeSetting, overtimeSetting, leaveEarlySetting, divergenceTime);
}

public CalAttrOfDailyPerformance(String employeeId, GeneralDate ymd, CalAttrOfDailyAttd calcategory) {
	super();
	this.employeeId = employeeId;
	this.ymd = ymd;
	this.calcategory = calcategory;
}


	
}
