package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
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

	public static EmployeeDataMngInfoDto fromDomain(EmployeeDataMngInfo domain) {
		if (domain == null) {
			return null;
		}

		EmployeeDataMngInfoDto dto = new EmployeeDataMngInfoDto();

		dto.setPersonId(domain.getPersonId());
		dto.setEmployeeId(domain.getEmployeeId());
		dto.setEmployeeCode(domain.getEmployeeCode().v());
		dto.setExternalCode(domain.getExternalCode().v());

		return dto;
	}
}
