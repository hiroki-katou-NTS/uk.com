package nts.uk.ctx.pr.formula.dom.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.formula.dom.enums.FixFormulaAtr;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;

/**
 * @author hungnm
 *
 */
public class FormulaEasyCondition extends DomainObject {
	@Getter
	private EasyFormulaCode easyFormulaCode;

	@Getter
	private FixFormulaAtr fixFormulaAtr;

	@Getter
	private Money fixMoney;

	@Getter
	private ReferenceMasterCode referenceMasterCode;

	@Getter
	private List<FormulaEasyDetail> formulaEasyDetail = new ArrayList<>();

	/**
	 * @param easyFormulaCode
	 * @param fixFormulaAtr
	 * @param fixMoney
	 * @param referenceMasterCode
	 * @param formulaEasyDetail
	 */
	public FormulaEasyCondition(EasyFormulaCode easyFormulaCode, FixFormulaAtr fixFormulaAtr, Money fixMoney,
			ReferenceMasterCode referenceMasterCode) {
		super();
		this.easyFormulaCode = easyFormulaCode;
		this.fixFormulaAtr = fixFormulaAtr;
		this.fixMoney = fixMoney;
		this.referenceMasterCode = referenceMasterCode;
	}

	public FormulaEasyCondition createFromJavaType(String easyFormulaCode, int fixFormulaAtr, BigDecimal fixMoney,
			String referenceMasterCode) {
		return new FormulaEasyCondition(new EasyFormulaCode(easyFormulaCode),
				EnumAdaptor.valueOf(fixFormulaAtr, FixFormulaAtr.class), new Money(fixMoney),
				new ReferenceMasterCode(referenceMasterCode));
	}
	
	public void setFormulaEasyDetail (List<FormulaEasyDetail> formulaEasyDetail) {
		this.formulaEasyDetail.clear();
		this.formulaEasyDetail.addAll(formulaEasyDetail);
	}
}
