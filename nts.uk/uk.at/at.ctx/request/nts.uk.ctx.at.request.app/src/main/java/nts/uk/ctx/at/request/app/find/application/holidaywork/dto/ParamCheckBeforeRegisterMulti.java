package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

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
public class ParamCheckBeforeRegisterMulti {
	
	private boolean require;

	private String companyId;
	
	private List<String> empList;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	private AppHolidayWorkInsertCmd appHolidayWork;
}
