package nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
public class DateDisplaySettingPeriod {
	private GeneralDate periodStartdate;
	private GeneralDate periodEnddate;
}
