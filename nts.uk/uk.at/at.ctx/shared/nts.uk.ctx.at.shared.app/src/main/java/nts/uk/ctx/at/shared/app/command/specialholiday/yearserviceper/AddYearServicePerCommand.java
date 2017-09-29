package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AddYearServicePerCommand {
	/**コード**/
	private String specialHolidayCode;
	private String yearServiceCode;
	private int yearServiceNo;
	private String yearServiceName;
	private Integer yearServiceCls;
	private List<YearServicePerSetCommand> yearServicePerSets;
}
