package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author HungTT - 計算式
 *
 */

@Getter
public class Formula extends AggregateRoot {
	
	private String companyId;
	private FormulaCode formulaCode;
	private FormulaName formulaName;
	private FormulaSettingMethod settingMethod;
	private Optional<NestedUseAtr> nestedAtr;

}
