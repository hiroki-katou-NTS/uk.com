package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class DayOffManagementDto {
	/*
	 * 振休日
	 */
	private GeneralDate dateHoliday;
	
	/*
	 * 消化数
	 */
	private String numberDay;
	
	private boolean usedDay;
	
	private String comDayOffId;

	public DayOffManagementDto(GeneralDate dateHoliday, String numberDay, boolean usedDay, String comDayOffId) {
		super();
		this.dateHoliday = dateHoliday;
		this.numberDay = numberDay;
		this.usedDay = usedDay;
		this.comDayOffId = comDayOffId;
	}
	
	
	
}
