package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import lombok.Value;

@Value
public class RtpIntegrationCommand {

	private String sourceCode;

	private String destinationCode;

	private Integer targetYm;

}
