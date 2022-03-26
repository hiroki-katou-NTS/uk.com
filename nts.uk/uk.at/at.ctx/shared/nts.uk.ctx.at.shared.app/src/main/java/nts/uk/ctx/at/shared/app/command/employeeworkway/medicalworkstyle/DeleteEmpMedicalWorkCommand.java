package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import lombok.Getter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class DeleteEmpMedicalWorkCommand {

	@PeregRecordId
	private String histId;
	
	@PeregEmployeeId
	private String employeeId;
}
