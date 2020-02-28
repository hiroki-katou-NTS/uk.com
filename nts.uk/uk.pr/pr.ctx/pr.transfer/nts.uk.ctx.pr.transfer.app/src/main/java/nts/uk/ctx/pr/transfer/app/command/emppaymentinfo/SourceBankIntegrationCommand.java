package nts.uk.ctx.pr.transfer.app.command.emppaymentinfo;

import lombok.Value;

@Value
public class SourceBankIntegrationCommand {

	private String sourceCode;

	private String destinationCode;

}
