package nts.uk.ctx.pr.formula.dom.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.FixFormulaAtr;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;

/**
 * @author hungnm
 *
 */
@Getter
public class FormulaEasyCondition extends AggregateRoot {
	
	private CompanyCode companyCode;
	
	private FormulaCode formulaCode;
	
	private HistoryId historyId;
	
	private EasyFormulaCode easyFormulaCode;

	private FixFormulaAtr fixFormulaAtr;

	private Money fixMoney;

	private ReferenceMasterCode referenceMasterCode;


	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
	 * @param fixFormulaAtr
	 * @param fixMoney
	 * @param referenceMasterCode
	 * @param formulaEasyDetail
	 */
	public FormulaEasyCondition(CompanyCode companyCode, FormulaCode formulaCode, HistoryId historyId, EasyFormulaCode easyFormulaCode, FixFormulaAtr fixFormulaAtr, Money fixMoney,
			ReferenceMasterCode referenceMasterCode) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.easyFormulaCode = easyFormulaCode;
		this.fixFormulaAtr = fixFormulaAtr;
		this.fixMoney = fixMoney;
		this.referenceMasterCode = referenceMasterCode;
	}

	public static FormulaEasyCondition createFromJavaType(String companyCode, String formulaCode, String historyId, String easyFormulaCode, int fixFormulaAtr, BigDecimal fixMoney,
			String referenceMasterCode) {
		return new FormulaEasyCondition(new CompanyCode(companyCode), new FormulaCode(formulaCode), new HistoryId(historyId),
				new EasyFormulaCode(easyFormulaCode), EnumAdaptor.valueOf(fixFormulaAtr, FixFormulaAtr.class),
				new Money(fixMoney), new ReferenceMasterCode(referenceMasterCode));
	}

}
