package nts.uk.ctx.pr.formula.dom.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.util.Range;
import nts.uk.ctx.pr.formula.dom.enums.AdjustmentAtr;
import nts.uk.ctx.pr.formula.dom.enums.BaseMoneyAtr;
import nts.uk.ctx.pr.formula.dom.enums.DivideValueSet;
import nts.uk.ctx.pr.formula.dom.enums.EasyFormulaTypeAtr;
import nts.uk.ctx.pr.formula.dom.enums.RoundAtr;
import nts.uk.ctx.pr.formula.dom.primitive.CompanyCode;
import nts.uk.ctx.pr.formula.dom.primitive.DivideValue;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;	
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaName;
import nts.uk.ctx.pr.formula.dom.primitive.HistoryId;
import nts.uk.ctx.pr.formula.dom.primitive.ItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.Money;
import nts.uk.ctx.pr.formula.dom.primitive.PremiumRate;
import nts.uk.ctx.pr.formula.dom.primitive.WorkItemCode;
import nts.uk.ctx.pr.formula.dom.primitive.WorkValue;

/**
 * @author hungnm
 *
 */
@Getter
public class FormulaEasyDetail extends DomainObject {

	private String companyCode;

	private FormulaCode formulaCode;

	private HistoryId historyId;

	private EasyFormulaCode easyFormulaCode;

	private EasyFormulaName easyFormulaName;

	private EasyFormulaTypeAtr easyFormulaTypeAtr;

	private Money baseFixedAmount;

	private BaseMoneyAtr baseAmountDevision;

	private DivideValue baseFixedValue;

	private DivideValueSet baseValueDevision;

	private PremiumRate premiumRate;

	private RoundAtr roundProcessingDevision;

	private WorkItemCode coefficientDivision;

	private WorkValue coefficientFixedValue;

	private AdjustmentAtr adjustmentDevision;

	private RoundAtr totalRounding;

	private Range<BigDecimal> limitValue;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param easyFormulaCode
	 * @param easyFormulaName
	 * @param easyFormulaTypeAtr
	 * @param baseFixedAmount
	 * @param baseAmountDevision
	 * @param baseFixedValue
	 * @param baseValueDevision
	 * @param premiumRate
	 * @param roundProcessingDevision
	 * @param coefficientDivision
	 * @param coefficientFixedValue
	 * @param adjustmentDevision
	 * @param totalRounding
	 * @param limitValue
	 */
	public FormulaEasyDetail(String companyCode, FormulaCode formulaCode, HistoryId historyId,
			EasyFormulaCode easyFormulaCode, EasyFormulaName easyFormulaName, EasyFormulaTypeAtr easyFormulaTypeAtr,
			Money baseFixedAmount, BaseMoneyAtr baseAmountDevision, DivideValue baseFixedValue,
			DivideValueSet baseValueDevision, PremiumRate premiumRate, RoundAtr roundProcessingDevision,
			WorkItemCode coefficientDivision, WorkValue coefficientFixedValue, AdjustmentAtr adjustmentDevision,
			RoundAtr totalRounding, Range<BigDecimal> limitValue) {
		super();
		this.companyCode = companyCode;
		this.formulaCode = formulaCode;
		this.historyId = historyId;
		this.easyFormulaCode = easyFormulaCode;
		this.easyFormulaName = easyFormulaName;
		this.easyFormulaTypeAtr = easyFormulaTypeAtr;
		this.baseFixedAmount = baseFixedAmount;
		this.baseAmountDevision = baseAmountDevision;
		this.baseFixedValue = baseFixedValue;
		this.baseValueDevision = baseValueDevision;
		this.premiumRate = premiumRate;
		this.roundProcessingDevision = roundProcessingDevision;
		this.coefficientDivision = coefficientDivision;
		this.coefficientFixedValue = coefficientFixedValue;
		this.adjustmentDevision = adjustmentDevision;
		this.totalRounding = totalRounding;
		this.limitValue = limitValue;
	}
	
	public FormulaEasyDetail createFromJavaType (String companyCode, String formulaCode, String historyId,
			String easyFormulaCode,String easyFormulaName, int easyFormulaTypeAtr,BigDecimal baseFixedAmount,
			int baseAmountDevision, BigDecimal baseFixedValue, int baseValueDevision, BigDecimal premiumRate,
			int roundProcessingDevision, String coefficientDivision, BigDecimal coefficientFixedValue, int adjustmentDevision,
			int totalRounding, BigDecimal minValue, BigDecimal maxValue){
		return new FormulaEasyDetail(companyCode, new FormulaCode(formulaCode), new HistoryId(historyId),
				new EasyFormulaCode(easyFormulaCode), new EasyFormulaName(easyFormulaName), EnumAdaptor.valueOf(easyFormulaTypeAtr, EasyFormulaTypeAtr.class)
				, new Money(baseFixedAmount), EnumAdaptor.valueOf(baseAmountDevision, BaseMoneyAtr.class), new DivideValue(baseFixedValue)
				, EnumAdaptor.valueOf(baseValueDevision, DivideValueSet.class), new PremiumRate(premiumRate), EnumAdaptor.valueOf(roundProcessingDevision, RoundAtr.class)
				, new WorkItemCode(coefficientDivision), new WorkValue(coefficientFixedValue), EnumAdaptor.valueOf(adjustmentDevision, AdjustmentAtr.class)
				,EnumAdaptor.valueOf(totalRounding, RoundAtr.class), Range.between(minValue, maxValue));
	}

}
