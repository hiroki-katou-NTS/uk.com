package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.calcategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcSetOfDivergenceTime;
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
		
		
}
