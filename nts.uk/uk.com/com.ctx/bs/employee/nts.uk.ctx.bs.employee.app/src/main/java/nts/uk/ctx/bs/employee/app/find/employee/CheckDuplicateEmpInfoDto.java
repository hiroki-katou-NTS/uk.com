package nts.uk.ctx.bs.employee.app.find.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class CheckDuplicateEmpInfoDto {

	Boolean isDuplicate;

	String messageId;

}
