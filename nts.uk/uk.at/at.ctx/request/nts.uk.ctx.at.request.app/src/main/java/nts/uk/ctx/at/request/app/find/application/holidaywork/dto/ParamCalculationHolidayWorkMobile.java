package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkInsertCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkUpdateCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamCalculationHolidayWorkMobile {

	private String companyId;
	
	private String employeeId;
	
	private String appDate;
	
	private Boolean mode;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	private AppHolidayWorkInsertCmd appHolidayWorkInsert;
	
	private AppHolidayWorkUpdateCmd appHolidayWorkUpdate;
	
	private Boolean isAgent;
}
