package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.input;

import lombok.Getter;

@Getter
public class UpdateResidenceCodeCommand {
	private String residenceCode;
	private int yearKey;
	private String personId;
}
