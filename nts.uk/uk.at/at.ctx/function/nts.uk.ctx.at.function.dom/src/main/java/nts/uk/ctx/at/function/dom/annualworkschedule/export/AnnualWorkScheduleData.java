package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnnualWorkScheduleData {
	private String name;
	private String month1st;
	private String month2nd;
	private String month3rd;
	private String month4th;
	private String month5th;
	private String month6th;
	private String month7th;
	private String month8th;
	private String month9th;
	private String month10th;
	private String month11th;
	private String month12th;
	private String average;
	private String sum;
	private String monthsExceeded;

	private String period1st;
	private String period2nd;
	private String period3rd;
	private String period4th;
	private String period5th;
	private String period6th;
	private String period7th;

	public AnnualWorkScheduleData(String name) {
		this.name = name;
	}
}