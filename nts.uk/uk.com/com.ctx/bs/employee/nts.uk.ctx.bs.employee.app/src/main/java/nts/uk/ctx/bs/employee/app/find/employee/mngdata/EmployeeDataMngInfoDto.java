package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import lombok.Setter;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class EmployeeDataMngInfoDto extends PeregDomainDto {
	/** 社員コード */

	@PeregItem("IS00001")
	private String employeeCode;

	/** 外部コード */
	@PeregItem("IS00002")
	private String externalCode;
}
