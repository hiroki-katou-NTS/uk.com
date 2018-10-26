package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Data;

@Data
public class CheckConditionTimeDto {
	private int category;
	private String categoryName;
	private int tabOrder = 0;
	private String startDate;
	private String endDate;
	private String startMonth;
	private String endMonth;
	private Integer year;
	private int period36Agreement ;
	public CheckConditionTimeDto(int category, String categoryName, String startDate, String endDate, String startMonth,
			String endMonth) {
		super();
		this.category = category;
		this.categoryName = categoryName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startMonth = startMonth;
		this.endMonth = endMonth;	
	}
	public CheckConditionTimeDto(int category, String categoryName, Integer year) {
		super();
		this.category = category;
		this.categoryName = categoryName;
		this.year = year;
	}
	public CheckConditionTimeDto(int category, String categoryName, String startDate, String endDate, String startMonth,
			String endMonth, Integer year) {
		super();
		this.category = category;
		this.categoryName = categoryName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.year = year;
	}	
}
