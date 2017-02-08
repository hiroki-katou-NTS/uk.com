/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formulaeasydetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.pr.formula.dom.formula.FormulaEasyDetail;

/**
 * @author hungnm
 *
 */
@Value
public class FormulaEasyDetailDto {

	BigDecimal aBaseMoney;

	int aBaseMoneyAtr;

	BigDecimal bDivideValue;

	int bDivideValueSet;

	BigDecimal cPremiumRate;

	int dRoundAtr;

	String formulaName;

	int easyFormulaTypeAtr;

	String eWorkItemCode;

	BigDecimal eWorkValue;

	int fAdjustmentAtr;

	int gRoundAtr;

	int maxValue;

	int minValue;

	List<String> aItemCode;

	public static FormulaEasyDetailDto fromDomain(FormulaEasyDetail domain) {
		return new FormulaEasyDetailDto(domain.getABaseMoney().v(), domain.getABaseMoneyAtr().value,
				domain.getBDivideValue().v(), domain.getBDivideValueSet().value, domain.getCPremiumRate().v(),
				domain.getDRoundAtr().value, domain.getFormulaName().v(), domain.getEasyFormulaTypeAtr().value,
				domain.getEWorkItemCode().v(), domain.getEWorkValue().v(), domain.getFAdjustmentAtr().value,
				domain.getGRoundAtr().value, domain.getLimitValue().max(), domain.getLimitValue().min(),
				domain.getAItemCode().stream().map(i -> i.v()).collect(Collectors.toList()));
	}

}
