package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author nampt
 * 日別実績の計算区分 - root
 *
 */
@Getter
public class CalAttrOfDailyPerformance extends AggregateRoot {
	
	//フレックス超過時間
	private AutoCalculationSetting flexExcessTime;
	
	//加給
	private AutoCalRasingSalarySetting rasingSalarySetting;
	
	//休出時間
	private AutoCalHolidaySetting holidayTimeSetting;
	
	//残業時間
	
}
