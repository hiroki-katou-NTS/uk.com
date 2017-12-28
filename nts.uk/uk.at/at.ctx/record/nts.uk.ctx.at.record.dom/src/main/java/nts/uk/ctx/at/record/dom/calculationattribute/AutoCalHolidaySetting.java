package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author nampt
 * 休出時間の自動計算設定
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoCalHolidaySetting {
	
	//休出時間
	private AutoCalculationSetting holidayWorkTime;
	
	//休出深夜時間
	private AutoCalculationSetting lateNightTime;

}
