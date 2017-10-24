package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SalaryCalAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SpecificSalaryCalAttr;

/**
 * 
 * @author nampt
 * 加給の自動計算設定
 *
 */
@Getter
public class AutoCalRaisingSalarySetting {

	//加給計算区分
	private SalaryCalAttr salaryCalSetting;
	
	//特定加給計算区分
	private SpecificSalaryCalAttr specificSalaryCalSetting;
	
}
