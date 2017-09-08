package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateYearServicePerCommand {
	/**コード**/
	private String specialHolidayCode;
	private String yearServiceCode;
	private String yearServiceName;
	private Integer yearServiceCls;
	
}
