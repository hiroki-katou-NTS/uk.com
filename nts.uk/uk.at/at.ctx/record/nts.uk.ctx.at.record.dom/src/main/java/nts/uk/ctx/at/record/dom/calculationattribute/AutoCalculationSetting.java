package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LimitOfOverTimeSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;

/**
 * 
 * @author nampt
 * 自動計算設定
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoCalculationSetting {
	
	//計算区分
	private AutoCalculationCategoryOutsideHours calculationAttr;
	
	//上限の設定
	private LimitOfOverTimeSetting upperLimitSetting;
	
	/**
	 * 計算区分を打刻から計算するに変更する
	 */
	public void calculationAttrChangeStamp() {
		this.calculationAttr = AutoCalculationCategoryOutsideHours.CalculateEmbossing;
	}
}
