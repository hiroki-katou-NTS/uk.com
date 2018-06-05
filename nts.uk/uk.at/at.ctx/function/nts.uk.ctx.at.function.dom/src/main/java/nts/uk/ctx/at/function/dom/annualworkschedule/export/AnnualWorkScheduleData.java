package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeOfManagePeriodImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem.MonthlyAttendanceResultImport;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.ValueOuputFormat;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;

@Setter
@Getter
public class AnnualWorkScheduleData {
	/**
	 * 値の出力形式
	 */
	private ValueOuputFormat valOutFormat;
	private String headingName;
	private YearMonth startYm;
	private int numMonth;

	private ItemData month1st;
	private ItemData month2nd;
	private ItemData month3rd;
	private ItemData month4th;
	private ItemData month5th;
	private ItemData month6th;
	private ItemData month7th;
	private ItemData month8th;
	private ItemData month9th;
	private ItemData month10th;
	private ItemData month11th;
	private ItemData month12th;

	private BigDecimal average;
	private BigDecimal sum;
	private Integer monthsExceeded;

	private ItemData period1st;
	private ItemData period2nd;
	private ItemData period3rd;
	private ItemData period4th;
	private ItemData period5th;
	private ItemData period6th;
	private ItemData period7th;

	private Integer maxDigitAfterDecimalPoint = null;

