package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LimitOfOverTimeSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;

/**
 * 
 * @author nampt
 * 自動計算設定
 *
 */
@Getter
public class AutoCalculationSetting {
	
	//計算区分
	private AutoCalculationCategoryOutsideHours calculationAttr;
	
	//上限の設定
	private LimitOfOverTimeSetting upperLimitSetting;
}
