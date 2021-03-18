package nts.uk.ctx.office.app.command.goout;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;

/*
 * 社員の外出情報 Command
 */
@Data
public class GoOutEmployeeInformationCommand implements GoOutEmployeeInformation.MementoGetter {
	// 外出時刻
	private Integer goOutTime;

	// 外出理由
	private String goOutReason;

	// 年月日
	private GeneralDate goOutDate;

	// 戻り時刻
	private Integer comebackTime;

	// 社員ID
	private String sid;
}
