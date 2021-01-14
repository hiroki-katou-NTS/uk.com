package nts.uk.screen.com.app.find.ccg005.goout;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;

@Data
@Builder
public class GoOutEmployeeInformationDto implements GoOutEmployeeInformation.MementoSetter {
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
