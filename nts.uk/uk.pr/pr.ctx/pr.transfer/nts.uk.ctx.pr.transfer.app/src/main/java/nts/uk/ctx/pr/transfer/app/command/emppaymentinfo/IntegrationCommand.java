package nts.uk.ctx.pr.transfer.app.command.emppaymentinfo;

import java.util.List;

import lombok.Value;

@Value
public class IntegrationCommand {

	private List<String> sourceIds;

	private String destinationId;

}
