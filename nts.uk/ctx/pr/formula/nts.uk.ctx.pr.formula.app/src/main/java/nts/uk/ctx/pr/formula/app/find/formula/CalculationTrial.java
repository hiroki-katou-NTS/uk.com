package nts.uk.ctx.pr.formula.app.find.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class CalculationTrial {

	private final String add = "+";
	private final String subtract = "-";
	private final String multiple = "*";
	private final String divide = "/";
	private final String pow = "^";
	private String openBracket = "(";
	private String closeBracket = ")";
	private String less = "<";
	private String more = ">";
	private String lessOrEqual = "<=";
	private String moreOrEqual = ">=";
	private String equal = "=";
	private String different = "!=";

	private String conditionalExpression = "関数＠条件式";
	private String andFunction = "関数＠かつ";
	private String orFunction = "関数＠または";
	private String roundFunction = "関数＠四捨五入";
	private String truncateFunction = "関数＠切捨て";
	private String roundUpFunction = "関数＠切上げ";
	private String maximumFunction = "関数＠最大値";
	private String minimumFunction = "関数＠最小値";
	private String familyMemberFunction = "関数＠家族人数";
	private String addMonthFunction = "関数＠月加算";
	private String extractYearFunction = "関数＠年抽出";
	private String extractMonthFunction = "関数＠月抽出";

	public CalculatorDto init() {
		String formula = "関数＠条件式（関数＠最大値（ 100 , 200 ）×  " + "関数＠四捨五入（　20.5　）＞ 0, 1000, 2000）+  "
				+ "関数＠最小値（100, 200）÷  関数＠切上げ（　10.6　）×  " + "関数＠条件式（ 関数＠かつ（ 関数＠年抽出（ 200010 ）＞1999 ,"
				+ "関数＠月抽出（ 200001 ）＜ 3 ）,  1.5 ,  2）";
		int smallIndex;
		BigDecimal result = null;
		for (int i = 0; i < formula.length(); i++) {
			// case first char is digit
			if (isDigit(formula.charAt(0))) {
				smallIndex = findLocation(formula);
				// get first value of formula
				BigDecimal firstValue = new BigDecimal(formula.substring(0, smallIndex));
				String firstSign = formula.substring(smallIndex, smallIndex + 1);
				// remove first value and sign outside formula
				formula = formula.substring(smallIndex + 1, formula.length());

				if (isDigit(formula.charAt(0))) {
					smallIndex = findLocation(formula);
					// get second value of formula
					BigDecimal secondValue = new BigDecimal(formula.substring(0, smallIndex + 1));
					String secondSign = formula.substring(smallIndex + 1, smallIndex + 2);
					// remove second value and sign outside formula
					formula = formula.substring(smallIndex + 2, formula.length());

					if (secondSign.equals(add) || secondSign.equals(subtract)) {
						// proceed to compute first value and second value
						result = executeSign(firstValue, secondValue, firstSign);
					} else if ((secondSign.equals(multiple) || secondSign.equals(divide))
							&& (firstSign.equals(multiple) || firstSign.equals(divide))) {
						result = executeSign(firstValue, secondValue, firstSign);
					} else if ((secondSign.equals(multiple) || secondSign.equals(divide))
							&& (firstSign.equals(add) || firstSign.equals(subtract))) {
						smallIndex = findLocation(formula);
						String thirdSign = formula.substring(smallIndex + 1, smallIndex + 2);
						List<BigDecimal> values = new ArrayList<>();
						values.add(new BigDecimal(formula.substring(0, smallIndex + 1)));
						formula = formula.substring(smallIndex + 2, formula.length());
					}
				}
			}
		}

		List<String> subFormula = splitFormula(formula);
		formula = formula.substring(Integer.parseInt(subFormula.get(1)) + 1, formula.length());
		return null;
	}

	private void proceedExcuteWithDigit(BigDecimal result, BigDecimal preValue, String firstSign, String preSign,
			String formula) {
		int smallIndex;
		if (isDigit(formula.charAt(0))) {
			smallIndex = findLocation(formula);
			// get second value of formula
			BigDecimal secondValue = new BigDecimal(formula.substring(0, smallIndex));
			String secondSign = formula.substring(smallIndex, smallIndex + 1);
			// remove second value and sign outside formula
			formula = formula.substring(smallIndex + 1, formula.length());

			if (secondSign.equals(add) || secondSign.equals(subtract)) {
				if (preValue == null) {
					result = executeSign(result, secondValue, firstSign);
				} else {
					result = executeSign(result, preValue, firstSign);
					result = executeSign(result, secondValue, preSign);
				}
				firstSign = secondSign;
				proceedExcuteWithDigit(result, null, firstSign, null, formula);
			} else if ((secondSign.equals(multiple) || secondSign.equals(divide))
					&& (firstSign.equals(multiple) || firstSign.equals(divide))) {
				if (preValue == null) {
					result = executeSign(result, secondValue, firstSign);
				} else {
					result = executeSign(result, preValue, firstSign);
					result = executeSign(result, secondValue, preSign);
				}
				firstSign = secondSign;
				proceedExcuteWithDigit(result, null, firstSign, null, formula);
			} else if ((secondSign.equals(multiple) || secondSign.equals(divide))
					&& (firstSign.equals(add) || firstSign.equals(subtract))) {
				if (isDigit(formula.charAt(smallIndex + 1))) {
					int nextIndex = findLocation(formula);
					BigDecimal nextValue = new BigDecimal(formula.substring(smallIndex + 1, nextIndex));
					String thirdSign = formula.substring(nextIndex, nextIndex + 1);
					preValue = secondSign.equals(multiple) ? secondValue.multiply(nextValue)
							: secondValue.divide(nextValue);
					formula = formula.substring(nextIndex + 1, formula.length());
					preSign = thirdSign;
					proceedExcuteWithDigit(result, preValue, firstSign, preSign, formula);
				}
			}
		}

	}

	private boolean isDigit(Character para) {
		return Character.isDigit(para);
	}

	private BigDecimal executeSign(BigDecimal firstValue, BigDecimal secondValue, String sign) {
		BigDecimal result = null;
		switch (sign) {
		case add:
			result = firstValue.add(secondValue);
			break;
		case subtract:
			result = firstValue.subtract(secondValue);
			break;
		case multiple:
			result = firstValue.multiply(secondValue);
			break;
		case divide:
			result = firstValue.divide(secondValue);
			break;
		case pow:
			result = firstValue.pow(secondValue.intValue());
			break;
		}
		return result;
	}

	private int findLocation(String formula) {
		int[] arr = new int[] { formula.indexOf(add), formula.indexOf(subtract), formula.indexOf(multiple),
				formula.indexOf(divide), formula.indexOf(pow, formula.indexOf(openBracket)) };
		return Arrays.stream(arr).filter(item -> item != -1).reduce((x, y) -> x < y ? x : y).getAsInt();
	}

	private List<String> splitFormula(String formula) {
		int openIndex = formula.indexOf(openBracket);
		int closeIndex = formula.indexOf(closeBracket);
		int start = openIndex;
		int countOpen = 1;
		int countClose = 1;
		int end = closeIndex;
		while (openIndex != -1 && openIndex < closeIndex) {
			openIndex = formula.indexOf("(", openIndex + 1);
			if (openIndex != -1 && openIndex < closeIndex) {
				countOpen += 1;
			}
		}
		while (countClose < countOpen) {
			if (openIndex > -1 && openIndex < formula.length()) {
				openIndex = formula.indexOf(openBracket, openIndex < closeIndex ? closeIndex + 1 : openIndex + 1);
				if (openIndex != -1 && (openIndex < closeIndex || openIndex > end)) {
					countOpen += 1;
				}
			}
			closeIndex = formula.indexOf(closeBracket, closeIndex + 1);
			countClose += 1;
		}
		List<String> subFormula = new ArrayList<>();
		if (!Character.isDigit(formula.charAt(0))) {
			subFormula.add(formula.substring(start + 1, closeIndex));
			subFormula.add(closeIndex + "");
		}
		return subFormula;
	}

	private BigDecimal conditionalExpression(String expression, String firstValue, String secondValue) {
		return null;
	}

	private boolean andFunction(String... paras) {
		return true;
	}

	private boolean orFunction(String... paras) {
		return true;
	}

	private BigDecimal roundFunction(String para) {
		return null;
	}

	private BigDecimal truncateFunction(String para) {
		return null;
	}

	private BigDecimal roundUpFunction(String para) {
		return null;
	}

	private BigDecimal maximumFunction(String... para) {
		return null;
	}

	private BigDecimal minimumFunction(String... para) {
		return null;
	}

	private BigDecimal familyMemberFunction(String para) {
		return BigDecimal.ONE;
	}

	private BigDecimal addMonthFunction(String yearMonth, String month) {
		return null;
	}

	private BigDecimal extractYear(String para) {
		return null;
	}

	private BigDecimal extractMonth(String para) {
		return null;
	}
}
