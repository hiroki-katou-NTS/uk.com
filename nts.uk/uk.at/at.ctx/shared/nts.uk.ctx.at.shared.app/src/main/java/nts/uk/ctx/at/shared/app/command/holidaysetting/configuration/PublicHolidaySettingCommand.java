package nts.uk.ctx.at.shared.app.command.holidaysetting.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author quytb
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class PublicHolidaySettingCommand {
	private String companyId;
	private Integer managePublicHoliday;
	private Integer publicHolidayPeriod;
	private Integer publicHdCarryOverDeadline;
	private Integer carryOverNumberOfPublicHdIsNegative;
}
