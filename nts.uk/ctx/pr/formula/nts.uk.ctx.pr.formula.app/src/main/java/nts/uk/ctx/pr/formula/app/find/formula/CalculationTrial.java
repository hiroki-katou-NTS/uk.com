package nts.uk.ctx.pr.formula.app.find.formula;

import javax.ejb.Stateless;

@Stateless
public class CalculationTrial {

	public CalculatorDto init(CalculatorDto calculatorDto) {

		String formula = "関数＠条件式（関数＠最大値（ 100 , 200 ）×  関数＠四捨五入（　20.5　）＞ 0, 1000, 2000）+"
				+ "  関数＠最小値（100, 200）÷"
				+ "  関数＠切上げ（　10.6　）×"
				+ "  関数＠条件式（ 関数＠かつ（ 関数＠年抽出（ 200010 ）＞1999 ,関数＠月抽出（ 200001 ）＜ 3 ）,  1.5 ,  2）";
		
		String result = formula.substring(formula.indexOf("[") + 1, formula.indexOf("]"));
//		for (int i = 0; i < formula.length(); i++) {
//			if (formula.charAt(i) == '(') {
//				String formulaType[] = formula.split("(", 2);
//
//			}
//		}
		
		calculatorDto.setResult(result);
		return calculatorDto;
	}
}
  