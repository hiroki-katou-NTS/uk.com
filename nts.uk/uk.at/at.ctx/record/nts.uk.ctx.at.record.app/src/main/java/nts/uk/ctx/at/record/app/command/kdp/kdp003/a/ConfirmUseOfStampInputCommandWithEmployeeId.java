package nts.uk.ctx.at.record.app.command.kdp.kdp003.a;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputCommand;

@Data
@EqualsAndHashCode(callSuper=false)
public class ConfirmUseOfStampInputCommandWithEmployeeId extends ConfirmUseOfStampInputCommand {
	private String employeeId;
	private String employeeCode;
	private String companyId;
}
