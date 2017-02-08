package nts.uk.ctx.pr.formula.dom.formula;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;

/**
 * @author hungnm
 *
 */
public class FormulaEasyHead extends DomainObject {
	@Getter
	private ConditionAtr conditionAtr;

	@Getter
	private ReferenceMasterNo referenceMasterNo;

	@Getter
	private List<FormulaEasyCondition> formulaEasyCondition = new ArrayList<>();

	/**
	 * @param conditionAtr
	 * @param referenceMasterNo
	 * @param formulaEasyCondition
	 */
	public FormulaEasyHead(ConditionAtr conditionAtr, ReferenceMasterNo referenceMasterNo) {
		super();
		this.conditionAtr = conditionAtr;
		this.referenceMasterNo = referenceMasterNo;
	}

	public FormulaEasyHead createFromJavaType(int conditionAtr, int referenceMasterNo) {
		return new FormulaEasyHead(EnumAdaptor.valueOf(conditionAtr, ConditionAtr.class),
				EnumAdaptor.valueOf(referenceMasterNo, ReferenceMasterNo.class));
	}
	
	public void setFormulaEasyCondition(List<FormulaEasyCondition> formulaEasyCondition){
		this.formulaEasyCondition.clear();
		this.formulaEasyCondition.addAll(formulaEasyCondition);
	}
}
