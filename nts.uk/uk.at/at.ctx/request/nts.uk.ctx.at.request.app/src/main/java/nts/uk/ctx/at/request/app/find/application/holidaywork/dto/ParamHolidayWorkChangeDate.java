package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoNoDateDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
public class ParamHolidayWorkChangeDate {

	private String companyId;

	private List<String> dateList;

	private int applicationType;
	
	private AppHdWorkDispInfoCmd appHdWorkDispInfoDto;
}
