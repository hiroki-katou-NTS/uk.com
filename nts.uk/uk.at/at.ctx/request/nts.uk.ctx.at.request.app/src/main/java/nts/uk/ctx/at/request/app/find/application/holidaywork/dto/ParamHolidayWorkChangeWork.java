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
public class ParamHolidayWorkChangeWork {
	
	private String companyId;
	
	private List<String> dateList;
	
	private String workTypeCode;
	
	private String workTimeCode;

	private int startTime;
	
	private int endTime;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfoDto;
}
