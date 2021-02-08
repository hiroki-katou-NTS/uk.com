package nts.uk.ctx.at.request.app.command.application.holidaywork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCommand {

	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkUpdateCmd appHolidayWork;
	
	public AppDispInfoStartupDto appDispInfoStartupDto;
}
