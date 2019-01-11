package nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee;

import java.util.List;

import lombok.Value;

@Value
public class RtpIntegrationCommand {

	private List<String> sourceCode;

	private String destinationCode;

	private Integer targetYm;

}
