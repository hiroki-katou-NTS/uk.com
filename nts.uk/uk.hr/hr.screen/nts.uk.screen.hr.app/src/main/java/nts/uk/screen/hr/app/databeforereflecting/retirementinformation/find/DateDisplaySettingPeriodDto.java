package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.DateDisplaySettingPeriod;

@AllArgsConstructor
@Data
public class DateDisplaySettingPeriodDto {

	private GeneralDate periodStartdate;
	private GeneralDate periodEnddate;

	public DateDisplaySettingPeriodDto(DateDisplaySettingPeriod domain) {
		this.periodStartdate = domain == null || domain.getPeriodStartdate() == null ? GeneralDate.today() : domain.getPeriodStartdate();
		this.periodEnddate = domain == null || domain.getPeriodEnddate() == null ? GeneralDate.today() : domain.getPeriodEnddate();
	}
}
