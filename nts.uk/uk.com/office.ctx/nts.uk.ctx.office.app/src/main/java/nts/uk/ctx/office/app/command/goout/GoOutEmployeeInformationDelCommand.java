package nts.uk.ctx.office.app.command.goout;

import lombok.Data;
import nts.arc.time.GeneralDate;

/*
 * 社員の外出情報 Delete Command
 */
@Data
public class GoOutEmployeeInformationDelCommand {
	// 年月日
	private GeneralDate goOutDate;

	// 社員ID
	private String sid;
}
