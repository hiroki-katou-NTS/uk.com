package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Data;

@Data
public class RemoveJobTitleCommand {
private String companyCode;
private String jobCode;

}
