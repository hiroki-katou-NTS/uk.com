package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LimitOfOverTimeSetting;

/**
 * 
 * @author nampt
 * 自動計算設定
 *
 */
@Getter
public class AutoCalculationSetting {
	
	//計算区分
	private AutoCalculationSetting calculationAttr;
	
	//上限の設定
	private LimitOfOverTimeSetting upperLimitSetting;
}
