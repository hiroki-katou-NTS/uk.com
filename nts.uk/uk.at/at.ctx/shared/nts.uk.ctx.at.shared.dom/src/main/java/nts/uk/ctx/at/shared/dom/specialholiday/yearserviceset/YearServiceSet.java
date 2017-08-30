package nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset;

import lombok.Getter;

@Getter
public class YearServiceSet {
	private String companyId;
	private int specialHolidayCode;
	private int yearServiceType;
	private int year;
	private int month;
	private int date;
	public YearServiceSet(String companyId, int specialHolidayCode, int yearServiceType, int year, int month, int date) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.yearServiceType = yearServiceType;
		this.year = year;
		this.month = month;
		this.date = date;
	}

	public static YearServiceSet createFromJavaType(String companyId, int specialHolidayCode, int yearServiceType, int year, int month, int date){
		return new YearServiceSet(companyId, specialHolidayCode, yearServiceType, year, month, date);
	}
}
