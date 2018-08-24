package nts.uk.ctx.at.function.app.find.holidaysremaining;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.specialholiday.SpecialHolidayDto;

@Data
public class VariousVacationControlDto {

	private boolean annualHolidaySetting;
	private boolean yearlyReservedSetting;
	private boolean substituteHolidaySetting;
	private boolean pauseItemHolidaySetting;
	private boolean childNursingSetting;
	private boolean nursingCareSetting;
	private List<SpecialHolidayDto> listSpecialHoliday;

	public VariousVacationControlDto(boolean annualHolidaySetting, boolean yearlyReservedSetting,
			boolean substituteHolidaySetting, boolean pauseItemHolidaySetting, boolean childNursingSetting,
			boolean nursingCareSetting, List<SpecialHolidayDto> listSpecialHoliday) {
		super();
		this.annualHolidaySetting = annualHolidaySetting;
		this.yearlyReservedSetting = yearlyReservedSetting;
		this.substituteHolidaySetting = substituteHolidaySetting;
		this.pauseItemHolidaySetting = pauseItemHolidaySetting;
		this.childNursingSetting = childNursingSetting;
		this.nursingCareSetting = nursingCareSetting;
		this.listSpecialHoliday = listSpecialHoliday;
	}
}
