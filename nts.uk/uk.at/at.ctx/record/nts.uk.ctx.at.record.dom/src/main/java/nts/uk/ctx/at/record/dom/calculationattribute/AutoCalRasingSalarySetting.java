package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SalaryCalSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SpecificSalaryCalSetting;

/**
 * 
 * @author nampt
 * 加給の自動計算設定
 *
 */
@Getter
public class AutoCalRasingSalarySetting {

	//加給計算区分
	private SalaryCalSetting salaryCalSetting;
	
	//特定加給計算区分
	private SpecificSalaryCalSetting specificSalaryCalSetting;
	
}
