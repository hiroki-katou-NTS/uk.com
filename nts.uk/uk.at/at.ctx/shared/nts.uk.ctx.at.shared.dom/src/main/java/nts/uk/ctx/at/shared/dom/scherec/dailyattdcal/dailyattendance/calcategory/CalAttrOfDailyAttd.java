package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * 日別勤怠の計算区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.計算区分.日別勤怠の計算区分
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class CalAttrOfDailyAttd implements DomainObject {
		
		//フレックス超過時間: フレックス超過時間の自動計算設定
		private AutoCalFlexOvertimeSetting flexExcessTime;
		
		//加給: 加給の自動計算設定
		private AutoCalRaisingSalarySetting rasingSalarySetting;
		
		//休出時間: 休出時間の自動計算設定
		private AutoCalRestTimeSetting holidayTimeSetting;
		
		//残業時間: 残業時間の自動計算設定
		private AutoCalOvertimeSetting overtimeSetting;
		
		//遅刻早退: 遅刻早退の自動計算設定
		private AutoCalcOfLeaveEarlySetting leaveEarlySetting;
		
		//乖離時間: 乖離時間の自動計算設定
		private AutoCalcSetOfDivergenceTime divergenceTime;

		public CalAttrOfDailyAttd(AutoCalFlexOvertimeSetting flexExcessTime,
				AutoCalRaisingSalarySetting rasingSalarySetting, AutoCalRestTimeSetting holidayTimeSetting,
				AutoCalOvertimeSetting overtimeSetting, AutoCalcOfLeaveEarlySetting leaveEarlySetting,
				AutoCalcSetOfDivergenceTime divergenceTime) {
			super();
			this.flexExcessTime = flexExcessTime;
			this.rasingSalarySetting = rasingSalarySetting;
			this.holidayTimeSetting = holidayTimeSetting;
			this.overtimeSetting = overtimeSetting;
			this.leaveEarlySetting = leaveEarlySetting;
			this.divergenceTime = divergenceTime;
		} 
		
	public CalAttrOfDailyAttd reCreate(AutoCalAtrOvertime atr) {
		return new CalAttrOfDailyAttd(this.flexExcessTime, this.rasingSalarySetting,
				this.holidayTimeSetting,
				this.overtimeSetting.changeNormalAutoCalcSetting(atr),
				this.leaveEarlySetting, this.divergenceTime

		);
		}
	
	// Đang để tạm lấy giá trị default để khỏi oẳng xử lý của Thanh dz
	public static CalAttrOfDailyAttd defaultData() {
		AutoCalFlexOvertimeSetting flexExcessTime = new AutoCalFlexOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)));
		AutoCalRaisingSalarySetting rasingSalarySetting = new AutoCalRaisingSalarySetting(false, false);
		AutoCalRestTimeSetting holidayTimeSetting = new AutoCalRestTimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)));
		AutoCalOvertimeSetting overtimeSetting = new AutoCalOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), 
				new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), 
				new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), 
				new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), 
				new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)), 
				new AutoCalSetting(TimeLimitUpperLimitSetting.valueOf(0), AutoCalAtrOvertime.valueOf(0)));
		AutoCalcOfLeaveEarlySetting leaveEarlySetting = new AutoCalcOfLeaveEarlySetting(false, false);
		AutoCalcSetOfDivergenceTime divergenceTime = new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.valueOf(0));
		
		return new CalAttrOfDailyAttd(flexExcessTime, rasingSalarySetting, holidayTimeSetting, overtimeSetting, leaveEarlySetting, divergenceTime);
	}
		
}
