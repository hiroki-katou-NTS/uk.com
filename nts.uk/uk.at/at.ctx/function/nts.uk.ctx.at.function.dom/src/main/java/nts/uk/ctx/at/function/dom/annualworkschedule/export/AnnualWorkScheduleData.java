package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;

@Setter
@Getter
public class AnnualWorkScheduleData {
	/**
	* 値の出力形式
	*/
	private ValueOuputFormat valOutFormat;
	private String headingName;
	private int numMonth;
	private BigDecimal month1st;
	private BigDecimal month2nd;
	private BigDecimal month3rd;
	private BigDecimal month4th;
	private BigDecimal month5th;
	private BigDecimal month6th;
	private BigDecimal month7th;
	private BigDecimal month8th;
	private BigDecimal month9th;
	private BigDecimal month10th;
	private BigDecimal month11th;
	private BigDecimal month12th;

	private BigDecimal average;
	private BigDecimal sum;
	private Integer monthsExceeded;

	private BigDecimal period1st;
	private BigDecimal period2nd;
	private BigDecimal period3rd;
	private BigDecimal period4th;
	private BigDecimal period5th;
	private BigDecimal period6th;
	private BigDecimal period7th;

	private Integer maxDigitAfterDecimalPoint = null;

	public void setMonthlyData(BigDecimal value, YearMonth startYm, YearMonth ym) {
		int monthIndex = (int) startYm.until(ym, ChronoUnit.MONTHS);
		switch (monthIndex) {
		case 0:
			this.month1st = value;
			break;
		case 1:
			this.month2nd = value;
			break;
		case 2:
			this.month3rd = value;
			break;
		case 3:
			this.month4th = value;
			break;
		case 4:
			this.month5th = value;
			break;
		case 5:
			this.month6th = value;
			break;
		case 6:
			this.month7th = value;
			break;
		case 7:
			this.month8th = value;
			break;
		case 8:
			this.month9th = value;
			break;
		case 9:
			this.month10th = value;
			break;
		case 10:
			this.month11th = value;
			break;
		case 11:
			this.month12th = value;
			break;
		}
	}

	/**
	 * TODO
	 * @return
	 */
	public AnnualWorkScheduleData calc() {
		this.sum = new BigDecimal(0);
		this.sum = this.sum.add(month1st == null? BigDecimal.valueOf(0): month1st)
		.add(month2nd == null? BigDecimal.valueOf(0): month2nd)
		.add(month3rd == null? BigDecimal.valueOf(0): month3rd)
		.add(month4th == null? BigDecimal.valueOf(0): month4th)
		.add(month5th == null? BigDecimal.valueOf(0): month5th)
		.add(month6th == null? BigDecimal.valueOf(0): month6th)
		.add(month7th == null? BigDecimal.valueOf(0): month7th)
		.add(month8th == null? BigDecimal.valueOf(0): month8th)
		.add(month9th == null? BigDecimal.valueOf(0): month9th)
		.add(month10th == null? BigDecimal.valueOf(0): month10th)
		.add(month11th == null? BigDecimal.valueOf(0): month11th)
		.add(month12th == null? BigDecimal.valueOf(0): month12th);

		this.average = this.sum.divide(BigDecimal.valueOf(this.numMonth), this.getMaxDigitAfterDecimalPoint(), RoundingMode.HALF_UP); //TODO
		return this;
	}

	private int getMaxDigitAfterDecimalPoint() {
		if (maxDigitAfterDecimalPoint == null) {
			maxDigitAfterDecimalPoint
			= Collections.max(
					Arrays.asList(this.getDigitAfterDecimalPoint(this.month1st),
					this.getDigitAfterDecimalPoint(this.month2nd),
					this.getDigitAfterDecimalPoint(this.month3rd),
					this.getDigitAfterDecimalPoint(this.month4th),
					this.getDigitAfterDecimalPoint(this.month5th),
					this.getDigitAfterDecimalPoint(this.month6th),
					this.getDigitAfterDecimalPoint(this.month7th),
					this.getDigitAfterDecimalPoint(this.month8th),
					this.getDigitAfterDecimalPoint(this.month9th),
					this.getDigitAfterDecimalPoint(this.month10th),
					this.getDigitAfterDecimalPoint(this.month11th),
					this.getDigitAfterDecimalPoint(this.month12th)));
		}
		return maxDigitAfterDecimalPoint;
	}

	private int getDigitAfterDecimalPoint(BigDecimal value) {
		if (value == null) return 0;
		String valueStr = value.toString();
		int indexPoint = valueStr.indexOf('.');
		if (indexPoint < 0) return 0;
		return valueStr.length() - (indexPoint + 1);
	}
	/**
	 * TODO
	 * @param value
	 * @return
	 */
	private String format(BigDecimal value) {
		if (value == null) return "";
		if (valOutFormat.equals(ValueOuputFormat.TIME)) {
			return String.format("%d:%02d", value.longValue() / 60, Math.abs(value.longValue() % 60));
		}
		if (valOutFormat.equals(ValueOuputFormat.TIMES)) {
			return String.valueOf(value.longValue());
		}
		return String.valueOf(value.floatValue());
	}

	public String formatMonth1st() {
		return this.format(this.month1st);
	}
	public String formatMonth2nd() {
		return this.format(this.month2nd);
	}
	public String formatMonth3rd() {
		return this.format(this.month3rd);
	}
	public String formatMonth4th() {
		return this.format(this.month4th);
	}
	public String formatMonth5th() {
		return this.format(this.month5th);
	}
	public String formatMonth6th() {
		return this.format(this.month6th);
	}
	public String formatMonth7th() {
		return this.format(this.month7th);
	}
	public String formatMonth8th() {
		return this.format(this.month8th);
	}
	public String formatMonth9th() {
		return this.format(this.month9th);
	}
	public String formatMonth10th() {
		return this.format(this.month10th);
	}
	public String formatMonth11th() {
		return this.format(this.month11th);
	}
	public String formatMonth12th() {
		return this.format(this.month12th);
	}
	public String formatSum() {
		return this.format(this.sum);
	}
	public String formatAverage() {
		return this.format(this.average);
	}
}