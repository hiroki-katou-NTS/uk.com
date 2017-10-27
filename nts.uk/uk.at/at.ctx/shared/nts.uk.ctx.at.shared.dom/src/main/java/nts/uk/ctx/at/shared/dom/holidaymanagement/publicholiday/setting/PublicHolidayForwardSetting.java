package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公休日数の繰越設定
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class PublicHolidayForwardSetting {
	private Boolean transferWhenPublicHolidayIsMinus;
	private PublicHolidayCarryOverDeadline publicHolidayCarryOverDeadline;
}
