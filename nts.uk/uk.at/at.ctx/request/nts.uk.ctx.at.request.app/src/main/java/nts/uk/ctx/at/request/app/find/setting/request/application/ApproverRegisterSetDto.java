package nts.uk.ctx.at.request.app.find.setting.request.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class ApproverRegisterSetDto {
	/** 会社単位  */
	private int companyUnit;
	/** 職場単位  */
	private int workplaceUnit;
	/** 社員単位  */
	private int employeeUnit;
}
