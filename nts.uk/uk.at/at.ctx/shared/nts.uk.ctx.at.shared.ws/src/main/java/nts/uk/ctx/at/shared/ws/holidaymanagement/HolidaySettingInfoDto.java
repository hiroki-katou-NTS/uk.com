package nts.uk.ctx.at.shared.ws.holidaymanagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidaySettingInfoDto {
	
	public Double addHolidayValue;
	public Double holidayValue;
	public Integer monthDay;
	public int nonStatutory;
	public GeneralDate standardDate;
	public int holidayCheckUnit;
	public Integer selectedClassification;
	
}
