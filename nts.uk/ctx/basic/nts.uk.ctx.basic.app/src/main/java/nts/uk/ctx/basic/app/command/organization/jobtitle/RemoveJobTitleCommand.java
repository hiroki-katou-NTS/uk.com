package nts.uk.ctx.basic.app.command.organization.jobtitle;

import lombok.Data;

@Data
public class RemoveJobTitleCommand {
private String companyCode;
private String jobCode;

}
