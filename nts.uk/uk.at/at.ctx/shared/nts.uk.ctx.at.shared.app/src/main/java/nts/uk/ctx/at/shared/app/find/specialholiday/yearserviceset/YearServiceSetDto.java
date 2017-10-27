package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceset;

import lombok.Value;
/**
 * 
 * @author yennth
 *
 */
@Value
public class YearServiceSetDto {
	private String specialHolidayCode;
	private int yearServiceNo;
	private int year;
	private int month;
	private int date;
}
