package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkUpdateCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamCheckBeforeUpdate {

	private boolean require;
	
	private String companyId;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	private AppHolidayWorkUpdateCmd appHolidayWork;
	
	private boolean isProxy;
}
