package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 休出時間の自動計算設定
 *
 */
@Getter
public class AutoCalHolidaySetting {
	
	//休出時間
	private AutoCalculationSetting holidayWorkTime;
	
	//休出深夜時間
	private AutoCalculationSetting lateNightTime;

}
