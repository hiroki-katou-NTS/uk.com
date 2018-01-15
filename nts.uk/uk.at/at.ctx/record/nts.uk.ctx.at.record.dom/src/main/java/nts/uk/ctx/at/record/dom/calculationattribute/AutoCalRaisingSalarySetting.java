package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SalaryCalAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SpecificSalaryCalAttr;

/**
 * 
 * @author nampt
 * 加給の自動計算設定
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AutoCalRaisingSalarySetting {

	//加給計算区分
	private SalaryCalAttr salaryCalSetting;
	
	//特定加給計算区分
	private SpecificSalaryCalAttr specificSalaryCalSetting;
	
}
