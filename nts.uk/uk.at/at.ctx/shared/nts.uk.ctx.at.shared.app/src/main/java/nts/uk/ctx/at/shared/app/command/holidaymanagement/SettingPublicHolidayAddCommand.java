package nts.uk.ctx.at.shared.app.command.holidaymanagement;


import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class SettingPublicHolidayAddCommand {
		
	public int addHolidayValue;
	public String holidayValue;
	public int monthDay;
	public int nonStatutory;
	public GeneralDate standardDate;
	public int holidayCheckUnit;
	
}
