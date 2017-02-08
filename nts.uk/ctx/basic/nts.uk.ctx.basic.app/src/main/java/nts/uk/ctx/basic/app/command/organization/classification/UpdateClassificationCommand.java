package nts.uk.ctx.basic.app.command.organization.classification;

import lombok.Data;

@Data
public class UpdateClassificationCommand {
	
	private String classificationCode;
	
	private String classificationName;
	
	private String memo;

}
