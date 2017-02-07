package nts.uk.ctx.pr.formula.dom.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.util.Range;
import nts.uk.ctx.pr.formula.dom.enums.AdjustmentAtr;
import nts.uk.ctx.pr.formula.dom.enums.BaseMoneyAtr;
import nts.uk.ctx.pr.formula.dom.enums.DivideValueSet;
import nts.uk.ctx.pr.formula.dom.enums.EasyFormulaTypeAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundAtr;
import nts.uk.ctx.pr.formula.dom.primitive.DivideValue;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.PremiumRate;
import nts.uk.ctx.pr.formula.dom.primitive.WorkItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.WorkValue;

/**
 * @author hungnm
 *
 */
public class FormulaEasyDetail extends DomainObject {

	@Getter
	private Money aBaseMoney;

	@Getter
	private BaseMoneyAtr aBaseMoneyAtr;

	@Getter
	private DivideValue bDivideValue;

	@Getter
	private DivideValueSet bDivideValueSet;

	@Getter
	private PremiumRate cPremiumRate;

	@Getter
	private RoundAtr dRoundAtr;

	@Getter
	private FormulaName formulaName;

	@Getter
	private EasyFormulaTypeAtr easyFormulaTypeAtr;

	@Getter
	private WorkItemCode eWorkItemCode;

	@Getter
	private WorkValue eWorkValue;

	@Getter
	private AdjustmentAtr fAdjustmentAtr;

	@Getter
	private RoundAtr gRoundAtr;

	@Getter
	private Range<Integer> limitValue;

	@Getter
	private List<ItemCode> aItemCode = new ArrayList<>();

	/**
	 * @param aBaseMoney
	 * @param aBaseMoneyAtr
	 * @param bDivideValue
	 * @param bDivideValueSet
	 * @param cPremiumRate
	 * @param dRoundAtr
	 * @param formulaName
	 * @param easyFormulaTypeAtr
	 * @param eWorkItemCode
	 * @param eWorkValue
	 * @param fAdjustmentAtr
	 * @param gRoundAtr
	 * @param limitValue
	 * @param aItemCode
	 */
	public FormulaEasyDetail(Money aBaseMoney, BaseMoneyAtr aBaseMoneyAtr, DivideValue bDivideValue,
			DivideValueSet bDivideValueSet, PremiumRate cPremiumRate, RoundAtr dRoundAtr, FormulaName formulaName,
			EasyFormulaTypeAtr easyFormulaTypeAtr, WorkItemCode eWorkItemCode, WorkValue eWorkValue,
			AdjustmentAtr fAdjustmentAtr, RoundAtr gRoundAtr, Range<Integer> limitValue) {
		super();
		this.aBaseMoney = aBaseMoney;
		this.aBaseMoneyAtr = aBaseMoneyAtr;
		this.bDivideValue = bDivideValue;
		this.bDivideValueSet = bDivideValueSet;
		this.cPremiumRate = cPremiumRate;
		this.dRoundAtr = dRoundAtr;
		this.formulaName = formulaName;
		this.easyFormulaTypeAtr = easyFormulaTypeAtr;
		this.eWorkItemCode = eWorkItemCode;
		this.eWorkValue = eWorkValue;
		this.fAdjustmentAtr = fAdjustmentAtr;
		this.gRoundAtr = gRoundAtr;
		this.limitValue = limitValue;
	}

	public FormulaEasyDetail createFromJavaType(BigDecimal aBaseMoney, int aBaseMoneyAtr, BigDecimal bDivideValue,
			int bDivideValueSet, BigDecimal cPremiumRate, int dRoundAtr, String formulaName, int easyFormulaTypeAtr,
			String eWorkItemCode, BigDecimal eWorkValue, int fAdjustmentAtr, int gRoundAtr, int minValue,
			int maxValue) {
		return new FormulaEasyDetail(new Money(aBaseMoney), EnumAdaptor.valueOf(aBaseMoneyAtr, BaseMoneyAtr.class),
				new DivideValue(bDivideValue), EnumAdaptor.valueOf(bDivideValueSet, DivideValueSet.class),
				new PremiumRate(cPremiumRate), EnumAdaptor.valueOf(dRoundAtr, RoundAtr.class),
				new FormulaName(formulaName), EnumAdaptor.valueOf(easyFormulaTypeAtr, EasyFormulaTypeAtr.class),
				new WorkItemCode(eWorkItemCode), new WorkValue(eWorkValue),
				EnumAdaptor.valueOf(fAdjustmentAtr, AdjustmentAtr.class),
				EnumAdaptor.valueOf(gRoundAtr, RoundAtr.class), Range.between(minValue, maxValue));
	}
	
	public void addItemCodesFromJavaType(List<String> aItemCode){
		this.aItemCode.clear();
		aItemCode.stream().forEach(item -> this.aItemCode.add(new ItemCode(item)));
	}
}
