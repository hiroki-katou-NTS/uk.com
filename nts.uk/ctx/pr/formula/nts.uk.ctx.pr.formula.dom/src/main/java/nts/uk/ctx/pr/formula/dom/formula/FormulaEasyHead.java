package nts.uk.ctx.pr.formula.dom.formula;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.ConditionAtr;
import nts.uk.ctx.pr.formula.dom.enums.ReferenceMasterNo;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;

/**
 * @author hungnm
 *
 */
@Getter
public class FormulaEasyHead extends DomainObject {
	
	private String companyCode;
	
	private FormulaCode formulaCode;
	
	private HistoryId historyId;

	private ConditionAtr conditionAtr;

	private ReferenceMasterNo referenceMasterNo;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param conditionAtr
	 * @param referenceMasterNo
	 */
	public FormulaEasyHead(String companyCode, FormulaCode formulaCode, HistoryId historyId,
			ConditionAtr conditionAtr, ReferenceMasterNo referenceMasterNo) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.conditionAtr = conditionAtr;
		this.referenceMasterNo = referenceMasterNo;
	}

	public FormulaEasyHead createFromJavaType(String companyCode, String formulaCode, String historyId, int conditionAtr, int referenceMasterNo) {
		return new FormulaEasyHead(companyCode, new FormulaCode(formulaCode),new HistoryId(historyId),
				EnumAdaptor.valueOf(conditionAtr, ConditionAtr.class),
				EnumAdaptor.valueOf(referenceMasterNo, ReferenceMasterNo.class));
	}

}
