package nts.uk.ctx.office.dom.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;

@Getter
@Setter
@NoArgsConstructor
public class GoOutEmployeeInformationDto implements GoOutEmployeeInformation.MementoSetter, GoOutEmployeeInformation.MementoGetter {
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
