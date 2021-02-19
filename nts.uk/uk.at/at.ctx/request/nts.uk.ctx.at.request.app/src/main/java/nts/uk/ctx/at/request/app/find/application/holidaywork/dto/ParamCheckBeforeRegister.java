package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkInsertCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamCheckBeforeRegister {
	
	private boolean require;
	
	private String companyId;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	private AppHolidayWorkInsertCmd appHolidayWork;
	
	private boolean isProxy;
}
