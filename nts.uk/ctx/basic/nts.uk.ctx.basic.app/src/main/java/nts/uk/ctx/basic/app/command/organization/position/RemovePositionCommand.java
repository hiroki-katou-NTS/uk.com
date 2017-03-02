package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Data;

@Data
public class RemovePositionCommand {
private String companyCode;
private String jobCode;
private String historyID;

}
