package nts.uk.ctx.at.record.app.find.remainingnumber.dayoffmanagement;

import lombok.Data;

@Data
public class DayOffManagementDto {
	/*
	 * 振休日
	 */
	private String dateHoliday;
	
	/*
	 * 消化数
	 */
	private String numberDay;
	
	private boolean usedDay;

	public DayOffManagementDto(String dateHoliday, String numberDay, boolean usedDay) {
		super();
		this.dateHoliday = dateHoliday;
		this.numberDay = numberDay;
		this.usedDay = usedDay;
	}
	
	
	
}
