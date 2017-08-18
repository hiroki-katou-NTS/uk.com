package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class YearHolidayGrantDeleteCommand.
 */

@Getter
@Setter
public class YearHolidayGrantDeleteCommand {
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String yearHolidayCode;
}
