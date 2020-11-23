package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
public class AppHolidayWorkParamPC {
	
	private List<String> empList;
	
	private List<String> dateList;
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;
}