	public void setMonthlyData(ItemData item, YearMonth ym) {
		int monthIndex = (int) this.startYm.until(ym, ChronoUnit.MONTHS);
		switch (monthIndex) {
		case 0:
			this.month1st = item;
			break;
		case 1:
			this.month2nd = item;
			break;
		case 2:
			this.month3rd = item;
			break;
		case 3:
			this.month4th = item;
			break;
		case 4:
			this.month5th = item;
			break;
		case 5:
			this.month6th = item;
			break;
		case 6:
			this.month7th = item;
			break;
		case 7:
			this.month8th = item;
			break;
		case 8:
			this.month9th = item;
			break;
		case 9:
			this.month10th = item;
			break;
		case 10:
			this.month11th = item;
			break;
		case 11:
			this.month12th = item;
			break;
		}
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public AnnualWorkScheduleData calc() {
		this.sum = new BigDecimal(0);
		this.sum = this.sum.add(month1st == null ? BigDecimal.valueOf(0) : month1st.getValue())
				.add(this.getItemValueByNullOrZero(this.month2nd)).add(this.getItemValueByNullOrZero(this.month3rd))
				.add(this.getItemValueByNullOrZero(this.month4th)).add(this.getItemValueByNullOrZero(this.month5th))
				.add(this.getItemValueByNullOrZero(this.month6th)).add(this.getItemValueByNullOrZero(this.month7th))
				.add(this.getItemValueByNullOrZero(this.month8th)).add(this.getItemValueByNullOrZero(this.month9th))
				.add(this.getItemValueByNullOrZero(this.month10th)).add(this.getItemValueByNullOrZero(this.month11th))
				.add(this.getItemValueByNullOrZero(this.month12th));

		this.average = this.sum.divide(BigDecimal.valueOf(this.numMonth), this.getMaxDigitAfterDecimalPoint(),
				RoundingMode.HALF_UP); // TODO
		return this;
	}

	private int getMaxDigitAfterDecimalPoint() {
		if (maxDigitAfterDecimalPoint == null) {
			maxDigitAfterDecimalPoint = Collections
					.max(Arrays.asList(this.getDigitAfterDecimalPoint(this.month1st.getValue()),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month2nd)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month3rd)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month4th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month5th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month6th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month7th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month8th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month9th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month10th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month11th)),
							this.getDigitAfterDecimalPoint(this.getItemValueByNullOrDefault(this.month12th))));
		}
		return maxDigitAfterDecimalPoint;
	}

	private int getDigitAfterDecimalPoint(BigDecimal value) {
		if (value == null)
			return 0;
		String valueStr = value.toString();
		int indexPoint = valueStr.indexOf('.');
		if (indexPoint < 0)
			return 0;
		return valueStr.length() - (indexPoint + 1);
	}

	private BigDecimal getItemValueByNullOrZero(ItemData item) {
		return item == null ? BigDecimal.valueOf(0) : item.getValue();
	}

	private BigDecimal getItemValueByNullOrDefault(ItemData item) {
		return item == null ? null : item.getValue();
	}

	private String formatItemData(ItemData item) {
		if (item == null)
			return "";
		return formatBigDecimal(item.getValue());
	}

	private String formatBigDecimal(BigDecimal value) {
		if (value == null)
			return "";
		if (valOutFormat.equals(ValueOuputFormat.TIME)) {
			return String.format("%d:%02d", value.longValue() / 60, Math.abs(value.longValue() % 60));
		}
		if (valOutFormat.equals(ValueOuputFormat.TIMES)) {
			return String.valueOf(value.longValue());
		}
		return String.valueOf(value.floatValue());
	}

	public String formatMonth1st() {
		return this.formatItemData(this.month1st);
	}

	public String formatMonth2nd() {
		return this.formatItemData(this.month2nd);
	}

	public String formatMonth3rd() {
		return this.formatItemData(this.month3rd);
	}

	public String formatMonth4th() {
		return this.formatItemData(this.month4th);
	}

	public String formatMonth5th() {
		return this.formatItemData(this.month5th);
	}

	public String formatMonth6th() {
		return this.formatItemData(this.month6th);
	}

	public String formatMonth7th() {
		return this.formatItemData(this.month7th);
	}

	public String formatMonth8th() {
		return this.formatItemData(this.month8th);
	}

	public String formatMonth9th() {
		return this.formatItemData(this.month9th);
	}

	public String formatMonth10th() {
		return this.formatItemData(this.month10th);
	}

	public String formatMonth11th() {
		return this.formatItemData(this.month11th);
	}

	public String formatMonth12th() {
		return this.formatItemData(this.month12th);
	}

	public String formatSum() {
		return this.formatBigDecimal(this.sum);
	}

	public String formatAverage() {
		return this.formatBigDecimal(this.average);
	}

	public static AnnualWorkScheduleData fromMonthlyAttendanceList(ItemOutTblBook itemOut,
			List<MonthlyAttendanceResultImport> monthlyAttendanceResult, YearMonth startYm, int numMonth) {
		final Map<Integer, Integer> operationMap = itemOut.getListOperationSetting().stream()
				.collect(Collectors.toMap(CalcFormulaItem::getAttendanceItemId, CalcFormulaItem::getOperation));

		AnnualWorkScheduleData annualWorkScheduleData = new AnnualWorkScheduleData();
		annualWorkScheduleData.setHeadingName(itemOut.getHeadingName().v());
		annualWorkScheduleData.setValOutFormat(itemOut.getValOutFormat());
		annualWorkScheduleData.setStartYm(startYm);
		annualWorkScheduleData.setNumMonth(numMonth);
		monthlyAttendanceResult.forEach(m -> {
			annualWorkScheduleData.setMonthlyData(
					AnnualWorkScheduleData.sumAttendanceData(operationMap, m.getAttendanceItems()),
					YearMonth.of(m.getYearMonth().year(), m.getYearMonth().month()));
		});
		return annualWorkScheduleData;
	}

	private static ItemData sumAttendanceData(Map<Integer, Integer> operationMap,
			List<AttendanceItemValueImport> attendanceItemsValue) {
		BigDecimal sum = BigDecimal.valueOf(0);
		for (AttendanceItemValueImport attendanceItem : attendanceItemsValue) {
			// 0: integer, 2 decimal
			if (attendanceItem.isNumber()) {
				BigDecimal val;
				try {
					val = new BigDecimal(attendanceItem.getValue());
				} catch (Exception e) {
					continue;
				}
				// 0: subtract, 1: plus
				if (operationMap.get(attendanceItem.getItemId()) == 0) {
					sum = sum.subtract(val);
				} else {
					sum = sum.add(val);
				}
			}
		}
		return new ItemData(sum, null);
	}

	public static AnnualWorkScheduleData fromAgreementTimeList(ItemOutTblBook itemOut,
			List<AgreementTimeOfManagePeriodImport> listAgreementTime, YearMonth startYm, int numMonth) {
		AnnualWorkScheduleData annualWorkScheduleData = new AnnualWorkScheduleData();
		annualWorkScheduleData.setHeadingName(itemOut.getHeadingName().v());
		annualWorkScheduleData.setValOutFormat(itemOut.getValOutFormat());
		annualWorkScheduleData.setStartYm(startYm);
		annualWorkScheduleData.setNumMonth(numMonth);
		listAgreementTime.forEach(m -> {
			BigDecimal value = new BigDecimal(m.getAgreementTime().getAgreementTime().v());
			AgreementTimeStatusOfMonthly status = m.getAgreementTime().getStatus();
			ItemData item = new ItemData(value, status);
			annualWorkScheduleData.setMonthlyData(item,
					YearMonth.of(m.getYearMonth().year(), m.getYearMonth().month()));
		});
		return annualWorkScheduleData;
	}
}