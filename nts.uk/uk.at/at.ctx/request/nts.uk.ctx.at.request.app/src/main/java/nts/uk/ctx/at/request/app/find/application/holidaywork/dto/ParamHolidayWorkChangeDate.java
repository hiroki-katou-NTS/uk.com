package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamHolidayWorkChangeDate {

	private String companyId;

	private List<String> dateList;

	private int applicationType;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfoDto;
}
