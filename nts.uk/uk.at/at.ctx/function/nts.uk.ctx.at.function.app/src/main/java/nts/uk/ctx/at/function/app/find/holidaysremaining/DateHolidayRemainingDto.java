package nts.uk.ctx.at.function.app.find.holidaysremaining;

import lombok.Data;

@Data
public class DateHolidayRemainingDto {
	private String startDate;
	private String endDate;
	
	public DateHolidayRemainingDto(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}
